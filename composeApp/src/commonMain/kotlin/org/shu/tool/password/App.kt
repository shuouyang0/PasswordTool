package org.shu.tool.password

import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.context.startKoin
import org.shu.tool.password.base.di.NetworkModule
import org.shu.tool.password.base.di.DataBaseModule
import org.shu.tool.password.ui.page.home.HomePage
import org.shu.tool.password.ui.theme.PasswordToolTheme

private fun dependencyInjection(){
    startKoin {
        modules(
            DataBaseModule,
            NetworkModule
        )
    }
}

@Composable
@Preview
fun App() {
    dependencyInjection()
    PasswordToolTheme {
        HomePage()
    }
}

