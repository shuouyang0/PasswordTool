package org.shu.tool.password.ui.page.home

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import org.shu.tool.password.base.module.PasswordRecord
import org.shu.tool.password.base.repo.PasswordRecordRepository

class HomeViewModel(
    private val repo: PasswordRecordRepository
) : ViewModel() {
    fun obtainAllRecord(): Pager<Int, PasswordRecord> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, // 每次加载20条记录
                enablePlaceholders = false // 关闭占位符
            ),
            pagingSourceFactory = {  repo.obtainPasswordRecords() }
        )// 使用缓存以避免重新加载
    }

}