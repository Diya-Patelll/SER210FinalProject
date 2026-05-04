package com.example.ser210finalproject.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.example.ser210finalproject.data.ItemsRepository
import com.example.ser210finalproject.data.Purchase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


class ListingDetailViewModel(
    private val itemsRepository: ItemsRepository,
    private val listingId: Int,
    private val currentUserEmail: String
) : ViewModel() {

    // fetch the specific listing
    val listing = itemsRepository.getListingStream(listingId)

    fun confirmPurchase(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val currentListing = listing.firstOrNull() ?: return@launch
            val buyerId = currentUserEmail.substringBefore("@")

            itemsRepository.insertPurchase(
                Purchase(
                    listingId = currentListing.listedId,
                    buyerId = buyerId,
                    sellerId = currentListing.sellerID,
                    pricePaid = currentListing.askingPrice
                )
            )
            itemsRepository.markListingAsSold(listingId)
            onSuccess()
        }
    }
}
