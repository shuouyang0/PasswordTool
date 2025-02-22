package org.shu.tool.password

import android.content.Context
import android.os.Build
import com.ctrip.sqllin.driver.DatabasePath
import com.ctrip.sqllin.driver.toDatabasePath
import org.koin.java.KoinJavaComponent.getKoin

internal val AppContext:Context by getKoin().inject()

actual val databasePath: DatabasePath
    get() = AppContext.toDatabasePath()

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()