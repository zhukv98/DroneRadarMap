package edu.uc.zhukv.droneradarmap

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.multidex.MultiDex
import com.secneo.sdk.Helper

class MApplication : Application() {
    var mApplication: GEODemoApplication? = null

    fun loadApplication(paramContext: Context?) {
        if (mApplication != null) return
        mApplication = GEODemoApplication(this@MApplication)
    }

    override fun attachBaseContext(paramContext: Context?) {
        super.attachBaseContext(paramContext)
        MultiDex.install(this)
        if (mApplication != null) {
            return
        }
        Helper.install(this)
        loadApplication(paramContext)
    }

    override fun onCreate() {
        super.onCreate()
        mApplication?.onCreate()
    }

    override fun onConfigurationChanged(paramConfig: Configuration) {
        super.onConfigurationChanged(paramConfig)
        mApplication?.onConfigurationChanged(paramConfig)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mApplication?.onLowMemory()
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}