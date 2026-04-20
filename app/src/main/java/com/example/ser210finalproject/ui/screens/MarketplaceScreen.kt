package com.example.ser210finalproject.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ser210finalproject.ui.theme.QuinnipiacBlue
import com.example.ser210finalproject.ui.theme.QuinnipiacGoldSoft

@Composable
fun MarketplaceScreen(onNavigate: (AppDestination) -> Unit) {
    var showLowerPriceOnly by remember { mutableStateOf(false) }
    val listings = listOf(
        MarketListing("Sophia R.", 120, 78),
        MarketListing("Marcus T.", 250, 160),
        MarketListing("Ava C.", 80, 52)
    )
    val filteredListings = if (showLowerPriceOnly) {
        listings.filter { it.price <= 100 }
    } else {
        listings
    }

    AppScaffold(
        currentDestination = AppDestination.Marketplace,
        onNavigate = onNavigate,
        title = "Marketplace"
    ) { modifier ->
        LazyColumn(
            modifier = modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Listings",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    FilterChip(
                        selected = showLowerPriceOnly,
                        onClick = { showLowerPriceOnly = !showLowerPriceOnly },
                        label = {
                            Text(if (showLowerPriceOnly) "Under $100" else "Filter")
                        }
                    )
                }
            }

            items(filteredListings) { listing ->
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = listing.seller,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "$${listing.price}",
                                color = QuinnipiacBlue,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "${listing.points} MealPoints",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

private data class MarketListing(
    val seller: String,
    val points: Int,
    val price: Int
)
