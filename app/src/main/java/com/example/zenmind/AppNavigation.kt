package com.example.zenmind

import androidx.compose.material.Icon

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info

import androidx.compose.runtime.Composable

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object Meditation : NavigationItem("meditation", Icons.Default.Info, "Meditation")
    object Hydration : NavigationItem("hydration", Icons.Default.Favorite, "Hydration")
    object Sleep : NavigationItem("sleep", Icons.Default.Edit, "Sleep")
    object Lifestyle : NavigationItem("lifestyle", Icons.Default.Home, "Lifestyle") // New
}

@Composable
fun NavigationGraph(navController: NavHostController, userId: String) {
    NavHost(navController, startDestination = NavigationItem.Meditation.route) {
        composable(NavigationItem.Meditation.route) {
            MeditationContent()
        }
        composable(NavigationItem.Hydration.route) {

            HydrationScreen(userId)
        }
        composable(NavigationItem.Sleep.route) {
            SleepRecommendation()
        }
        composable(NavigationItem.Lifestyle.route) {
            LifestyleScorePage()
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem.Meditation,
        NavigationItem.Hydration,
        NavigationItem.Sleep,
        NavigationItem.Lifestyle
    )
    BottomNavigation {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = navController.currentDestination?.route == item.route,
                onClick = {
                    navController.navigate(item.route) {

                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}