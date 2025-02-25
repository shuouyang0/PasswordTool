package org.shu.tool.password

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.shu.tool.password.base.di.initKoin

fun main() = application {
    val state = rememberWindowState(
        size = DpSize(400.dp, 800.dp),
        position = WindowPosition(300.dp, 300.dp)
    )
    initKoin {}
    Window(
        onCloseRequest = ::exitApplication,
        title = "PasswordTool",
        state = state
    ) {
        App()
    }
}