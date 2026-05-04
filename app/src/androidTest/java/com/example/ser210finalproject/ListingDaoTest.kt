package com.example.ser210finalproject

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ser210finalproject.data.AppDatabase
import com.example.ser210finalproject.data.Listing
import com.example.ser210finalproject.data.ListingDAO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListingDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var listingDao: ListingDAO

    @Before
    fun createDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        listingDao = database.listingDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertListing_thenReadActiveListings_returnsListing() = runBlocking {
        // tests  a newly inserted listing appears in the active listings query
        val listing = Listing(
            sellerID = "diya.patel",
            pointsAmount = 150.0,
            askingPrice = 80.0
        )

        listingDao.insertListing(listing)
        val activeListings = listingDao.getAllActiveListings().first()

        assertEquals(1, activeListings.size)
        assertEquals("diya.patel", activeListings.first().sellerID)
        assertFalse(activeListings.first().isSold)
    }

    @Test
    fun getListingsForSeller_returnsOnlyMatchingListings() = runBlocking {
        // tests seller-specific queries only return listings for that seller
        listingDao.insertListing(
            Listing(
                sellerID = "diya.patel",
                pointsAmount = 100.0,
                askingPrice = 50.0
            )
        )
        listingDao.insertListing(
            Listing(
                sellerID = "alex.lee",
                pointsAmount = 200.0,
                askingPrice = 120.0
            )
        )

        val sellerListings = listingDao.getListingsForSeller("diya.patel").first()

        assertEquals(1, sellerListings.size)
        assertEquals("diya.patel", sellerListings.first().sellerID)
    }

    @Test
    fun markListingAsSold_removesListingFromActiveListings() = runBlocking {
        // tests that marking a listing sold removes it from the active listings query
        listingDao.insertListing(
            Listing(
                sellerID = "diya.patel",
                pointsAmount = 90.0,
                askingPrice = 45.0
            )
        )
        val insertedListing = listingDao.getAllActiveListings().first().first()

        listingDao.markListingAsSold(insertedListing.listedId)

        val activeListings = listingDao.getAllActiveListings().first()
        assertEquals(0, activeListings.size)
    }
}
