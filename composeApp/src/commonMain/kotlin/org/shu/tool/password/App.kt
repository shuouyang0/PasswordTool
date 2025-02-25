package org.shu.tool.password

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.shu.tool.password.ui.page.home.HomePage
import org.shu.tool.password.ui.theme.PasswordToolTheme

@Composable
@Preview
fun App() {
    KoinContext {
        val navController = rememberNavController()
        PasswordToolTheme {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomePage()
                }
            }
        }
    }
}

