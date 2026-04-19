package com.example.ser210finalproject.data

import kotlinx.coroutines.flow.Flow

// implementation of itemsrepository that communicates with Room DAOs to provide local data
class OfflineItemsRepository(
    private val studentDao: StudentDAO,
    private val listingDao: ListingDAO,
    private val purchaseDao: PurchaseDAO
) : ItemsRepository {

    override fun getStudentStream(id: String): Flow<Student?> =
        studentDao.getStudent(id)

    override suspend fun insertStudent(student: Student) =
        studentDao.insertStudent(student)

    override fun getAllActiveListingsStream(): Flow<List<Listing>> =
        listingDao.getAllActiveListings()

    override fun getListingStream(id: Int): Flow<Listing?> =
        listingDao.getListing(id)

    override fun getSellerListingsStream(sellerId: String): Flow<List<Listing>> =
        listingDao.getListingsForSeller(sellerId)

    override suspend fun insertListing(listing: Listing) =
        listingDao.insertListing(listing)

    override suspend fun updateListing(listing: Listing) =
        listingDao.updateListing(listing)

    override suspend fun markListingAsSold(id: Int) =
        listingDao.markListingAsSold(id)

    override suspend fun deleteListing(listing: Listing) =
        listingDao.deleteListing(listing)

    override fun getPurchaseHistoryStream(): Flow<List<Purchase>> =
        purchaseDao.getPurchaseHistory()

    override fun getPurchasesForBuyerStream(buyerId: String): Flow<List<Purchase>> =
        purchaseDao.getPurchasesForBuyer(buyerId)

    override suspend fun insertPurchase(purchase: Purchase) =
        purchaseDao.insertPurchase(purchase)
}
