package org.shu.tool.password

import com.ctrip.sqllin.driver.DatabasePath

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect val databasePath: DatabasePath
