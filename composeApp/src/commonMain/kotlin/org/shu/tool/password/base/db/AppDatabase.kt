package org.shu.tool.password.base.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.scope.Scope
import org.shu.tool.password.AppDatabaseConstructor
import org.shu.tool.password.base.common.DATABASE_VERSION
import org.shu.tool.password.base.module.PasswordRecord
import org.shu.tool.password.getDatabaseBuilder

@Database(entities = [PasswordRecord::class], version = DATABASE_VERSION)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase:RoomDatabase() {
    abstract fun passwordRecordDao(): PasswordRecordDao
}

fun getRoomDatabase(
    scope: Scope
): AppDatabase {
    return getDatabaseBuilder(scope)
//        .addMigrations(MIGRATIONS)
//        .fallbackToDestructiveMigrationOnDowngrade()
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}