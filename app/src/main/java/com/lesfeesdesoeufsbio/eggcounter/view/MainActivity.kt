package com.lesfeesdesoeufsbio.eggcounter.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lesfeesdesoeufsbio.eggcounter.utils.AppLifecycleObserver
import com.lesfeesdesoeufsbio.eggcounter.view.ui.theme.EggCounterTheme


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

@Composable
fun Router(navController: NavHostController = rememberNavController()){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navItem = listOf(
        NavItem("today",Icons.Default.Egg, {arg -> MainView(arg) },0),
        NavItem("history", Icons.Default.History, { arg -> HistoryView(arg) },1)
    )

    val currentPos:Int = navItem.fold(0){acc,item -> if (item.label == currentRoute) item.pos else acc }

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
                        selected = currentRoute == item.label,
                        onClick = {
                            navController.navigate(item.label) {
                                //popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        })
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = navItem[0].label,
            Modifier.padding(innerPadding)
        ) {
            navItem.forEach { item ->

                composable(item.label, enterTransition = {
                    return@composable slideIntoContainer(getAnimation(currentPos), tween(700)
                        )
                }, exitTransition = {
                    return@composable slideOutOfContainer(getAnimation(currentPos), tween(700))
                }) {
                    item.route.invoke(
                        Modifier
                            .padding()
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}


fun getAnimation(currentPos:Int): AnimatedContentTransitionScope.SlideDirection {
    if (currentPos == 1){
        return AnimatedContentTransitionScope.SlideDirection.Start // vers gauche
    } else {
        return AnimatedContentTransitionScope.SlideDirection.End // go droite
    }
}

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: @Composable ((Modifier) -> Unit),
    val pos : Int
)