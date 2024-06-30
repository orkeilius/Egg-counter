package com.lesfeesdesoeufsbio.eggcounter.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.lesfeesdesoeufsbio.eggcounter.model.DaySale
import com.lesfeesdesoeufsbio.eggcounter.model.DaySaleReposytory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val daySaleReposytory = DaySaleReposytory.getInstance(application.applicationContext)
    private val _uiState = MutableStateFlow(arrayListOf<DaySale>())
    val daySales = _uiState.asStateFlow()

    init {
        _uiState.value = daySaleReposytory.getAllDay()
    }

}