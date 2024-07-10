package com.lesfeesdesoeufsbio.eggcounter.view

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
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
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = daySale.name,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            onValueChange = {text->mainViewModel.setName(text)},
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent
            ),

            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 18.sp

            ),
            singleLine = true,

        )
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
                            .focusable().focusRequester(FocusRequester.Default).onFocusChanged {}
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