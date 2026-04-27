package com.example.ser210finalproject.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ser210finalproject.data.ItemsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine

enum class MarketplaceSortOption(val label: String) {
    DEFAULT("Default"),
    PRICE_HIGH_TO_LOW("Price: High to Low"),
    PRICE_LOW_TO_HIGH("Price: Low to High"),
    POINTS_HIGH_TO_LOW("Points: High to Low")
}

class MarketplaceViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {

    private val _selectedSortOption = MutableStateFlow(MarketplaceSortOption.DEFAULT)
    val selectedSortOption: StateFlow<MarketplaceSortOption> = _selectedSortOption

    val filteredListings = combine(
        itemsRepository.getAllActiveListingsStream(),
        _selectedSortOption
    ) { listings, selectedSortOption ->
        when (selectedSortOption) {
            MarketplaceSortOption.DEFAULT -> listings
            MarketplaceSortOption.PRICE_HIGH_TO_LOW -> listings.sortedByDescending { it.askingPrice }
            MarketplaceSortOption.PRICE_LOW_TO_HIGH -> listings.sortedBy { it.askingPrice }
            MarketplaceSortOption.POINTS_HIGH_TO_LOW -> listings.sortedByDescending { it.pointsAmount }
        }
    }

    fun setSortOption(sortOption: MarketplaceSortOption) {
        _selectedSortOption.value = sortOption
    }

    fun isOwnListing(sellerId: String, currentUserEmail: String): Boolean {
        return sellerId == currentUserEmail.substringBefore("@")
    }

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

    fun formatPointsPerDollar(points: Double, price: Double): String {
        if (price <= 0.0) return "0 points per dollar"
        val ratio = points / price
        return "${formatNumber(ratio)} points per dollar"
    }
}
