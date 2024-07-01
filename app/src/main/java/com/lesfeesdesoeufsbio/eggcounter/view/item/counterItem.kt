package com.lesfeesdesoeufsbio.eggcounter.view.item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lesfeesdesoeufsbio.eggcounter.model.EggNumber
import com.lesfeesdesoeufsbio.eggcounter.model.EggSize
import com.lesfeesdesoeufsbio.eggcounter.viewModel.MainViewModel


@Composable
fun CounterItem(
    eggNumber: EggNumber,
    eggSize: EggSize,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel()
) {
    val daySale by mainViewModel.daySale.collectAsState()
    val animatedSize by animateDpAsState(
        targetValue = if (daySale.getNumberSaleForType(eggNumber, eggSize) != 0) 30.dp else 0.dp,
        label= "animatedSize"
    )
    Card(
        modifier = modifier
            .height(150.dp + animatedSize)
    ) {

        Column(
            modifier = modifier
                .padding(2.dp)
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = "Quantité: ${eggNumber.nb} Taille: ${eggSize.size}",
                textAlign = TextAlign.Center
            )
            Text(text = daySale.getNumberSaleForType(eggNumber, eggSize).toString(),
                fontSize = 36.sp)
            AnimatedVisibility(
                modifier = Modifier.height(animatedSize),
                visible = (animatedSize != 0.dp)
            ) {
                Text(text = " %.2f€".format(daySale.getTotalForType(eggNumber, eggSize)))

            }
            Row {
                Button(
                    onClick = { mainViewModel.counterRemove(eggNumber, eggSize) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336)),
                    modifier = Modifier.weight(.5f),
                            contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        modifier = Modifier,
                        color = Color.White,
                        text = "-",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { mainViewModel.counterAdd(eggNumber, eggSize) },
                    modifier = Modifier.weight(.5f),
                    contentPadding = PaddingValues(1.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4)),

                    ) {
                    Text(
                        color = Color.White,
                        text = "+",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}