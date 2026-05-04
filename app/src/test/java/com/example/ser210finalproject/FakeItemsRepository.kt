package com.example.ser210finalproject

import com.example.ser210finalproject.data.ItemsRepository
import com.example.ser210finalproject.data.Listing
import com.example.ser210finalproject.data.Purchase
import com.example.ser210finalproject.data.Student
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

// fake repository used only for JUnit tests
// lets the viewmodel tests run without needing the real room database.
class FakeItemsRepository : ItemsRepository {
    val studentsByEmail = MutableStateFlow(mutableMapOf<String, Student>())
    val listings = MutableStateFlow<List<Listing>>(emptyList())
    val purchases = MutableStateFlow<List<Purchase>>(emptyList())

    override fun getStudentStream(email: String): Flow<Student?> {
        return studentsByEmail.map { it[email] }
    }

    override suspend fun insertStudent(student: Student) {
        studentsByEmail.value = studentsByEmail.value.toMutableMap().apply {
            put(student.email, student)
        }
    }

    override fun getAllActiveListingsStream(): Flow<List<Listing>> {
        return listings.map { currentListings -> currentListings.filter { !it.isSold } }
    }

    override fun getAllListingsStream(): Flow<List<Listing>> {
        return listings
    }

    override fun getListingStream(id: Int): Flow<Listing?> {
        return listings.map { currentListings -> currentListings.firstOrNull { it.listedId == id } }
    }

    override suspend fun insertListing(listing: Listing) {
        val nextId = (listings.value.maxOfOrNull { it.listedId } ?: 0) + 1
        listings.value += listing.copy(listedId = nextId)
    }

    override suspend fun markListingAsSold(id: Int) {
        listings.value = listings.value.map { listing ->
            if (listing.listedId == id) listing.copy(isSold = true) else listing
        }
    }

    override suspend fun deleteListing(listing: Listing) {
        listings.value = listings.value.filterNot { it.listedId == listing.listedId }
    }

    override fun getPurchasesForBuyerStream(buyerId: String): Flow<List<Purchase>> {
        return purchases.map { currentPurchases -> currentPurchases.filter { it.buyerId == buyerId } }
    }

    override suspend fun insertPurchase(purchase: Purchase) {
        purchases.value += purchase
    }
}
