package org.shu.tool.password

import org.shu.tool.password.base.db.AppDatabase

import org.koin.core.scope.Scope

interface Platform {
    val name: String
}

expect fun getPlatform(scope: Scope): Platform

/**
 * 用于注入平台相关的模块
 */
expect fun getDatabase(scope: Scope): AppDatabase

