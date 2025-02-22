package org.shu.tool.password.base.di

import com.ctrip.sqllin.driver.DatabaseConfiguration
import com.ctrip.sqllin.dsl.Database
import org.koin.dsl.module
import org.shu.tool.password.databasePath
import org.shu.tool.password.base.db.PasswordRecordDao


val DataBaseModule = module {
    single { createDatabase() }
    single { PasswordRecordDao(get()) }
}


private fun createDatabase() = Database(
    DatabaseConfiguration(
        name = "password.db",
        path = databasePath,
        version = 1,
        create = {
            // You must write SQL to String when the database is created or upgraded
            it.execSQL("""
                CREATE TABLE password_record (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                    websiteLink VARCHAR(255) NOT NULL,
                    account VARCHAR(255) NOT NULL,
                    accountType INT NOT NULL DEFAULT 0,
                    passwordType INT NOT NULL,
                    cipher VARCHAR(255) NOT NULL,
                    registerDate BIGINT NOT NULL,
                    nickname VARCHAR(255),
                    username VARCHAR(255),
                    modifyDate BIGINT,
                    remark TEXT
                );
            """.trimIndent())
        },
//        upgrade = { _, _, _ ->
//            // You must write SQL to String when the database is created or upgraded
//        }
    ),
    enableSimpleSQLLog = true,
)
