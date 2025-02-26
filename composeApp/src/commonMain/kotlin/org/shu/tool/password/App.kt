package org.shu.tool.password

import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.shu.keytool.base.algorithm.EncryptionAlgorithm
import org.shu.keytool.base.algorithm.PeanutEncryptionAlgorithm
import org.shu.tool.password.ui.page.home.HomePage
import org.shu.tool.password.ui.theme.PasswordToolTheme
import org.shu.tool.password.ui.page.detail.DetailPage
import org.shu.tool.password.util.Log

@Composable
@Preview
fun App() {
    EncryptionAlgorithm.use(PeanutEncryptionAlgorithm())
    KoinContext {
        val navController = rememberNavController()
        PasswordToolTheme {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { stackEntry ->
                    HomePage(
                        navToDetail = { id ->
                            navController.navigate("detail/${id ?: -1}")
                            Log.d("form HomePage", id)
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
                    var recordId = stackEntry.arguments?.getLong("recordId")
                    if ( recordId != null && recordId < 0) recordId = null
                    Log.d("go DetailPage", recordId)
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

