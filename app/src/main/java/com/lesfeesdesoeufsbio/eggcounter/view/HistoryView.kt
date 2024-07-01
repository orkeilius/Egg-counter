package com.lesfeesdesoeufsbio.eggcounter.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lesfeesdesoeufsbio.eggcounter.model.DaySale
import com.lesfeesdesoeufsbio.eggcounter.view.item.HistorytItem
import com.lesfeesdesoeufsbio.eggcounter.viewModel.MainViewModel

@Composable
fun HistoryView(modifier: Modifier = Modifier, mainViewModel: MainViewModel = viewModel()) {
    val daySales by mainViewModel.daySales.collectAsState()


    Column(modifier =  modifier) {
        LazyColumn {
            items(
                daySales,
            ) { daySale ->
                HistorytItem(daySale= daySale)
            }
        }
    }

}


