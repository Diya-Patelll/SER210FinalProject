package com.example.ser210finalproject.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.ser210finalproject.ui.screens.ListingDetailScreen
import com.example.ser210finalproject.ui.screens.LoginScreen
import com.example.ser210finalproject.ui.screens.MarketListing
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
    val marketListings = remember {
        mutableStateListOf(
            MarketListing("Sophia R.", 120, 78),
            MarketListing("Marcus T.", 250, 160),
            MarketListing("Ava C.", 80, 52),
            MarketListing("Jhon F.", 500, 100),
            MarketListing("Kyle L.", 750, 300)
        )
    }
    var selectedListing by remember { mutableStateOf<MarketListing?>(null) }

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
            MarketplaceScreen(
                listings = marketListings,
                onNavigate = { destination ->
                    navigateToMainScreen(navController, destination)
                },
                onOpenListing = { listing ->
                    selectedListing = listing
                    navController.navigate(
                        "listingDetail/${Uri.encode(listing.seller)}/${listing.points}/${listing.price}"
                    )
                }
            )
        }

        composable("listingDetail/{seller}/{points}/{price}") { backStackEntry ->
            val seller = Uri.decode(backStackEntry.arguments?.getString("seller").orEmpty())
            val points = backStackEntry.arguments?.getString("points")?.toIntOrNull() ?: 0
            val price = backStackEntry.arguments?.getString("price")?.toIntOrNull() ?: 0
            val listing = selectedListing ?: MarketListing(seller, points, price)

            ListingDetailScreen(
                listing = listing,
                onNavigate = { destination ->
                    navigateToMainScreen(navController, destination)
                },
                onBackToMarketplace = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppDestination.Profile.route) {
            ProfileScreen(
                onNavigate = { destination ->
                    navigateToMainScreen(navController, destination)
                },
                displayName = loginViewModel.getDisplayNameFromEmail(currentUserEmail),
                email = currentUserEmail,
                currentListing = marketListings.firstOrNull { it.seller == loginViewModel.getDisplayNameFromEmail(currentUserEmail) }
            )
        }

        composable(AppDestination.Seller.route) {
            SellerScreen(
                onNavigate = { destination ->
                    navigateToMainScreen(navController, destination)
                },
                onPostListing = { points, price ->
                    val sellerName = loginViewModel.getDisplayNameFromEmail(currentUserEmail)
                    val newListing = MarketListing(sellerName, points, price)
                    marketListings.removeAll { it.seller == sellerName }
                    marketListings.add(0, newListing)
                    selectedListing = newListing
                    navController.navigate(AppDestination.Marketplace.route) {
                        launchSingleTop = true
                    }
                }
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
