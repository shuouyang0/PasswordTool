package org.shu.tool.password

import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.shu.tool.password.ui.page.home.HomePage
import org.shu.tool.password.ui.theme.PasswordToolTheme

@Composable
@Preview
fun App() {
    PasswordToolTheme {
        HomePage()
    }
}

