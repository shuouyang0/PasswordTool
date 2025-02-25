package org.shu.tool.password

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.koin.core.scope.Scope
import org.shu.tool.password.base.common.DATABASE_FILENAME
import org.shu.tool.password.base.db.AppDatabase
import java.io.File


class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(scope: Scope): Platform = JVMPlatform()

actual fun getDatabase(scope: Scope): AppDatabase {
    val dbFile = File(System.getProperty("java.io.tmpdir"), DATABASE_FILENAME)
    return Room.databaseBuilder<AppDatabase>(name = dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}