package com.lesfeesdesoeufsbio.eggcounter.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TimeHelper {

    companion object {
        fun getCurrentLocalDateTime(): LocalDateTime{
            return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        }
    }

}