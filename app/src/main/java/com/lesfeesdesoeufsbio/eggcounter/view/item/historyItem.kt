package com.lesfeesdesoeufsbio.eggcounter.view.item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lesfeesdesoeufsbio.eggcounter.model.DaySale
import com.lesfeesdesoeufsbio.eggcounter.model.EggNumber
import com.lesfeesdesoeufsbio.eggcounter.model.EggSize
import com.lesfeesdesoeufsbio.eggcounter.viewModel.HistoryViewModel

@Preview
@Composable
fun HistorytItem(
    modifier: Modifier = Modifier,
    daySale: DaySale = DaySale(),
    historyViewModel: HistoryViewModel = viewModel()
) {
    val openDate by historyViewModel.openDate.collectAsState()

    val visible = (openDate == daySale.date.toEpochDays())
    val density = LocalDensity.current
    val animatedSize by animateDpAsState(
        targetValue = if (visible) 350.dp else 0.dp,
        label = "alpha"
    )



    Card(
        onClick = { historyViewModel.openCard(daySale.date.toEpochDays()) },
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column() {

            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = daySale.date.toString(),
                    fontSize = 28.sp,
                )
                Column {
                    EggSize.entries.forEach { size ->
                        Row {
                            Text(text = daySale.getEggNumberForSize(size).toString())
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(text = size.name)

                        }
                    }
                }
                Button(
                    onClick = {},
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowForward,
                        contentDescription = "open",

                        )
                }
            }
        }
        AnimatedVisibility(
            modifier = Modifier.height(animatedSize),
            visible = visible
        ) {
            ExpendedDaySaleItem(daySale = daySale)
        }
    }
}

@Composable
fun ExpendedDaySaleItem(modifier: Modifier = Modifier, daySale: DaySale) {
    Column {

        Card(
            modifier = modifier
                .wrapContentHeight()
                .padding(8.dp)
                .weight(1f)
                .border(
                    BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
                    RoundedCornerShape(8.dp)
                )
        ) {
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
                    ) {
                        for (size in EggSize.entries) {
                            EggInfo(
                                Modifier
                                    .weight(0.25f)
                                    .padding(6.dp),
                                daySale,
                                size,
                                quantity,
                            )
                        }
                    }
                }
            }
        }
        Text(

            text = "Total : %.2f €".format(daySale.getTotal()),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 4.dp)
                //.weight(1f)

        )
    }
}

@Composable
fun EggInfo(modifier: Modifier, daySale: DaySale, eggSize: EggSize, eggNumber: EggNumber) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "%d - %s".format(eggNumber.nb, eggSize.size.toString())
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "%d vente(s)".format(daySale.getNumberSaleForType(eggNumber, eggSize))
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "%.2f €".format(daySale.getTotalForType(eggNumber, eggSize))
        )
    }
}