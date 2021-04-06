package edu.uc.zhukv.droneradarmap

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import dji.sdk.base.BaseProduct
import dji.sdk.products.Aircraft
import dji.sdk.sdkmanager.DJISDKManager


class ConnectionActivity : Activity(), View.OnClickListener {
    private var mTextConnectionStatus: TextView? = null
    private var mTextProduct: TextView? = null
    private var mVersionTv: TextView? = null
    private var mBtnOpen: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // When the compile and target version is higher than 22, please request the
        // following permissions at runtime to ensure the
        // SDK work well.
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.VIBRATE,
                Manifest.permission.INTERNET, Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WAKE_LOCK, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.READ_PHONE_STATE), 1)
        setContentView(R.layout.activity_connection)
        initUI()

        // Register the broadcast receiver for receiving the device connection's changes.
        val filter = IntentFilter()
        filter.addAction(GEODemoApplication.FLAG_CONNECTION_CHANGE)
        registerReceiver(mReceiver, filter)
    }

    fun onReturn(view: View?) {
        Log.e(TAG, "onReturn")
        finish()
    }

    @SuppressLint("StringFormatInvalid")
    private fun initUI() {
        mTextConnectionStatus = findViewById<View>(R.id.text_connection_status) as TextView
        mTextProduct = findViewById<View>(R.id.text_product_info) as TextView
        mVersionTv = findViewById<View>(R.id.text_version) as TextView
        mVersionTv!!.text = resources.getString(R.string.sdk_version, DJISDKManager.getInstance().sdkVersion)
        mBtnOpen = findViewById<View>(R.id.btn_open) as Button
        mBtnOpen!!.setOnClickListener(this)
        mBtnOpen!!.isEnabled = false
    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy")
        unregisterReceiver(mReceiver)
        super.onDestroy()
    }

    protected var mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            refreshSDKRelativeUI()
        }
    }

    private fun refreshSDKRelativeUI() {
        val mProduct: BaseProduct? = GEODemoApplication.productInstance
        if (null != mProduct && mProduct.isConnected) {
            Log.v(TAG, "refreshSDK: True")
            mBtnOpen!!.isEnabled = true
            val str = if (mProduct is Aircraft) "Aircraft" else "HandHeld"
            mTextConnectionStatus!!.text = "Status: $str connected"
            if (null != mProduct.model) {
                mTextProduct!!.text = "" + mProduct.model.displayName
            } else {
                mTextProduct?.setText(R.string.product_information)
            }
        } else {
            Log.v(TAG, "refreshSDK: False")
            mBtnOpen!!.isEnabled = false
            mTextProduct?.setText(R.string.product_information)
            mTextConnectionStatus?.setText(R.string.connection_loose)
        }
    }

    fun showToast(msg: String?) {
        runOnUiThread { Toast.makeText(this@ConnectionActivity, msg, Toast.LENGTH_SHORT).show() }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_open -> {
                val status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(applicationContext)
                if (status != ConnectionResult.SUCCESS) {
                    GooglePlayServicesUtil.getErrorDialog(status, this, status)
                    showToast("Cannot run without Google Play, please check！")
                } else {
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                }
            }
            else -> {
            }
        }
    }

    companion object {
        private val TAG = ConnectionActivity::class.java.name
    }
}