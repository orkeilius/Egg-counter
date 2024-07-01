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

    private val daySaleReposytory = DaySaleReposytory.getInstance(application.applicationContext)
    private val _uiState = MutableStateFlow(DaySale())
    val daySale= _uiState.asStateFlow()
    @SuppressLint("StaticFieldLeak")
    val context: Context = application.applicationContext

    init {
        _uiState.value = daySaleReposytory.getDay()
    }

    fun counterAdd(eggNumber: EggNumber,eggSize: EggSize){
        val newState = daySale.value.deepcopy()
        val newSale = EggSale(eggNumber,eggSize)
        newState.addSale(newSale)
        daySaleReposytory.save(newState)
        _uiState.update { newState }

        checkWarningPriceNotSet(newSale)
    }
    fun counterRemove(eggNumber: EggNumber,eggSize: EggSize){
        val newState = daySale.value.deepcopy()
        newState.removeSale(eggNumber,eggSize)
        daySaleReposytory.save(newState)
        _uiState.update { newState }
    }

    private fun checkWarningPriceNotSet(eggSale: EggSale){
        if (eggSale.getPrice() == 0f){
            Toast.makeText(context,"Prix non ajouté",Toast.LENGTH_SHORT).show()
        }
    }

}