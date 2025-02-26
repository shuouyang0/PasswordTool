package org.shu.tool.password.ui.page.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.shu.keytool.base.algorithm.EncryptionAlgorithm
import org.shu.tool.password.base.module.PasswordRecord
import org.shu.tool.password.ui.widget.SwipeMoreWidget
import org.shu.tool.password.util.LazyPagingItems
import org.shu.tool.password.util.Log
import org.shu.tool.password.util.TimeExt
import org.shu.tool.password.util.collectAsLazyPagingItems
import org.shu.tool.password.util.to_SHA256_BASE64
import passwordtool.composeapp.generated.resources.Res
import passwordtool.composeapp.generated.resources.account_s
import passwordtool.composeapp.generated.resources.delete
import passwordtool.composeapp.generated.resources.error
import passwordtool.composeapp.generated.resources.ic_next
import passwordtool.composeapp.generated.resources.ic_website
import passwordtool.composeapp.generated.resources.input_private_key_tip
import passwordtool.composeapp.generated.resources.look_up
import passwordtool.composeapp.generated.resources.pass

@Composable
fun obtainTestData(): LazyPagingItems<PasswordRecord> {
    val testData = mutableListOf<PasswordRecord>()
    repeat(20) { idx ->
        testData.add(
            PasswordRecord(
                id = idx.toLong(),
                websiteLink = "https://www.baidu.com",
                account = "shuouyang0@gmail.com",
                accountType = PasswordRecord.ACCOUNT_TYPE_EMAIL,
                passwordType = PasswordRecord.PASSWORD_TYPE_STRONG,
                cipher = "12345678",
                registerDate = TimeExt.now(),
                //        nickname = "百度",
                remark = "这是简单的备注，用于测试",
                username = "shuouyang",
                modifyDate = TimeExt.now() + 20
            )
        )
    }
    val page = PagingData.from(testData)
    return flow { emit(page) }.collectAsLazyPagingItems()
}

@Composable
fun HomePage(
    navToDetail: (Long?) -> Unit = {}
) {
    val scheme = MaterialTheme.colorScheme
    val viewModel = koinViewModel<HomeViewModel>()
    var pager by remember { mutableStateOf(viewModel.obtainAllRecord()) }
    val items = pager.flow.collectAsLazyPagingItems()

    Column(modifier = Modifier.fillMaxSize().background(scheme.background)) {
        PasswordRecordList(
            items = items,
            onDeleteRecord = { viewModel.deleteRecord(it) },
            onEditRecord = { navToDetail(it.id) },
            modifier = Modifier.fillMaxWidth().weight(1f).background(scheme.onBackground)
        )
        SearchBar(
            onValueChange = {
                pager = viewModel.searchRecord(it)
            },
            onAddRecord = { navToDetail(-1) },
            modifier = Modifier.fillMaxWidth().height(60.dp).background(scheme.secondaryContainer)
        )
    }
}


@Composable
fun PasswordRecordList(
    items: LazyPagingItems<PasswordRecord> = obtainTestData(),
    onDeleteRecord: (PasswordRecord) -> Unit = {},
    onEditRecord: (PasswordRecord) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val state = rememberLazyListState()
    LazyColumn(modifier = modifier, state = state) {
        items(count = items.itemCount, key = { index -> items[index]?.id.toString() }) { index ->
            val record = items[index]
            Log.d("HomePage --> ", record)
            if (record != null) {
                PasswordRecordItem(
                    record = record, onDeleteRecord = onDeleteRecord, onEditRecord = onEditRecord
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchBar(
    onValueChange: (String) -> Unit = {},
    onAddRecord: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var keyword by remember { mutableStateOf("") }

    TextField(
        modifier = modifier,
        value = keyword,
        onValueChange = {
            keyword = it
            onValueChange(it)
        },
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
            focusedTextColor = MaterialTheme.colorScheme.secondary,
            unfocusedTextColor = MaterialTheme.colorScheme.secondary
        ),
        singleLine = true,
        textStyle = TextStyle(fontSize = 12.sp),
        placeholder = { Text(stringResource(Res.string.look_up), fontSize = 12.sp) },
        shape = MaterialTheme.shapes.small.copy(
            topStart = ZeroCornerSize,
            topEnd = ZeroCornerSize,
            bottomStart = ZeroCornerSize,
            bottomEnd = ZeroCornerSize
        ),
        leadingIcon = {
            Icon(
                contentDescription = "",
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        trailingIcon = {
            Icon(imageVector = Icons.Default.Add,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(40.dp)
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
                    .clickable { onAddRecord() })
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    )
}

@Preview
@Composable
fun PasswordRecordItem(
    record: PasswordRecord,
    onDeleteRecord: (PasswordRecord) -> Unit = {},
    onEditRecord: (PasswordRecord) -> Unit = {},
) {
    var export by remember { mutableStateOf(false) }
    val radio = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    SwipeMoreWidget(rtl = false, content = {
        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().pointerInput(Unit) {
            detectTapGestures(onLongPress = { onEditRecord(record) }, onTap = {
                export = !export
                coroutineScope.launch {
                    radio.animateTo(if (export) 90f else 0f)
                }
            })
        }.background(MaterialTheme.colorScheme.secondaryContainer)) {
            KeyRecordDefaultItem(record, radio)
            AnimatedVisibility(export) {
                KeyRecordExportItem(
                    record = record,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                        .padding(horizontal = 20.dp)
                )
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth().height(1.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.background
            )
        }
    }, more = {
        Text(text = stringResource(Res.string.delete),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxHeight().width(60.dp).clickable { onDeleteRecord(record) }
                .background(Color.Red).wrapContentSize(Alignment.Center))
    })

}

@Preview
@Composable
fun KeyRecordDefaultItem(
    record: PasswordRecord, radio: Animatable<Float, AnimationVector1D>
) {
    Row(
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = record.obtainIconLink(),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            placeholder = painterResource(Res.drawable.ic_website),
            error =  painterResource(Res.drawable.ic_website),
        )
        Text(
            text = record.obtainTitle(),
            modifier = Modifier.padding(horizontal = 20.dp).weight(1f),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Image(
            contentDescription = null,
            painter = painterResource(Res.drawable.ic_next),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
            modifier = Modifier.size(10.dp).rotate(radio.value)
        )
    }
}

@Preview
@Composable
fun KeyRecordExportItem(record: PasswordRecord, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        if (record.remark.isNotBlank()) {
            Text(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                text = record.remark,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Log.d("KeyRecordExportItem", record.account)
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.account_s) + record.account,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        PasswordWidget(
            verifyPrivateKey = { it.to_SHA256_BASE64() == record.cipher },
            computeCipher = { EncryptionAlgorithm.encrypt(record.websiteLink, it) },
            modifier = Modifier.padding(vertical = 10.dp).fillMaxWidth().height(60.dp)
        )
    }
}

@Preview
@Composable
fun PasswordWidget(
    modifier: Modifier = Modifier,
    verifyPrivateKey: (String) -> Boolean = { false },
    computeCipher: (String) -> String = { "" },
) {
    var state by remember { mutableIntStateOf(0) } // 0-初始态，1-私钥验证通过, -1 私钥校验失败，2
    var width by remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current

    var privateKey by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val privateKeyCap = width / (privateKey.length + 1) / 4
    val passwordCap = width / (password.length + 1) / 8

    Box(
        modifier = modifier.onSizeChanged { width = it.width },
        contentAlignment = Alignment.Center // 设置内容居中对齐
    ) {
        AnimatedVisibility(password.isNotBlank()) {
            Text(
                text = password,
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = TextStyle(letterSpacing = passwordCap.sp),
                fontSize = 14.sp,
                modifier = Modifier.fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.onSecondaryContainer)
                    .wrapContentSize(Alignment.Center),
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        AnimatedVisibility(state > 0) {
            DragBar(modifier = Modifier.fillMaxSize()) {
                clipboardManager.setText(AnnotatedString(password))
//                Tip.showToast(context.getString(R.string.copy_password_tip))
            }
        }

        AnimatedVisibility(state <= 0) {
            OutlinedTextField(
                modifier = Modifier.fillMaxSize(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                label = { PasswordWidgetLabel(state) },
                isError = state == -1,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSecondary,
                    letterSpacing = privateKeyCap.sp,
                ),
                colors = OutlinedTextFieldDefaults.colors().copy(
                    errorLabelColor = MaterialTheme.colorScheme.error,
                    unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
                    focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                    disabledLabelColor = MaterialTheme.colorScheme.secondary
                ),
                value = privateKey,
                onValueChange = { privateKey = it },
                shape = GenericShape { size, _ ->
                    moveTo(0f, 0f)
                    lineTo(size.width, 0f)
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                keyboardActions = KeyboardActions(onDone = {
                    state = if (verifyPrivateKey(privateKey)) {
                        coroutineScope.launch {
                            delay(1000)
                            password = computeCipher(privateKey)
                        }
                        1
                    } else {
                        -1
                    }
                })
            )
        }

    }
}

@Preview
@Composable
fun PasswordWidgetLabel(state: Int) {
    Text(
        text = when (state) {
            1 -> stringResource(Res.string.pass)
            -1 -> stringResource(Res.string.error)
            else -> stringResource(Res.string.input_private_key_tip)
        }, textAlign = TextAlign.Center, fontSize = 10.sp
    )
}

@Composable
fun DragBar(
    modifier: Modifier = Modifier.fillMaxSize(),
    iconPainter: Painter = painterResource(Res.drawable.ic_next),
    iconColor: Color = MaterialTheme.colorScheme.surface,
    iconBgColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    dragBarBg: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    onFinished: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    var containerWidth by remember { mutableFloatStateOf(0f) }
    var containerHeight by remember { mutableFloatStateOf(0f) }
    val offsetX = remember { Animatable(0f) }
    var isDragging by remember { mutableStateOf(false) }
    val dragEnd = {
        isDragging = false
        coroutineScope.launch {
            val state = offsetX.value > (containerWidth - containerHeight) / 2
            if (state) {
                onFinished()
                offsetX.animateTo(containerWidth - containerHeight)
                coroutineScope.launch { //自动回到开始
                    delay(3000L)
                    offsetX.animateTo(0f)
                }
            } else {
                offsetX.animateTo(0f)
            }
        }
    }
    Canvas(modifier = modifier.onSizeChanged {
        containerWidth = it.width.toFloat()
        containerHeight = it.height.toFloat()
    }.pointerInput(Unit) {
        detectDragGestures(onDragStart = {
            isDragging = it.x > 0 && it.x < containerHeight
        }, onDragEnd = { dragEnd() }, onDragCancel = { dragEnd() }, onDrag = { _, dragAmount ->
            if (isDragging) {
                coroutineScope.launch {
                    offsetX.snapTo(
                        (offsetX.value + dragAmount.x).coerceIn(
                            0f, containerWidth - containerHeight
                        )
                    )
                }
            }
        })
    }) {
        drawRect(
            color = iconBgColor,
            topLeft = Offset(offsetX.value, 0f),
            size = Size(containerHeight, containerHeight),
        )
        val iconSize = containerHeight / 2f
        val iconOffset = (containerHeight - iconSize) / 2
        translate(left = iconOffset + offsetX.value, top = iconOffset) {
            with(iconPainter) {
                draw(
                    size = Size(iconSize, iconSize), colorFilter = ColorFilter.tint(iconColor)
                )
            }
        }
        drawRect(
            color = dragBarBg, size = Size(
                containerWidth - containerHeight - offsetX.value, containerHeight
            ), topLeft = Offset(containerHeight + offsetX.value, 0f)
        )
    }
}


