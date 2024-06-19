package com.lesfeesdesoeufsbio.eggcounter.model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable


@Serializable
class EggSale(
    val number: EggNumber,
    val size: EggSize,
    val time : LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
){}

@Serializable
enum class EggNumber (val nb : Int){
    six(6), twelve(12), thirty(30)
}

@Serializable
enum class EggSize(){
    small, medium, large
}