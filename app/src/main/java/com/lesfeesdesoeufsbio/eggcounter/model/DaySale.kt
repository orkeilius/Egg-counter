package com.lesfeesdesoeufsbio.eggcounter.model

import com.lesfeesdesoeufsbio.eggcounter.utils.TimeHelper
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class DaySale(

    val date: LocalDate = TimeHelper.getCurrentLocalDateTime().date,
    val sales: MutableList<EggSale> = arrayListOf()
) {


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

    fun deepcopy(): DaySale{
        val list = sales.toMutableList()
        return this.copy(sales = list)

    }


}