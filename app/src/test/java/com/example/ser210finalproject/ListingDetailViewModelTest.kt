package com.example.ser210finalproject

import com.example.ser210finalproject.data.Listing
import com.example.ser210finalproject.viewmodel.ListingDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListingDetailViewModelTest {

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun confirmPurchase_marksListingAsSold() = runTest {
        // tests that confirms a purchase marks the selected listing as sold
        val repository = FakeItemsRepository()
        repository.listings.value = listOf(
            Listing(listedId = 4, sellerID = "diya.patel", pointsAmount = 100.0, askingPrice = 30.0)
        )
        val viewModel = ListingDetailViewModel(repository, 4, "bob.cat@quinnipiac.edu")
        var successCalled = false

        viewModel.confirmPurchase { successCalled = true }
        advanceUntilIdle()

        assertTrue(successCalled)
        val updatedListing = repository.getListingStream(4).first()
        assertNotNull(updatedListing)
        assertTrue(updatedListing!!.isSold)
    }
}
