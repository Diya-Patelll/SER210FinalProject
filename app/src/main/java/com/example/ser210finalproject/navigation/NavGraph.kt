package com.example.ser210finalproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ser210finalproject.ui.screens.LoginScreen
import com.example.ser210finalproject.ui.screens.MarketplaceScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(onLoginClick = {
                navController.navigate("marketplace")
            })
        }

        composable("marketplace"){
            MarketplaceScreen()
        }
        // 3 other screens go here
    }
}