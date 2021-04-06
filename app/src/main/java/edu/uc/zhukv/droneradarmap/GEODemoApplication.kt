package edu.uc.zhukv.droneradarmap

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import dji.common.error.DJIError
import dji.common.error.DJISDKError
import dji.sdk.base.BaseComponent
import dji.sdk.base.BaseProduct
import dji.sdk.base.BaseProduct.ComponentKey
import dji.sdk.products.Aircraft
import dji.sdk.sdkmanager.DJISDKInitEvent
import dji.sdk.sdkmanager.DJISDKManager
import dji.sdk.sdkmanager.DJISDKManager.SDKManagerCallback


class GEODemoApplication(private val application: MApplication) : Application() {
    private var mHandler: Handler? = null
    override fun getApplicationContext(): Context {
        return application
    }

    override fun onCreate() {
        super.onCreate()
        mHandler = Handler(Looper.getMainLooper())

        //Check the permissions before registering the application for android system 6.0 above.
        val permissionCheck = ContextCompat.checkSelfPermission(application, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permissionCheck2 = ContextCompat.checkSelfPermission(application, Manifest.permission.READ_PHONE_STATE)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || permissionCheck == 0 && permissionCheck2 == 0) {

            //This is used to start SDK services and initiate SDK.
            DJISDKManager.getInstance().registerApp(application, mDJISDKManagerCallback)
        } else {
            Toast.makeText(applicationContext, "Please check if the permission is granted.", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * When starting SDK services, an instance of interface DJISDKManager.DJISDKManagerCallback will be used to listen to
     * the SDK Registration result and the product changing.
     */
    private val mDJISDKManagerCallback: SDKManagerCallback = object : SDKManagerCallback {
        //Listens to the SDK registration result
        override fun onRegister(error: DJIError) {
            if (error === DJISDKError.REGISTRATION_SUCCESS) {
                DJISDKManager.getInstance().startConnectionToProduct()
                val handler = Handler(Looper.getMainLooper())
                handler.post { Toast.makeText(applicationContext, "Register Success", Toast.LENGTH_LONG).show() }
            } else {
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    Toast.makeText(applicationContext, "register sdk fails, check network is available", Toast.LENGTH_LONG).show()
                    Log.e(TAG, error.description)
                }
            }
            Log.e("TAG", error.toString())
        }

        override fun onProductDisconnect() {
            Log.d("TAG", "onProductDisconnect")
            notifyStatusChange()
        }

        override fun onProductConnect(baseProduct: BaseProduct) {
            Log.d("TAG", String.format("onProductConnect newProduct:%s", baseProduct))
            notifyStatusChange()
        }

        override fun onProductChanged(baseProduct: BaseProduct) {}
        override fun onComponentChange(componentKey: ComponentKey, oldComponent: BaseComponent,
                                       newComponent: BaseComponent) {
            if (newComponent != null) {
                newComponent.setComponentListener { isConnected ->
                    Log.d("TAG", "onComponentConnectivityChanged: $isConnected")
                    notifyStatusChange()
                }
            }
            Log.d("TAG", String.format("onComponentChange key:%s, oldComponent:%s, newComponent:%s",
                    componentKey,
                    oldComponent,
                    newComponent))
        }

        override fun onInitProcess(djisdkInitEvent: DJISDKInitEvent, i: Int) {}
        override fun onDatabaseDownloadProgress(l: Long, l1: Long) {}
    }

    private fun notifyStatusChange() {
        mHandler!!.removeCallbacks(updateRunnable)
        mHandler!!.postDelayed(updateRunnable, 500)
    }

    private val updateRunnable = Runnable {
        val intent = Intent(FLAG_CONNECTION_CHANGE)
        application.sendBroadcast(intent)
    }

    companion object {

        private val TAG = GEODemoApplication::class.java.name
        const val FLAG_CONNECTION_CHANGE = "com_dji_GEODemo_connection_change"
        private var product: BaseProduct? = null

        /**
         * Gets instance of the specific product connected after the
         * API KEY is successfully validated. Please make sure the
         * API_KEY has been added in the Manifest
         */
        @get:Synchronized
        val productInstance: BaseProduct?
            get() {
                if (null == product) {
                    product = DJISDKManager.getInstance().product
                }
                return product
            }
        val isAircraftConnected: Boolean
            get() = productInstance != null && productInstance is Aircraft

        @get:Synchronized
        val aircraftInstance: Aircraft?
            get() = if (!isAircraftConnected) null else productInstance as Aircraft?
    }
}