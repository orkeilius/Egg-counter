package com.lesfeesdesoeufsbio.eggcounter.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lesfeesdesoeufsbio.eggcounter.model.EggNumber
import com.lesfeesdesoeufsbio.eggcounter.model.EggSize
import com.lesfeesdesoeufsbio.eggcounter.view.item.CounterItem
import com.lesfeesdesoeufsbio.eggcounter.viewModel.MainViewModel

@Composable
fun MainView(modifier: Modifier = Modifier, mainViewModel: MainViewModel = viewModel()) {
    val daySale by mainViewModel.currentDaySale.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        for (quantity in EggNumber.entries) {
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f)
                , verticalAlignment = Alignment.CenterVertically
            ) {
                for (size in EggSize.entries) {
                    CounterItem(
                        quantity, size,
                        Modifier
                            .weight(0.25f)
                            .padding(6.dp)
                    )
                }
            }

        }
        Text(
            text = "Total : %.2f €".format(daySale.getTotal()),
            fontSize = 34.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()

        )

    }
}