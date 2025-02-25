package org.shu.tool.password

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import kotlinx.coroutines.flow.flow
import org.shu.tool.password.base.module.PasswordRecord
import org.shu.tool.password.ui.page.home.DragBar
import org.shu.tool.password.ui.page.home.Home
import org.shu.tool.password.ui.page.home.KeyRecordDefaultItem
import org.shu.tool.password.ui.page.home.KeyRecordExportItem
import org.shu.tool.password.ui.page.home.PasswordRecordItem
import org.shu.tool.password.ui.page.home.PasswordWidget
import org.shu.tool.password.ui.page.home.PasswordWidgetLabel
import org.shu.tool.password.ui.page.home.SearchBar
import org.shu.tool.password.ui.theme.PasswordToolTheme
import org.shu.tool.password.util.collectAsLazyPagingItems

private val testData = obtainTestData()

@Preview
@Composable
fun HomePreview() {
    PasswordToolTheme {
        val flow = flow { emit(PagingData.from(testData)) }
        Home(
            items = flow.collectAsLazyPagingItems()
        )
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    PasswordToolTheme {
        SearchBar()
    }
}

@Preview
@Composable
private fun PasswordRecordItemPreview() {
    PasswordToolTheme {
        PasswordRecordItem(testData[0])
    }
}

@Preview
@Composable
private fun KeyRecordDefaultItemPreview() {
    PasswordToolTheme {
        val radio = remember { Animatable(0f) }
        KeyRecordDefaultItem(testData[0],radio)
    }
}

@Preview
@Composable
private fun KeyRecordExportItemPreview() {
    PasswordToolTheme {
        KeyRecordExportItem(testData[0])
    }
}
@Preview
@Composable
private fun PasswordWidgetPreview() {
    PasswordToolTheme {
        PasswordWidget(modifier = Modifier.height(100.dp))
    }
}
@Preview
@Composable
private fun PasswordWidgetLabelPreview() {
    PasswordToolTheme {
        PasswordWidgetLabel(1)
    }
}
@Preview
@Composable
private fun DragBarPreview() {
    PasswordToolTheme {
        DragBar(modifier = Modifier.height(80.dp))
    }
}

fun obtainTestData(): List<PasswordRecord> {
    return List(20) {
        PasswordRecord(
            id = 0,
            websiteLink = "https://www.baidu.com",
            account = "shuouyang0@gmail.com",
            accountType = PasswordRecord.ACCOUNT_TYPE_EMAIL,
            passwordType = PasswordRecord.PASSWORD_TYPE_STRONG,
            cipher = "jlksjflkasjflj",
            registerDate = System.currentTimeMillis(),
            //        nickname = "百度",
            remark = "这是简单的备注，用于测试",
            username = "shuouyang",
            modifyDate = System.currentTimeMillis() + 20
        )
    }
}