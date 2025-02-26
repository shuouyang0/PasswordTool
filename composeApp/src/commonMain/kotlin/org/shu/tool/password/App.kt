package org.shu.tool.password

import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.shu.tool.password.ui.page.home.HomePage
import org.shu.tool.password.ui.theme.PasswordToolTheme
import org.shu.tool.password.ui.page.detail.DetailPage

@Composable
@Preview
fun App() {
    KoinContext {
        val navController = rememberNavController()
        PasswordToolTheme {
            NavHost(navController = navController, startDestination = "home") {

                composable("home") { stackEntry ->
                    HomePage(
                        navToDetail = { record ->
                            navController.navigate("detail/${record?.id}")
                        }
                    )
                }
                composable("detail/{recordId}",
                    arguments = listOf(
                        navArgument("recordId") {
                            type = NavType.LongType
                        }
                    )
                ) { stackEntry ->
                    val recordId = stackEntry.arguments?.getLong("recordId")
                    DetailPage(
                        recordId = recordId,
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}

