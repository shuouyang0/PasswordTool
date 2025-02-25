package org.shu.tool.password.ui.page.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomePage() {
    val scheme = MaterialTheme.colorScheme
    val viewModel = koinViewModel<HomeViewModel>()
    val scope = rememberCoroutineScope()
    scope.launch {
        viewModel.obtainAllRecord().flow.collectLatest {
            println("shuouyang -----> $it" )
        } }
    Column(modifier = Modifier.fillMaxSize().background(scheme.background)) {
        PasswordRecordList(
            modifier = Modifier.fillMaxWidth().weight(1f).background(scheme.onBackground)
        )
        SearchBarWidget(
            modifier = Modifier.fillMaxWidth().height(60.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {

        }
    }
}

@Composable
fun PasswordRecordList(
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {

    }
}

