package org.shu.tool.password.base.repo


class PasswordRecordRepository(
    private val _localSource: PasswordRecordLocalSource,
    private val _remoteSource: PasswordRecordRemoteSource
) {
    //TODO:负责协调本地和远程数据，保持同步
    fun obtainPasswordRecords() = _localSource.obtainAll()
}