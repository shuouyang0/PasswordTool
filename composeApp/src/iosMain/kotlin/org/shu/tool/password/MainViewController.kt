package org.shu.tool.password

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.shu.tool.password.base.di.diCommonModule

fun MainViewController() = ComposeUIViewController {
    startKoin {
        module { single { DiPlatformFactory() } }
        modules(diCommonModule())
    }
    App()
}