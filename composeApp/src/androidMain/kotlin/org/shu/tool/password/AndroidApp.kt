package org.shu.tool.password

import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.shu.tool.password.base.di.initKoin


class AndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("shuouyang", "onCreate: =====================INIT====================")
        initKoin {
            androidContext(this@AndroidApp)
            androidLogger()
        }
    }
}