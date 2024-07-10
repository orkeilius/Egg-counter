package com.lesfeesdesoeufsbio.eggcounter.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.lesfeesdesoeufsbio.eggcounter.model.DaySale
import com.lesfeesdesoeufsbio.eggcounter.model.DaySaleReposytory
import com.lesfeesdesoeufsbio.eggcounter.model.EggNumber
import com.lesfeesdesoeufsbio.eggcounter.model.EggSale
import com.lesfeesdesoeufsbio.eggcounter.model.EggSize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val daySaleRepository = DaySaleReposytory.getInstance(application.applicationContext)

    @SuppressLint("StaticFieldLeak")
    val context: Context = application.applicationContext

    private val _daySale = MutableStateFlow(DaySale())
    val currentDaySale = _daySale.asStateFlow()

    private val _daySales = MutableStateFlow(arrayListOf<DaySale>())
    val daySales = _daySales.asStateFlow()

    init {
        _daySale.value = daySaleRepository.getDay()
        _daySales.value = daySaleRepository.getAllDay()
    }

    fun counterAdd(eggNumber: EggNumber,eggSize: EggSize){
        val newState = currentDaySale.value.deepcopy()
        val newSale = EggSale(eggNumber, eggSize)
        newState.addSale(newSale)
        updateCurrentDaySale(newState)

        checkWarningPriceNotSet(newSale)
    }
    fun counterRemove(eggNumber: EggNumber,eggSize: EggSize){
        val newState = currentDaySale.value.deepcopy()
        newState.removeSale(eggNumber,eggSize)
        updateCurrentDaySale(newState)
    }

    fun setName(name:String){
        val newState = currentDaySale.value.deepcopy()
        updateCurrentDaySale(newState.copy(name=name))
    }

    fun updateCurrentDaySale(newDaySale: DaySale) {
        daySaleRepository.save(newDaySale)
        _daySale.update { newDaySale }

        _daySales.update {
            (arrayListOf(newDaySale) + it.filter { it.date != currentDaySale.value.date }) as ArrayList<DaySale>
        }
    }

    private fun checkWarningPriceNotSet(eggSale: EggSale){
        if (eggSale.getPrice() == 0f){
            Toast.makeText(context,"Prix non ajouté",Toast.LENGTH_SHORT).show()
        }
    }

    // history page

    private val _openDate = MutableStateFlow(-1)
    var openDate = _openDate.asStateFlow()

    fun openCard(date: Int) {
        if (openDate.value == date) {
            _openDate.update { -1 }
        } else {
            _openDate.update { date }
        }
    }


}