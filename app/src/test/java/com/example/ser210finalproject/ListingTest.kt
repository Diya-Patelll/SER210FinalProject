package com.example.ser210finalproject

import com.example.ser210finalproject.data.Listing
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class ListingTest {

    @Test
    fun listing_defaultsToUnsold() {
        // verifies that a new listing keeps its seller, points, price, and default unsold state
        val listing = Listing(
            sellerID = "diya.patel",
            pointsAmount = 125.0,
            askingPrice = 70.0
        )

        assertEquals("diya.patel", listing.sellerID)
        assertEquals(125.0, listing.pointsAmount, 0.0)
        assertEquals(70.0, listing.askingPrice, 0.0)
        assertFalse(listing.isSold)
    }
}
