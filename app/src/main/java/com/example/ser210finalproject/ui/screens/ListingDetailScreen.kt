package com.example.ser210finalproject.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ser210finalproject.ui.theme.QuinnipiacBlue

@Composable
fun ListingDetailScreen(
    listing: MarketListing,
    onNavigate: (AppDestination) -> Unit,
    onBackToMarketplace: () -> Unit
) {
    var purchaseConfirmed by remember { mutableStateOf(false) }

    AppScaffold(
        currentDestination = AppDestination.Marketplace,
        onNavigate = onNavigate,
        title = "Listing Details"
    ) { modifier ->
        Column(
            modifier = modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = listing.seller,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$${listing.price}",
                        style = MaterialTheme.typography.titleLarge,
                        color = QuinnipiacBlue,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                    Text(
                        text = "${listing.points} MealPoints",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = listing.ratioText,
                        color = QuinnipiacBlue,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            if (purchaseConfirmed) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = QuinnipiacBlue)
                ) {
                    Text(
                        text = "Purchase confirmed. Your request has been submitted.",
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(18.dp)
                    )
                }
            }

            Button(
                onClick = { purchaseConfirmed = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirm purchase")
            }

            OutlinedButton(
                onClick = onBackToMarketplace,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to marketplace")
            }
        }
    }
}
