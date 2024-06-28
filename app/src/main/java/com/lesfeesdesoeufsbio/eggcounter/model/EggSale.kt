package com.lesfeesdesoeufsbio.eggcounter.model

import com.lesfeesdesoeufsbio.eggcounter.utils.TimeHelper
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
class EggSale(
    val number: EggNumber,
    val size: EggSize,
    val time : LocalDateTime = TimeHelper.getCurrentLocalDateTime()


){

    fun getPrice():Float{
        when(size){
            EggSize.small -> return 0f
            EggSize.medium -> {
                when(number){
                    EggNumber.six -> return 2.50f
                    EggNumber.twelve -> return 4.50f
                    EggNumber.thirty -> return 11f
                }
            }
            EggSize.large ->{
                when(number){
                    EggNumber.six -> return 3f
                    EggNumber.twelve -> return 5.5f
                    EggNumber.thirty -> return 0f
                }

            }
        }
    }
}

@Serializable
enum class EggNumber (val nb : Int){
    six(6), twelve(12), thirty(30)
}

@Serializable
enum class EggSize(val size : Char){
    small('S'), medium('M'), large('L')
}