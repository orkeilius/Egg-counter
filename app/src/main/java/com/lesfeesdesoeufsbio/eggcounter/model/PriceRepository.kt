package com.lesfeesdesoeufsbio.eggcounter.model

import android.content.Context
import android.content.SharedPreferences

class PriceRepository private constructor(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("prices", Context.MODE_PRIVATE)

    companion object {
        private var instance: PriceRepository? = null

        fun getInstance(context: Context): PriceRepository {
            if (instance == null) {
                instance = PriceRepository(context.applicationContext)
            }
            return instance!!
        }
    }

    fun getPrice(eggNumber: EggNumber, eggSize: EggSize): Float {
        val key = getKey(eggNumber, eggSize)
        val defaultPrice = getDefaultPrice(eggNumber, eggSize)
        return prefs.getFloat(key, defaultPrice)
    }

    fun setPrice(eggNumber: EggNumber, eggSize: EggSize, price: Float) {
        prefs.edit().putFloat(getKey(eggNumber, eggSize), price).apply()
    }

    private fun getKey(eggNumber: EggNumber, eggSize: EggSize): String {
        return "${eggSize.name}_${eggNumber.name}"
    }

    private fun getDefaultPrice(number: EggNumber, size: EggSize): Float {
        return when(size){
            EggSize.small -> {
                when(number){
                    EggNumber.six -> 2f
                    EggNumber.twelve -> 3.50f
                    EggNumber.thirty -> 7f
                }
            }
            EggSize.medium -> {
                when(number){
                    EggNumber.six -> 2.50f
                    EggNumber.twelve -> 4.50f
                    EggNumber.thirty -> 11f
                }
            }
            EggSize.large ->{
                when(number){
                    EggNumber.six -> 3f
                    EggNumber.twelve -> 5.5f
                    EggNumber.thirty -> 0f
                }
            }
        }
    }
}
