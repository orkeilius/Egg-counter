package com.lesfeesdesoeufsbio.eggcounter.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lesfeesdesoeufsbio.eggcounter.model.EggNumber
import com.lesfeesdesoeufsbio.eggcounter.model.EggSize
import com.lesfeesdesoeufsbio.eggcounter.viewModel.MainViewModel

@Composable
fun SettingsView(modifier: Modifier = Modifier, mainViewModel: MainViewModel = viewModel()) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Prix",
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        for (size in EggSize.entries) {
            Text(
                text = "Taille ${size.size}",
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp)
                    .align(Alignment.Start)
            )
            for (quantity in EggNumber.entries) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "${quantity.nb} œufs", fontSize = 18.sp)

                    var priceText by remember { 
                        mutableStateOf(mainViewModel.priceRepository.getPrice(quantity, size).toString()) 
                    }

                    OutlinedTextField(
                        value = priceText,
                        onValueChange = { newValue ->
                            priceText = newValue
                            val parsedValue = newValue.replace(",", ".")
                            val newPrice = parsedValue.toFloatOrNull()
                            if (newPrice != null) {
                                mainViewModel.priceRepository.setPrice(quantity, size, newPrice)
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.width(120.dp),
                        singleLine = true,
                        placeholder = { Text("Prix") }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}
