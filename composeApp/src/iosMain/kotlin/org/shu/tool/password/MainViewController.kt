package org.shu.tool.password

import androidx.compose.ui.window.ComposeUIViewController
import org.shu.tool.password.base.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin{}
    App()
}