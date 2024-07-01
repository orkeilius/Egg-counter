package com.lesfeesdesoeufsbio.eggcounter.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lesfeesdesoeufsbio.eggcounter.utils.AppLifecycleObserver
import com.lesfeesdesoeufsbio.eggcounter.view.ui.theme.EggCounterTheme
import com.lesfeesdesoeufsbio.eggcounter.viewModel.MainViewModel
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appLifecycleObserver = AppLifecycleObserver(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)


        enableEdgeToEdge()
        setContent {

            EggCounterTheme {
               Router()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Router(){
    val navItem = listOf(
        NavItem("today",Icons.Default.Egg, {arg -> MainView(arg) },0),
        NavItem("history", Icons.Default.History, { arg -> HistoryView(arg) },1)
    )

    val pagerState = rememberPagerState(pageCount = {
        navItem.count()
    })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            NavigationBar(Modifier) {
                navItem.forEach { item ->
                    NavigationBarItem(icon = {
                        Icon(
                            item.icon, contentDescription = item.label
                        )
                    },
                        label = { Text(item.label) },
                        selected = pagerState.currentPage == item.pos,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(item.pos)
                            }
                        })
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(state = pagerState) { page ->
            val item = navItem[page]
            item.route.invoke(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }
    }
}

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: @Composable ((Modifier) -> Unit),
    val pos : Int
)