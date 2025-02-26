package org.shu.tool.password.ui.page.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.shu.tool.password.base.db.PasswordRecordDao
import org.shu.tool.password.base.module.PasswordRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.StateFlow

class DetailViewModel(
    private val dao: PasswordRecordDao
) : ViewModel() {
    private var _pageRecord = MutableStateFlow<PasswordRecord?>(null)
    var pageRecord: StateFlow<PasswordRecord?> = _pageRecord

    fun obtainRecordById(id: Long?) {
        if (id == null) {
            _pageRecord.value = null
        } else {
            viewModelScope.launch {
                _pageRecord.value = dao.getRecordById(id)
            }
        }
    }

    fun insertRecord(record: PasswordRecord) {
        viewModelScope.launch {
            dao.insert(record)
        }

    }
}