package com.example.ser210finalproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ser210finalproject.data.ItemsRepository
import com.example.ser210finalproject.data.Listing
import com.example.ser210finalproject.data.Purchase
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileViewModel(
    private val itemsRepository: ItemsRepository,
    private val currentUserEmail: String
) : ViewModel() {
    private val currentUserId = currentUserEmail.substringBefore("@")

    // seller matches the current user
    val myListings = itemsRepository.getAllActiveListingsStream().map { listings ->
        listings.filter { it.sellerID == currentUserId }
    }

    // purchases made by the current user
    val myPurchases = combine(
        itemsRepository.getPurchasesForBuyerStream(currentUserId),
        itemsRepository.getListingStreamMap()
    ) { purchases, listingsById ->
        purchases.map { purchase ->
            PurchaseDisplay(
                purchase = purchase,
                pointsAmount = listingsById[purchase.listingId]?.pointsAmount
            )
        }
    }

    // delete function
    fun deleteListing(listing: Listing) {
        viewModelScope.launch {
            itemsRepository.deleteListing(listing)
        }
    }

    fun formatUserName(userId: String): String {
        val firstName = userId.substringBefore(".")
            .lowercase()
            .replaceFirstChar { it.uppercase() }
        val lastName = userId.substringAfter(".", "")

        return if (lastName.isNotEmpty()) {
            "$firstName ${lastName.first().uppercaseChar()}."
        } else {
            firstName
        }
    }

    fun formatNumber(value: Double?): String {
        if (value == null) return "Unavailable"
        return if (value % 1.0 == 0.0) {
            value.toInt().toString()
        } else {
            "%.2f".format(value)
        }
    }

    fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val netDate = Date(timestamp)
        return sdf.format(netDate)
    }
}

data class PurchaseDisplay(
    val purchase: Purchase,
    val pointsAmount: Double?
)
