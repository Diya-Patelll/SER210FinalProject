package com.example.ser210finalproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ser210finalproject.MarketplaceApplication
import com.example.ser210finalproject.ui.screens.AppDestination
import com.example.ser210finalproject.ui.screens.LoginScreen
import com.example.ser210finalproject.ui.screens.MarketplaceScreen
import com.example.ser210finalproject.ui.screens.ProfileScreen
import com.example.ser210finalproject.ui.screens.SellerScreen
import com.example.ser210finalproject.ui.viewmodel.LoginViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val application = LocalContext.current.applicationContext as MarketplaceApplication
    val loginViewModel = remember(application) {
        LoginViewModel(application.container.itemsRepository)
    }
    var currentUserEmail by remember { mutableStateOf("diya.patel@quinnipiac.edu") }

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                loginViewModel = loginViewModel,
                onLoginSuccess = { email ->
                    currentUserEmail = email
                    navController.navigate(AppDestination.Marketplace.route) {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable(AppDestination.Marketplace.route) {
            MarketplaceScreen(onNavigate = { destination ->
                navigateToMainScreen(navController, destination)
            })
        }

        composable(AppDestination.Profile.route) {
            ProfileScreen(
                onNavigate = { destination ->
                    navigateToMainScreen(navController, destination)
                },
                displayName = loginViewModel.getDisplayNameFromEmail(currentUserEmail),
                email = currentUserEmail
            )
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
