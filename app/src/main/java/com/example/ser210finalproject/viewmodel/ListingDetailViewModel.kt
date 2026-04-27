package com.example.ser210finalproject.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ser210finalproject.data.ItemsRepository


class ListingDetailViewModel(
    private val itemsRepository: ItemsRepository,
    private val listingId: Int
) : ViewModel() {

    // fetch the specific listing
    val listing = itemsRepository.getListingStream(listingId)

}