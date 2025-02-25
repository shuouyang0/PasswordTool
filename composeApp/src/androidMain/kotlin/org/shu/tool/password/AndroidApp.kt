package org.shu.tool.password

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.shu.tool.password.base.di.diCommonModule

class AndroidApp:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AndroidApp)
            module { single { DiPlatformFactory(get()) } }
            diCommonModule()
        }
    }
}