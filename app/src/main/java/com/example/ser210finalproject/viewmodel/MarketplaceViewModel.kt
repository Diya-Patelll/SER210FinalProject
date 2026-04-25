package com.example.ser210finalproject.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ser210finalproject.data.ItemsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine

class MarketplaceViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {

    private val _showLowerPriceOnly = MutableStateFlow(false)
    val showLowerPriceOnly: StateFlow<Boolean> = _showLowerPriceOnly

    // combines the database with filter
    val filteredListings = combine(
        itemsRepository.getAllActiveListingsStream(),
        _showLowerPriceOnly
    ) { listings, filter ->
        if (filter) listings.filter { it.askingPrice <= 100.0 } else listings
    }

    fun toggleFilter() {
        _showLowerPriceOnly.value = !_showLowerPriceOnly.value
    }

    fun isOwnListing(sellerId: String, currentUserEmail: String): Boolean {
        // converts email to name to match sellerID
        return sellerId == currentUserEmail.substringBefore("@")
    }

    // formatting logic
    fun formatSellerName(sellerId: String): String {
        val firstName = sellerId.substringBefore(".")
            .lowercase()
            .replaceFirstChar { it.uppercase() }
        val lastName = sellerId.substringAfter(".", "")

        return if (lastName.isNotEmpty()) {
            "$firstName ${lastName.first().uppercaseChar()}."
        } else {
            firstName
        }
    }

    fun formatMoney(value: Double): String {
        return if (value % 1.0 == 0.0) {
            "$${value.toInt()}"
        } else {
            "$${"%.2f".format(value)}"
        }
    }

    fun formatNumber(value: Double): String {
        return if (value % 1.0 == 0.0) {
            value.toInt().toString()
        } else {
            "%.2f".format(value)
        }
    }
}