package org.shu.tool.password.util

object Log {
    private const val TAG = "shuouyang-"
    fun d(tag: String = "", msg: Any?) {
        println("$TAG $tag: $msg")
    }
}