package com.lesfeesdesoeufsbio.eggcounter.model

import com.lesfeesdesoeufsbio.eggcounter.utils.TimeHelper
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.serialization.Serializable
import kotlinx.datetime.toLocalDateTime

@Serializable
class DaySale(

    val date: LocalDate = TimeHelper.getCurrentLocalDateTime().date,
    private var sales: ArrayList<EggSale> = arrayListOf()
) {

    fun getSales():ArrayList<EggSale>{
        return sales
    }

    fun addSale(sale : EggSale){
        sales.add(sale);
    }
    fun removeSale(eggNumber: EggNumber, eggSize: EggSize) {
        for (sale in sales) {
            if (sale.number == eggNumber && sale.size == eggSize) {
                sales.remove(sale)
                return
            }
        }
    }

    fun getNumberSaleForType(eggNumber: EggNumber, eggSize: EggSize): Int {
        var number = 0
        for (sale in sales) {
            if (sale.number == eggNumber && sale.size == eggSize) {
                number++
            }
        }
        return number

    }

    fun getEggNumberForSize(eggSize: EggSize): Int{
        var number = 0
        for (sale in sales){
            if(sale.size == eggSize){
                number += sale.number.nb
            }
        }
        return number
    }

    fun getTotal():Float{
        var number = 0f
        for (sale in sales){
            number += sale.getPrice()
        }
        return number
    }


}