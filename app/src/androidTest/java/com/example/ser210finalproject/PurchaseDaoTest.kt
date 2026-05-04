package com.example.ser210finalproject

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ser210finalproject.data.AppDatabase
import com.example.ser210finalproject.data.Purchase
import com.example.ser210finalproject.data.PurchaseDAO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PurchaseDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var purchaseDao: PurchaseDAO

    @Before
    fun createDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        purchaseDao = database.purchaseDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertPurchase_andGetByBuyer_returnsPurchase() = runBlocking {
        val purchase = Purchase(
            listingId = 1,
            buyerId = "diya.patel",
            sellerId = "bob.cat",
            pricePaid = 50.0
        )

        purchaseDao.insertPurchase(purchase)
        val result = purchaseDao.getPurchasesForBuyer("diya.patel").first()

        assertEquals(1, result.size)
        assertEquals("bob.cat", result.first().sellerId)
    }

}
