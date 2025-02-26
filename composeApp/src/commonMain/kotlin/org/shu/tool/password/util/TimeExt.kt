package org.shu.tool.password.util

import kotlinx.datetime.Clock

object TimeExt{
    fun now():Long = Clock.System.now().toEpochMilliseconds()
}