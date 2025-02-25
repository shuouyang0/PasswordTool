package org.shu.tool.password.ui.page.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomePage() {
    val scheme = MaterialTheme.colorScheme
    Column(modifier = Modifier.fillMaxSize().background(scheme.background)) {
        Box(modifier = Modifier.fillMaxWidth().weight(1f).background(scheme.onBackground)){

        }
        SearchBarWidget(
            modifier = Modifier.fillMaxWidth().height(60.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {

        }
    }
}

@Composable
fun PasswordRecordList(
    modifier: Modifier = Modifier,
) {
    LazyColumn(){

    }
}

