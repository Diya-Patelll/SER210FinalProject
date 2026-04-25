package com.example.ser210finalproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ser210finalproject.data.ItemsRepository
import com.example.ser210finalproject.data.Listing
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val itemsRepository: ItemsRepository,
    private val currentUserEmail: String
) : ViewModel() {

    // seller matches the current user
    val myListings = itemsRepository.getAllActiveListingsStream().map { listings ->
        listings.filter { it.sellerID == currentUserEmail.substringBefore("@") }
    }

    // delete function
    fun deleteListing(listing: Listing) {
        viewModelScope.launch {
            itemsRepository.deleteListing(listing)
        }
    }
}