package com.example.ser210finalproject.ui.screens

import java.util.Locale

data class MarketListing(
    val seller: String,
    val points: Int,
    val price: Int
) {
    val ratioText: String
        get() = if (price > 0) {
            String.format(Locale.US, "%.2f points per dollar", points.toDouble() / price.toDouble())
        } else {
            "0.00 points per dollar"
        }
}
