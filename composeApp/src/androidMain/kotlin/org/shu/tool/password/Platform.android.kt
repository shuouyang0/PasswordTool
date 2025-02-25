package org.shu.tool.password

import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.shu.tool.password.base.common.DATABASE_FILENAME
import org.shu.tool.password.base.db.AppDatabase
import org.koin.core.scope.Scope

class AndroidPlatform(context: Context) : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT} - context:$context "
}

actual fun getPlatform(scope: Scope): Platform = AndroidPlatform(scope.get())


actual fun getDatabase(scope: Scope): AppDatabase {
    return Room.databaseBuilder(scope.get(), AppDatabase::class.java, DATABASE_FILENAME)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}