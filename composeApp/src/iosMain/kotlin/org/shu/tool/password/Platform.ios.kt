package org.shu.tool.password

import platform.UIKit.UIDevice
import com.ctrip.sqllin.driver.DatabasePath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask
import com.ctrip.sqllin.driver.toDatabasePath

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()


actual val databasePath: DatabasePath
    get() {
        val stringPath = (NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, true).firstOrNull() as? String ?: "")
        return stringPath.toDatabasePath()
    }