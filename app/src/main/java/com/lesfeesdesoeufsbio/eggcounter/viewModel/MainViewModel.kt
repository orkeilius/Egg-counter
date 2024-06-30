package com.lesfeesdesoeufsbio.eggcounter.viewModel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    init {
        _uiState.value = daySaleReposytory.getDay()
    }


    var update by mutableStateOf(false)
        private set



    fun counterAdd(eggNumber: EggNumber,eggSize: EggSize){
        val newState = daySale.value.deepcopy()
        newState.addSale(EggSale(eggNumber,eggSize))
        daySaleReposytory.save(newState)
        _uiState.update { newState }
    }
    fun counterRemove(eggNumber: EggNumber,eggSize: EggSize){
        val newState = daySale.value.deepcopy()
        newState.removeSale(eggNumber,eggSize)
        daySaleReposytory.save(newState)
        _uiState.update { newState }
    }


}