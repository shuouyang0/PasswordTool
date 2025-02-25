package org.shu.tool.password.base.repo

import io.ktor.client.HttpClient

class PasswordRecordRemoteSource(
    private val httpClient: HttpClient
) {
}