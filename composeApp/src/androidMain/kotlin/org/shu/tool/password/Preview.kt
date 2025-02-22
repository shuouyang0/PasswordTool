package org.shu.tool.password

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.shu.tool.password.ui.page.home.HomePage
import org.shu.tool.password.ui.theme.PasswordToolTheme

@Preview
@Composable
fun HomePagePrev() {
    PasswordToolTheme {
        HomePage()
    }
}