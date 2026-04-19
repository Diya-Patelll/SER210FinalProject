package com.example.ser210finalproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listing")
data class Listing(
    @PrimaryKey(autoGenerate = true)
    val listedID: Int = 0,
    val sellerID: String, // links to students u_id
    val pointsAmount: Double, // meal points user sells
    val askingPrice: Double,  // money they want
    val isSold: Boolean = false // if it's still available

)
