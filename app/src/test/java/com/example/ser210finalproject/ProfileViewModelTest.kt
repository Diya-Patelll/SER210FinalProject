package com.example.ser210finalproject

import com.example.ser210finalproject.data.Listing
import com.example.ser210finalproject.viewmodel.ProfileViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ProfileViewModelTest {

    @Test
    fun myListings_filtersCurrentUsersListings() = runBlocking {
        // tests the profile only shows listings created by the current logged-in user
        val repository = FakeItemsRepository()
        repository.listings.value = listOf(
            Listing(listedId = 1, sellerID = "diya.patel", pointsAmount = 100.0, askingPrice = 30.0),
            Listing(listedId = 2, sellerID = "bob.cat", pointsAmount = 50.0, askingPrice = 20.0)
        )
        val viewModel = ProfileViewModel(repository, "diya.patel@quinnipiac.edu")

        val myListings = viewModel.myListings.first()

        assertEquals(1, myListings.size)
        assertEquals("diya.patel", myListings.first().sellerID)
    }
}
