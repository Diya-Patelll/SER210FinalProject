package com.example.ser210finalproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ser210finalproject.data.ItemsRepository
import com.example.ser210finalproject.data.Listing
import kotlinx.coroutines.launch

class SellerViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {

    // save the listing
    fun postListing(
        sellerEmail: String,
        points: String,
        price: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val pointsValue = points.toDoubleOrNull()
        val priceValue = price.toDoubleOrNull()

        // validation logic
        if (pointsValue == null || pointsValue <= 0 || priceValue == null || priceValue <= 0) {
            onError("Enter valid positive numbers for points and price.")
            return
        }

        viewModelScope.launch {
            val newListing = Listing(
                sellerID = sellerEmail.substringBefore("@"),
                pointsAmount = pointsValue,
                askingPrice = priceValue,
                isSold = false
            )
            itemsRepository.insertListing(newListing)
            onSuccess()
        }
    }
}