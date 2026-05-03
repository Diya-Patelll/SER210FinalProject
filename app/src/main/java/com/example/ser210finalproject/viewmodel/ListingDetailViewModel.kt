package com.example.ser210finalproject.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.example.ser210finalproject.data.ItemsRepository
import kotlinx.coroutines.launch


class ListingDetailViewModel(
    private val itemsRepository: ItemsRepository,
    private val listingId: Int
) : ViewModel() {

    // fetch the specific listing
    val listing = itemsRepository.getListingStream(listingId)

    fun confirmPurchase(onSuccess: () -> Unit) {
        viewModelScope.launch {
            itemsRepository.markListingAsSold(listingId)
            onSuccess()
        }
    }
}
