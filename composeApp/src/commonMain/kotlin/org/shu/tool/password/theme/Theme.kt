package org.shu.tool.password.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * ## ColorScheme 中主要颜色及其用途
 * - primary: 主要品牌颜色，用于按钮、浮动操作按钮等突出元素。
 * - onPrimary: 在主要颜色背景上使用的文本和图标颜色。
 * - primaryContainer: 用于主要颜色的容器，如卡片或对话框。
 * - onPrimaryContainer: 在主要颜色容器背景上使用的文本和图标颜色。
 * - secondary: 次要品牌颜色，用于不太突出的元素。
 * - onSecondary: 在次要颜色背景上使用的文本和图标颜色。
 * - secondaryContainer: 用于次要颜色的容器。
 * - onSecondaryContainer: 在次要颜色容器背景上使用的文本和图标颜色。
 * - tertiary: 第三品牌颜色，用于进一步区分元素。
 * - onTertiary: 在第三颜色背景上使用的文本和图标颜色。
 * - tertiaryContainer: 用于第三颜色的容器。
 * - onTertiaryContainer: 在第三颜色容器背景上使用的文本和图标颜色。
 * - error: 用于指示错误状态的颜色，如错误消息或无效输入。
 * - onError: 在错误颜色背景上使用的文本和图标颜色。
 * - errorContainer: 用于错误状态的容器。
 * - onErrorContainer: 在错误状态容器背景上使用的文本和图标颜色。
 * - background: 屏幕背景颜色。
 * - onBackground: 在背景颜色上使用的文本和图标颜色。
 * - surface: 表面颜色，如卡片、列表项和对话框。
 * - onSurface: 在表面颜色上使用的文本和图标颜色。
 * - surfaceVariant: 表面的替代颜色，用于提供微妙的对比度。
 * - onSurfaceVariant: 在表面替代颜色上使用的文本和图标颜色。
 * - outline: 用于创建轮廓或分隔线的颜色。
 * - inverseOnSurface: 在反转表面颜色上使用的文本和图标颜色。
 * - inverseSurface: 反转表面颜色，用于在深色主题中创建对比度。
 * - inversePrimary: 在反转主题中使用的主要颜色。
 */
private val DarkColorScheme = darkColorScheme(
    background = Color(0xFF26282a), //最外围的大背景的颜色
    onBackground = Color(0xFF323438), // 大背景上被选中的容器的背景色

    surface = Color(0xFFd9dce0), //直接位于大背景的文字的颜色
    onSurface = Color(0xFFd9dce0), //直接位于大背景的文字被选中的颜色
    onSurfaceVariant = Color(0xFF505358), //

    secondaryContainer = Color(0xFF26271f), //次要背景颜色
    onSecondaryContainer = Color(0xFF3b3d41), //次要背景选中时的颜色
    secondary = Color(0xFFa2a5a9), //次要背景之上的文字的颜色
    onSecondary = Color(0xFFa2a5a9), //次要背景之上的文字的颜色

    primary = Color(0xFFd8dbdf),
    primaryContainer = Color(0xFF26282a),
    onPrimary = Color(0xFFFFFFFF),
    onPrimaryContainer = Color(0xFF2370e6),

    error = Color(0xFFe74c3c),
    tertiary = Color(0xFF2ecc71),
)

private val LightColorScheme = lightColorScheme(
    background = Color(0xFFf6f7f9), //最外围的大背景的颜色
    onBackground = Color(0xFFe9eaef), // 大背景上被选中的容器的背景色

    surface = Color(0xFF303030), //直接位于大背景的文字的颜色
    onSurface = Color(0xFF303030), //直接位于大背景的文字被选中的颜色
    onSurfaceVariant = Color(0xFFa7aebb),

    secondaryContainer = Color(0xFFFFFFFF), //次要背景颜色
    onSecondaryContainer = Color(0xFFdadde1), //次要背景选中时的颜色
    secondary = Color(0xFF212121), //次要背景之上的文字的颜色
    onSecondary = Color(0xFF212121), //次要背景之上的文字的颜色

    primary = Color(0xFF000000),
    primaryContainer = Color(0xFFFFFFFF),
    onPrimary = Color(0xFFFFFFFF),
    onPrimaryContainer = Color(0xFF2370e6),

    error = Color(0xFFe74c3c),
    tertiary = Color(0xFF2ecc71),
)

@Composable
fun PasswordToolTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme){
        DarkColorScheme
    }else{
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
