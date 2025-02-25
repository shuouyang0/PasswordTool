package org.shu.tool.password.base.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.shu.tool.password.base.common.DATABASE_VERSION
import org.shu.tool.password.base.module.PasswordRecord

@Database(entities = [PasswordRecord::class], version = DATABASE_VERSION)
abstract class AppDatabase:RoomDatabase() {
    abstract fun passwordRecordDao(): PasswordRecordDao
}