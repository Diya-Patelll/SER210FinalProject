package com.example.ser210finalproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ser210finalproject.MarketplaceApplication
import com.example.ser210finalproject.ui.screens.AppDestination
import com.example.ser210finalproject.ui.screens.ListingDetailScreen
import com.example.ser210finalproject.ui.screens.LoginScreen
import com.example.ser210finalproject.ui.screens.MarketplaceScreen
import com.example.ser210finalproject.ui.screens.ProfileScreen
import com.example.ser210finalproject.ui.screens.SellerScreen
import com.example.ser210finalproject.ui.viewmodel.LoginViewModel
import com.example.ser210finalproject.viewmodel.ListingDetailViewModel
import com.example.ser210finalproject.viewmodel.MarketplaceViewModel
import com.example.ser210finalproject.viewmodel.ProfileViewModel
import com.example.ser210finalproject.viewmodel.SellerViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val application = LocalContext.current.applicationContext as MarketplaceApplication
    val itemsRepository = application.container.itemsRepository
    val loginViewModel = remember(application) {
        LoginViewModel(itemsRepository)
    }
    val sellerViewModel = remember(application) {
        SellerViewModel(itemsRepository)
    }
    val marketplaceViewModel = remember(application) {
        MarketplaceViewModel(itemsRepository)
    }
    var currentUserEmail by remember { mutableStateOf("") }

    val profileViewModel = remember(currentUserEmail) {
        ProfileViewModel(itemsRepository, currentUserEmail)
    }
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
                navigateToMainScreen(navController, destination) },
                onListingClick = {id ->
                    navController.navigate("listing_details/$id")},
                viewModel = marketplaceViewModel,
                currentUserEmail = currentUserEmail
            )
        }

        composable(
            route = "listing_details/{listingId}",
            arguments = listOf(navArgument("listingId") { type = NavType.IntType })
        ) { backStackEntry ->
            val listingId = backStackEntry.arguments?.getInt("listingId") ?: 0
            val detailViewModel = remember (listingId){
                ListingDetailViewModel(itemsRepository, listingId, currentUserEmail)
            }
            ListingDetailScreen(
                viewModel = detailViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(AppDestination.Profile.route) {
            ProfileScreen(
                onNavigate = { destination ->
                    navigateToMainScreen(navController, destination)
                },
                profileViewModel = profileViewModel,
                displayName = loginViewModel.getDisplayNameFromEmail(currentUserEmail),
                email = currentUserEmail,
                onLogout = {
                    currentUserEmail = ""
                    navController.navigate("login") {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(AppDestination.Seller.route) {
            SellerScreen(onNavigate = { destination ->
                navigateToMainScreen(navController, destination) },
                sellerViewModel = sellerViewModel,
                currentUserEmail = currentUserEmail
            )
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
