package org.shu.tool.password

import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import org.shu.tool.password.base.db.AppDatabase

import org.koin.core.scope.Scope

interface Platform {
    val name: String
}

expect fun getPlatform(scope: Scope): Platform

/**
 * 用于注入平台相关的模块
 */
expect fun getDatabaseBuilder(scope: Scope): RoomDatabase.Builder<AppDatabase>

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
