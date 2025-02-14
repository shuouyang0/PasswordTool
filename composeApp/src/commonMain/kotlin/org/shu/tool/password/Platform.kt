package org.shu.tool.password

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform