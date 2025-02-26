package org.shu.tool.password.ui.page.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.shu.tool.password.base.module.PasswordRecord
import org.shu.tool.password.util.Log
import org.shu.tool.password.util.TimeExt
import org.shu.tool.password.util.isEmail
import org.shu.tool.password.util.isPhoneNumber
import org.shu.tool.password.util.isURL
import org.shu.tool.password.util.to_SHA256_BASE64
import passwordtool.composeapp.generated.resources.Res
import passwordtool.composeapp.generated.resources.account
import passwordtool.composeapp.generated.resources.ic_account
import passwordtool.composeapp.generated.resources.ic_back
import passwordtool.composeapp.generated.resources.ic_email
import passwordtool.composeapp.generated.resources.ic_phone
import passwordtool.composeapp.generated.resources.ic_private_key
import passwordtool.composeapp.generated.resources.ic_scan_code
import passwordtool.composeapp.generated.resources.ic_weblink
import passwordtool.composeapp.generated.resources.more
import passwordtool.composeapp.generated.resources.private_key
import passwordtool.composeapp.generated.resources.remark
import passwordtool.composeapp.generated.resources.username
import passwordtool.composeapp.generated.resources.weblink
import passwordtool.composeapp.generated.resources.nickname
import passwordtool.composeapp.generated.resources.complete
import passwordtool.composeapp.generated.resources.edit
import passwordtool.composeapp.generated.resources.new

@Composable
fun DetailPage(
    recordId: Long? = null, //null-表示添加模式，notnull-表示编辑模式
    onBack: () -> Unit = {}, modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<DetailViewModel>()
    viewModel.obtainRecordById(recordId)
    val record by viewModel.pageRecord.collectAsStateWithLifecycle()
    Log.d("DetailPage", "$recordId $record")
    val scheme = MaterialTheme.colorScheme
    var websiteLink by remember(record) { mutableStateOf(record?.websiteLink ?: "") }
    var account by remember(record) { mutableStateOf(record?.account ?: "") }
    var privateKey by remember(record) { mutableStateOf("") }
    var username by remember(record) { mutableStateOf(record?.username ?: "") }
    var nickname by remember(record) { mutableStateOf(record?.nickname ?: "") }
    var remark by remember(record) { mutableStateOf(record?.remark ?: "") }
    var passwordType by remember(record) { mutableIntStateOf(0) }

    Column(modifier = modifier.fillMaxSize().background(scheme.background)) {

        val title =
            if (record == null) stringResource(Res.string.new) else stringResource(Res.string.edit)

        PageTitleBar(title = title, onBack = onBack, onComplete = {
            val newRecord = PasswordRecord(
                websiteLink = websiteLink,
                account = account,
                accountType = if (account.isEmail()) 0 else 1,
                passwordType = passwordType,
                cipher = record?.cipher ?: privateKey.to_SHA256_BASE64(),
                username = username,
                nickname = nickname,
                remark = remark,
                registerDate = record?.registerDate ?: TimeExt.now(),
                modifyDate = if (record == null) -1 else TimeExt.now(),
                id = record?.id,
            )
            viewModel.insertRecord(newRecord)
            onBack()
        })
        HorizontalDivider(color = MaterialTheme.colorScheme.onBackground)
        val editModifier =
            Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 6.dp).height(60.dp)

        EditPasswordRecordProperty(
            value = websiteLink,
            label = stringResource(Res.string.weblink),
            painter = painterResource(Res.drawable.ic_weblink),
            trailingIcon = {
                TrailingIcon(Res.drawable.ic_scan_code)
            },
            onValueChange = { websiteLink = it },
            onVerify = { it.isURL() },
            modifier = Modifier.then(editModifier)
        )

        if (record == null) {
            EditPasswordRecordProperty(
                value = privateKey,
                label = stringResource(Res.string.private_key),
                painter = painterResource(Res.drawable.ic_private_key),
                trailingIcon = {
                    Box(
                        modifier = Modifier.fillMaxHeight()
                            .clickable { passwordType = (passwordType + 1) % 2 }.padding(10.dp)
                            .background(if (passwordType == 0) Color.Red else Color.Green),
                    )
                },
                onValueChange = { privateKey = it },
                onVerify = { it.isNotBlank() },
                modifier = Modifier.then(editModifier)
            )
        }

        EditPasswordRecordProperty(
            value = account,
            label = stringResource(Res.string.account),
            painter = painterResource(Res.drawable.ic_account),
            trailingIcon = {
                if (account.isPhoneNumber()) {
                    TrailingIcon(Res.drawable.ic_phone)
                } else {
                    TrailingIcon(Res.drawable.ic_email)
                }
            },
            onValueChange = { account = it },
            onVerify = { it.isEmail() || it.isPhoneNumber() },
            modifier = Modifier.then(editModifier)
        )

        var export by remember { mutableStateOf(true) }

        DividerExportWidget(
            label = stringResource(Res.string.more),
            export = export,
            onExport = { export = it },
            modifier = Modifier.padding(vertical = 6.dp).fillMaxWidth().wrapContentHeight()
        )

        AnimatedVisibility(visible = export) {
            Column {
                Row(
                    modifier = Modifier.then(editModifier),
                ) {
                    EditPasswordRecordExtProperty(
                        value = username,
                        label = stringResource(Res.string.username),
                        onValueChange = { username = it },
                        modifier = Modifier.weight(1f)
                    )

                    EditPasswordRecordExtProperty(
                        value = nickname,
                        label = stringResource(Res.string.nickname),
                        onValueChange = { nickname = it },
                        modifier = Modifier.fillMaxHeight().weight(1f)
                    )
                }

                EditPasswordRecordExtProperty(
                    value = remark,
                    label = stringResource(Res.string.remark),
                    singleLine = false,
                    onValueChange = { remark = it },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 6.dp)
                        .fillMaxHeight(),
                )
            }
        }

    }
}

@Composable
fun PageTitleBar(
    title: String = "",
    onBack: () -> Unit = {},
    onComplete: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val height = 60.dp
    Row(modifier = modifier.fillMaxWidth().height(height)) {
        Image(painter = painterResource(Res.drawable.ic_back),
            contentDescription = "back",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer),
            modifier = Modifier.size(height).clickable { onBack() }.padding(20.dp))
        Text(
            text = title,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f).fillMaxHeight()
                .wrapContentHeight(Alignment.CenterVertically) // 文字垂直居中

        )
        Text(text = stringResource(Res.string.complete),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(height).wrapContentHeight(Alignment.CenterVertically)
                .clickable { onComplete() })
    }
}

@Composable
fun TrailingIcon(icon: DrawableResource, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(icon),
        contentDescription = "",
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
        modifier = modifier.fillMaxHeight().padding(10.dp),
    )
}

@Composable
fun EditPasswordRecordProperty(
    value: String,
    label: String,
    painter: Painter,
    onValueChange: (String) -> Unit,
    onVerify: (String) -> Boolean = { true },
    trailingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var pass by remember { mutableStateOf(true) }
    OutlinedTextField(value = value, label = {
        Text(
            text = label, fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary
        )
    }, onValueChange = {
        onValueChange(it)
        pass = onVerify(it)
    }, singleLine = true, isError = !pass, leadingIcon = {
        Image(
            painter = painter,
            contentDescription = "",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
            modifier = Modifier.fillMaxHeight().padding(10.dp),
        )
    }, trailingIcon = trailingIcon, textStyle = TextStyle(
        fontSize = 14.sp,
        textAlign = TextAlign.Start,
        color = MaterialTheme.colorScheme.onSecondary,
    ), colors = OutlinedTextFieldDefaults.colors().copy(
        errorLabelColor = MaterialTheme.colorScheme.error,
        unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
        focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
        disabledLabelColor = MaterialTheme.colorScheme.secondary
    ), shape = GenericShape { size, _ ->
        moveTo(0f, 0f)
        lineTo(size.width, 0f)
        lineTo(size.width, size.height)
        lineTo(0f, size.height)
    }, modifier = modifier
    )
}

@Composable
fun EditPasswordRecordExtProperty(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(value = value, label = {
        Text(
            text = label, fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary
        )
    }, onValueChange = { onValueChange(it) }, singleLine = singleLine, textStyle = TextStyle(
        fontSize = 14.sp,
        textAlign = TextAlign.Start,
        color = MaterialTheme.colorScheme.onSecondary,
    ), colors = OutlinedTextFieldDefaults.colors().copy(
        errorLabelColor = MaterialTheme.colorScheme.error,
        unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
        focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
        disabledLabelColor = MaterialTheme.colorScheme.secondary
    ), shape = GenericShape { size, _ ->
        moveTo(0f, 0f)
        lineTo(size.width, 0f)
        lineTo(size.width, size.height)
        lineTo(0f, size.height)
    }, modifier = modifier
    )
}

@Composable
fun DividerExportWidget(
    label: String,
    export: Boolean = false,
    modifier: Modifier = Modifier,
    onExport: (Boolean) -> Unit = {},
) {
    val radio = remember { Animatable(0f) }
    LaunchedEffect(export) {
        radio.animateTo(
            if (export) {
                -90f
            } else {
                0f
            }
        )
    }
    Column(modifier = modifier.padding(start = 10.dp)) {
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                .clickable { onExport(!export) }) {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 12.sp,
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(30.dp).background(MaterialTheme.colorScheme.background)
                    .align(Alignment.CenterEnd).rotate(radio.value),
                contentDescription = ""
            )
        }
    }

}