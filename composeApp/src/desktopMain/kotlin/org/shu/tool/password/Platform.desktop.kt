package org.shu.tool.password

import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.core.scope.Scope
import org.shu.tool.password.base.common.DATABASE_FILENAME
import org.shu.tool.password.base.db.AppDatabase
import java.io.File


class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(scope: Scope): Platform = JVMPlatform()

actual fun getDatabaseBuilder(scope: Scope): RoomDatabase.Builder<AppDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), DATABASE_FILENAME)
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    )
}