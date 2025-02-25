package org.shu.tool.password

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.shu.tool.password.base.di.diCommonModule

fun main() = application {
    val state = rememberWindowState(
        size = DpSize(400.dp, 250.dp),
        position = WindowPosition(300.dp, 300.dp)
    )
    startKoin {
        module { single { DiPlatformFactory() } }
        modules(diCommonModule())
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "PasswordTool",
        state = state
    ) {
        App()
    }
}