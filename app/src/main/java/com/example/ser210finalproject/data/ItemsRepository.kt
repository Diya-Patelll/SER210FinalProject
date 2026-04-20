package com.example.ser210finalproject.data

import kotlinx.coroutines.flow.Flow

// repository that defines data operation for marketplace
// abstracts data source from rest of the app
interface ItemsRepository {
    // student operations
    fun getStudentStream(email: String): Flow<Student?>
    suspend fun insertStudent(student: Student)

    // listing operations (marketplace logic)
    fun getAllActiveListingsStream(): Flow<List<Listing>>
    fun getListingStream(id: Int): Flow<Listing?>
    fun getSellerListingsStream(sellerId: String): Flow<List<Listing>>
    suspend fun insertListing(listing: Listing)
    suspend fun updateListing(listing: Listing)
    suspend fun markListingAsSold(id: Int)
    suspend fun deleteListing(listing: Listing)

    // purchase operations
    fun getPurchaseHistoryStream(): Flow<List<Purchase>>
    fun getPurchasesForBuyerStream(buyerId: String): Flow<List<Purchase>>
    suspend fun insertPurchase(purchase: Purchase)
}
