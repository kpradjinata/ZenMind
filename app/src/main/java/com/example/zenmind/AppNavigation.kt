package com.example.zenmind

import androidx.compose.material.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
// Import other components as necessary


//import androidx.compose.material3.BottomNavigation
//import androidx.compose.material3.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings


import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object Meditation : NavigationItem("meditation", Icons.Default.Home, "Meditation")
    object Hydration : NavigationItem("hydration", Icons.Default.Settings, "Hydration")
    object Sleep : NavigationItem("sleep", Icons.Default.Home, "Sleep")
    object Lifestyle : NavigationItem("lifestyle", Icons.Default.Settings, "Lifestyle") // New
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Meditation.route) {
        composable(NavigationItem.Meditation.route) {
            MeditationContent() // Make sure this composable is accessible
        }
        composable(NavigationItem.Hydration.route) {
            // Placeholder or the actual composable for the hydration screen
            HydrationRecommendationEnhanced()
        }
        composable(NavigationItem.Sleep.route) {
            SleepRecommendation(viewModel = viewModel()) {
                // Assuming you have navController available here
                navController.navigate(NavigationItem.Lifestyle.route)
            }
        }
        composable(NavigationItem.Lifestyle.route) {
            LifestyleScorePage() // Ensure this composable is defined and ready to display the scores
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem.Meditation,
        NavigationItem.Hydration,
        NavigationItem.Sleep,
        NavigationItem.Lifestyle // Make sure this is included
    )
    BottomNavigation {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = navController.currentDestination?.route == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Avoid multiple copies of the same destination in the stack
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}