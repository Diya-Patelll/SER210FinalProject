package com.example.ser210finalproject.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PurchaseDAO {
    @Insert
    suspend fun insertPurchase(purchase: Purchase)
    @Query("SELECT * FROM purchases WHERE buyerId = :buyerId")
    fun getPurchasesForBuyer(buyerId: String): Flow<List<Purchase>>
}
