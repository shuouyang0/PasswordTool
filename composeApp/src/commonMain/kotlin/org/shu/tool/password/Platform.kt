package org.shu.tool.password

import org.koin.core.module.Module
import org.shu.tool.password.base.db.AppDatabase

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

/**
 * 用于注入平台相关的模块
 */
expect class DiPlatformFactory {
    fun createDatabase(): AppDatabase
}

