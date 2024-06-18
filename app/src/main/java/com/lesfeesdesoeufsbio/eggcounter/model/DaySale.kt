package com.lesfeesdesoeufsbio.eggcounter.model

import java.time.LocalDate


class DaySale(
    time: LocalDate = LocalDate.now(),
    sales: ArrayList<EggSale> = arrayListOf<EggSale>()
) {
    val time = time
    var sales = sales
        private set

    fun AddSale(sale : EggSale){
        sales.add(sale);
    }

}