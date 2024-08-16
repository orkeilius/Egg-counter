package com.lesfeesdesoeufsbio.eggcounter.model

import com.lesfeesdesoeufsbio.eggcounter.utils.TimeHelper
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
class EggSale(
    val number: EggNumber,
    val size: EggSize,

    //keep to work with older saved data
    val time : LocalDateTime = TimeHelper.getCurrentLocalDateTime()


){

    fun getPrice():Float{
        when(size){
            EggSize.small -> {
                return when(number){
                    EggNumber.six -> 2f
                    EggNumber.twelve -> 3.50f
                    EggNumber.thirty -> 7f
                }
            }
            EggSize.medium -> {
                return when(number){
                    EggNumber.six -> 2.50f
                    EggNumber.twelve -> 4.50f
                    EggNumber.thirty -> 11f
                }
            }
            EggSize.large ->{
                return when(number){
                    EggNumber.six -> 3f
                    EggNumber.twelve -> 5.5f
                    EggNumber.thirty -> 0f
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