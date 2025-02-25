package org.shu.tool.password.base.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import org.shu.tool.password.base.module.PasswordRecord

@Dao
interface PasswordRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: PasswordRecord)

    @Update
    suspend fun update(record: PasswordRecord)

    @Upsert
    suspend fun upsert(record: PasswordRecord)

    @Delete
    suspend fun delete(record: PasswordRecord)

    @Query("SELECT * FROM password_record WHERE id = :id")
    suspend fun getRecordById(id: String): PasswordRecord?

    @Query("""
        SELECT * FROM password_record
        ORDER BY COALESCE(modifyDate, registerDate) DESC
    """)
    suspend fun getPasswordRecords(): List<PasswordRecord>

    @Transaction
    @Query("SELECT * FROM password_record WHERE " +
            "websiteLink LIKE :key OR " +
            "account LIKE :key OR " +
            "nickname LIKE :key OR " +
            "username LIKE :key OR " +
            "remark LIKE :key")
    suspend fun getPasswordRecordsByKeyword(key: String):List<PasswordRecord>
}
