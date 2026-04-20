package com.example.ser210finalproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ser210finalproject.ui.screens.AppDestination
import com.example.ser210finalproject.ui.screens.LoginScreen
import com.example.ser210finalproject.ui.screens.MarketplaceScreen
import com.example.ser210finalproject.ui.screens.ProfileScreen
import com.example.ser210finalproject.ui.screens.SellerScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(onLoginClick = {
                navController.navigate(AppDestination.Marketplace.route) {
                    popUpTo("login") { inclusive = true }
                }
            })
        }

        composable(AppDestination.Marketplace.route) {
            MarketplaceScreen(onNavigate = { destination ->
                navigateToMainScreen(navController, destination)
            })
        }

        composable(AppDestination.Profile.route) {
            ProfileScreen(onNavigate = { destination ->
                navigateToMainScreen(navController, destination)
            })
        }

        composable(AppDestination.Seller.route) {
            SellerScreen(onNavigate = { destination ->
                navigateToMainScreen(navController, destination)
            })
        }
    }
}

private fun navigateToMainScreen(
    navController: NavHostController,
    destination: AppDestination
) {
    navController.navigate(destination.route) {
        launchSingleTop = true
        restoreState = true
        popUpTo(AppDestination.Marketplace.route) {
            saveState = true
        }
    }
}
