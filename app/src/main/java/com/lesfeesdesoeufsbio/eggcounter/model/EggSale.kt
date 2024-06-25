package com.lesfeesdesoeufsbio.eggcounter.model

import com.lesfeesdesoeufsbio.eggcounter.utils.TimeHelper
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
class EggSale(
    val number: EggNumber,
    val size: EggSize,
    val time : LocalDateTime = TimeHelper.getCurrentLocalDateTime()
){}

@Serializable
enum class EggNumber (val nb : Int){
    six(6), twelve(12), thirty(30)
}

@Serializable
enum class EggSize(val size : Char){
    small('S'), medium('M'), large('L')
}