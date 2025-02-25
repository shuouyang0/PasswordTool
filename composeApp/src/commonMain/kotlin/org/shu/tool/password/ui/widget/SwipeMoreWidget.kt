package org.shu.tool.password.ui.widget


import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun SwipeMoreWidget(
    modifier: Modifier = Modifier,
    rtl: Boolean = true,
    content: @Composable () -> Unit = {},
    more: @Composable () -> Unit = {},
) {
    var offset = remember { Animatable(0f) }
    var maxOffset by remember { mutableIntStateOf(100) }
    val cs = rememberCoroutineScope()
    val dragEnd = {
        val newOffset = if(offset.value.absoluteValue > maxOffset /2f) if (rtl) -maxOffset.toFloat() else maxOffset.toFloat() else 0f
        cs.launch { offset.animateTo(newOffset) }
    }
    Layout(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = { dragEnd() },
                    onDragCancel = { dragEnd() },
                    onDrag = { _, dragAmount ->
                        val range = if (rtl) -maxOffset..0 else 0..maxOffset
                        val newOffset = (dragAmount.x + offset.value).coerceIn(range.first.toFloat(),range.last.toFloat())
                        cs.launch { offset.snapTo(newOffset) }
                    }
                )
            },
        content = {
            content()
            more()
        }
    ) { measurables, constraints ->
        val contentMeasurable = measurables[0]
        val moreMeasurable = measurables[1]
        val contentPlaceable = contentMeasurable.measure(constraints)
        val morePlaceable = moreMeasurable.measure(constraints.copy(maxHeight = contentPlaceable.height))
        maxOffset = morePlaceable.width
        layout(contentPlaceable.width, contentPlaceable.height) {
            val offsetX = offset.value.toInt()
            val contentX = if (rtl) offsetX else offsetX
            val moreX = if (rtl) contentPlaceable.width + offsetX else offsetX - morePlaceable.width

            contentPlaceable.placeRelative(contentX, 0)
            morePlaceable.placeRelative(moreX, 0)
        }
    }
}