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

    @Query("SELECT * FROM key_records WHERE id = :id")
    fun getRecordById(id: String): PasswordRecord?

    @Query("""
        SELECT * FROM key_records
        ORDER BY COALESCE(modifyDate, registerDate) DESC
    """)
    fun getPasswordRecords(): PagingSource<Int, PasswordRecord>

    @Transaction
    @Query("SELECT * FROM key_records WHERE " +
            "websiteLink LIKE :key OR " +
            "account LIKE :key OR " +
            "nickname LIKE :key OR " +
            "username LIKE :key OR " +
            "remark LIKE :key")
    fun getPasswordRecordsByKeyword(key: String): PagingSource<Int, PasswordRecord>
}
