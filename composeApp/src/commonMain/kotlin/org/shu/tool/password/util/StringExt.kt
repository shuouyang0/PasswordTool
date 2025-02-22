package org.shu.tool.password.util

import io.ktor.http.Url
import io.ktor.utils.io.core.toByteArray
import org.kotlincrypto.hash.sha2.SHA256
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


fun String?.isEmail(): Boolean {
    if (this.isNullOrBlank()) return false
    val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
    return regex.matches(this)
}

fun String?.maskEmail(): String {
    if (this == null || !this.isEmail()) return ""
    val atIndex = this.indexOf('@')
    val prefix: String = if (atIndex < 2) {
        this.substring(0, atIndex)
    } else {
        this.substring(0, 2) + "***"
    }
    val domain = this.substring(atIndex)
    return "$prefix$domain"
}
fun String?.isPhoneNumber(): Boolean {
    if (this.isNullOrBlank()) return false
    return this.matches("""\d{10,15}""".toRegex())
}

fun String?.maskPhoneNumber(): String {
    if (this == null || !this.isPhoneNumber()) return  ""
    return if (this.length >= 8) {
        this.take(3) + "****" + this.takeLast(4)
    } else {
        this.take(3) + "****"
    }
}
fun String?.isURL():Boolean{
    val str = this ?: return false
    return try {
        val url = Url(str)
        url.host.isNotBlank() && url.protocol.name.isNotEmpty()
    } catch (e: Exception) {
        false
    }
}

@OptIn(ExperimentalEncodingApi::class)
fun String.to_SHA256_BASE64(): String {
    val digest = SHA256().digest(this.toByteArray())
   return Base64.encode(digest)
}