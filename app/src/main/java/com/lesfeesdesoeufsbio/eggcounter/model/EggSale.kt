package com.lesfeesdesoeufsbio.eggcounter.model

import java.time.LocalDateTime

class EggSale(
    val number: EggNumber,
    val size: EggSize,
    val time : LocalDateTime
){}

enum class EggNumber (val nb : Int){
    six(6), twelve(12), thirty(30)
}

enum class EggSize(){
    small, medium, large
}