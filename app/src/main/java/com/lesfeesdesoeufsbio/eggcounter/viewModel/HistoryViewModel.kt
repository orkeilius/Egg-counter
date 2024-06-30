package com.lesfeesdesoeufsbio.eggcounter.viewModel

import android.app.Application
import androidx.compose.runtime.remember
import androidx.lifecycle.AndroidViewModel
import com.lesfeesdesoeufsbio.eggcounter.model.DaySale
import com.lesfeesdesoeufsbio.eggcounter.model.DaySaleReposytory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate
import java.util.Date


class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val daySaleReposytory = DaySaleReposytory.getInstance(application.applicationContext)
    private val _uiState = MutableStateFlow(arrayListOf<DaySale>())
    val daySales = _uiState.asStateFlow()

    private val _openDate = MutableStateFlow<Int>(-1)
    var openDate = _openDate.asStateFlow()

    init {
        _uiState.value = daySaleReposytory.getAllDay()
    }


    fun openCard(date : Int){
        if (openDate.value == date){
            _openDate.update{-1}
        }
        else {
            _openDate.update{date}
        }
        }

}