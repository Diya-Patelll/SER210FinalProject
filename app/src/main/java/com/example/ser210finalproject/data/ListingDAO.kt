package com.example.ser210finalproject.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// DAO for listing, defines SQL queries for the Marketplace
@Dao
interface ListingDAO {
    // listings that haven't been sold
    @Query("SELECT * FROM listings WHERE isSold = 0")
    fun getAllActiveListings(): Flow<List<Listing>>

    @Query("SELECT * FROM listings WHERE listedID = :id")
    fun getListing(id: Int): Flow<Listing?>

    // users view all listings that they've posted
    @Query("SELECT * FROM listings WHERE sellerID = :sellerId")
    fun getListingsForSeller(sellerId: String): Flow<List<Listing>>

    @Insert
    suspend fun insertListing(listing: Listing)

    @Update
    suspend fun updateListing(listing: Listing)

    @Query("UPDATE listings SET isSold = 1 WHERE listedID = :id")
    suspend fun markListingAsSold(id: Int)

    @Delete
    suspend fun deleteListing(listing: Listing)
}
