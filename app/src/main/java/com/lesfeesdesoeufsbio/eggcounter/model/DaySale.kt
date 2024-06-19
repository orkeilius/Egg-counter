package com.lesfeesdesoeufsbio.eggcounter.model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.serialization.Serializable
import kotlinx.datetime.toLocalDateTime

@Serializable
class DaySale(

    val date: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    private var sales: ArrayList<EggSale> = arrayListOf()
) {

    fun getSales():ArrayList<EggSale>{
        return sales
    }

    fun addSale(sale : EggSale){
        sales.add(sale);
    }

}