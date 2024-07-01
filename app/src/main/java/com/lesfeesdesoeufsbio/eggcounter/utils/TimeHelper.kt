package com.lesfeesdesoeufsbio.eggcounter.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

class TimeHelper {

    companion object {
        fun getCurrentLocalDateTime(): LocalDateTime{
            return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        }


        private val FRENCH_FULL: MonthNames = MonthNames(
            listOf(
                "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre ", "Décembre"
            )
        )
        val customFormat = LocalDate.Format {
            dayOfMonth(); char(' '); monthName(FRENCH_FULL) ; char(' '); year()
        }
    }

}