package com.example.ser210finalproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchases")
data class Purchase(
    @PrimaryKey(autoGenerate = true)
    val purchaseId: Int = 0,
    val listingId: Int,    // item bought
    val buyerId: String,   // who bought it, links to Student u_id
    val sellerId: String,  // who sold it
    val pricePaid: Double, // records price
    val timestamp: Long = System.currentTimeMillis()
)