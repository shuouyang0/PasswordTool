package org.shu.tool.password

import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.room.RoomDatabase
import org.shu.tool.password.base.common.DATABASE_FILENAME
import org.shu.tool.password.base.db.AppDatabase
import org.koin.core.scope.Scope

class AndroidPlatform(context: Context) : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT} - context:$context "
}

actual fun getPlatform(scope: Scope): Platform = AndroidPlatform(scope.get())


actual fun getDatabaseBuilder(scope: Scope): RoomDatabase.Builder<AppDatabase>  {
    val appContext = scope.get<Context>()
    val dbFile = appContext.getDatabasePath(DATABASE_FILENAME)

    return  Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}