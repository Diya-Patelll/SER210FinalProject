package com.example.ser210finalproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ser210finalproject.data.ItemsRepository
import com.example.ser210finalproject.data.Listing
import kotlinx.coroutines.flow.firstOrNull
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
            val normalizedEmail = sellerEmail.trim().lowercase()
            val student = itemsRepository.getStudentStream(normalizedEmail).firstOrNull()

            if (student == null) {
                onError("Unable to verify student balance.")
                return@launch
            }

            val activeSellerListings = itemsRepository
                .getAllActiveListingsStream()
                .firstOrNull()
                .orEmpty()
                .filter { it.sellerID == normalizedEmail.substringBefore("@") }

            val alreadyListedPoints = activeSellerListings.sumOf { it.pointsAmount }
            val availableToList = student.mealpointBalance - alreadyListedPoints

            if (pointsValue > availableToList) {
                onError("Insufficient MealPoints.")
                return@launch
            }

            val newListing = Listing(
                sellerID = normalizedEmail.substringBefore("@"),
                pointsAmount = pointsValue,
                askingPrice = priceValue,
                isSold = false
            )
            itemsRepository.insertListing(newListing)
            onSuccess()
        }
    }
}
