package com.example.ser210finalproject

import com.example.ser210finalproject.data.Purchase
import org.junit.Assert.assertEquals
import org.junit.Test

class PurchaseTest {

    @Test
    fun purchase_storesExpectedValues() {
        // verifies that a purchase entity keeps the buyer, seller, listing id, and price paid
        val purchase = Purchase(
            listingId = 3,
            buyerId = "bob.cat",
            sellerId = "diya.patel",
            pricePaid = 55.0
        )

        assertEquals(3, purchase.listingId)
        assertEquals("bob.cat", purchase.buyerId)
        assertEquals("diya.patel", purchase.sellerId)
        assertEquals(55.0, purchase.pricePaid, 0.0)
    }
}
