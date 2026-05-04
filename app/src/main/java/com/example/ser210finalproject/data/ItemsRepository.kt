package com.example.ser210finalproject.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// repository that defines data operation for marketplace
// abstracts data source from rest of the app
interface ItemsRepository {
    // student operations
    fun getStudentStream(email: String): Flow<Student?>
    suspend fun insertStudent(student: Student)

    // listing operations (marketplace logic)
    fun getAllActiveListingsStream(): Flow<List<Listing>>
    fun getListingStream(id: Int): Flow<Listing?>
    fun getListingStreamMap(): Flow<Map<Int, Listing>> = getAllListingsStream().map { listings -> listings.associateBy { it.listedId } }
    suspend fun insertListing(listing: Listing)
    suspend fun markListingAsSold(id: Int)
    suspend fun deleteListing(listing: Listing)

    // purchase operations
    fun getPurchasesForBuyerStream(buyerId: String): Flow<List<Purchase>>
    suspend fun insertPurchase(purchase: Purchase)
    fun getAllListingsStream(): Flow<List<Listing>>
}
