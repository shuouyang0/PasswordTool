package org.shu.tool.password.ui.page.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.coroutines.launch
import org.shu.tool.password.base.db.PasswordRecordDao
import org.shu.tool.password.base.module.PasswordRecord

class HomeViewModel(
    private val dao: PasswordRecordDao
) : ViewModel() {
    fun obtainAllRecord(): Pager<Int, PasswordRecord> {
        return Pager(config = PagingConfig(
            pageSize = 20, // 每次加载20条记录
            enablePlaceholders = false // 关闭占位符
        ), pagingSourceFactory = { dao.getKeyRecords() })// 使用缓存以避免重新加载
    }

    fun updateRecord(record: PasswordRecord) {
        viewModelScope.launch {
            dao.upsert(record)
        }
    }


    fun searchRecord(key: String): Pager<Int, PasswordRecord> {
        return if (key.isBlank()) {
            obtainAllRecord()
        } else {
            Pager(config = PagingConfig(
                pageSize = 20, // 每次加载20条记录
                enablePlaceholders = false // 关闭占位符
            ), pagingSourceFactory = { dao.getKeyRecordsByKeyword("%${key}%") })
        }
    }

    fun deleteRecord(record: PasswordRecord) {
        viewModelScope.launch {
            dao.delete(record)
        }
    }

}