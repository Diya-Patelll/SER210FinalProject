package com.example.ser210finalproject

import com.example.ser210finalproject.data.Listing
import com.example.ser210finalproject.viewmodel.MarketplaceSortOption
import com.example.ser210finalproject.viewmodel.MarketplaceViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class MarketplaceViewModelTest {

    @Test
    fun filteredListings_sortsByPriceLowToHigh() = runBlocking {
        // tests the marketplace sort option orders listings from lowest to highest price
        val repository = FakeItemsRepository()
        repository.listings.value = listOf(
            Listing(listedId = 1, sellerID = "a.one", pointsAmount = 100.0, askingPrice = 90.0),
            Listing(listedId = 2, sellerID = "b.two", pointsAmount = 150.0, askingPrice = 40.0)
        )
        val viewModel = MarketplaceViewModel(repository)

        viewModel.setSortOption(MarketplaceSortOption.PRICE_LOW_TO_HIGH)
        val listings = viewModel.filteredListings.first()

        assertEquals(2, listings.first().listedId)
        assertEquals("A O.", viewModel.formatSellerName("a.one"))
    }
}
