package com.lesfeesdesoeufsbio.eggcounter.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lesfeesdesoeufsbio.eggcounter.view.item.HistorytItem
import com.lesfeesdesoeufsbio.eggcounter.viewModel.HistoryViewModel

@Composable
fun HistoryView(modifier: Modifier = Modifier, historyViewModel: HistoryViewModel = viewModel()) {
    val daySales by historyViewModel.daySales.collectAsState()

    Column(modifier =  modifier) {
        LazyColumn(
            modifier = Modifier.wrapContentHeight()
        ) {
            items(
                daySales,
            ) { daySale ->
                HistorytItem(daySale= daySale)
            }
        }
    }

}


