package com.example.ser210finalproject.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ser210finalproject.ui.theme.QuinnipiacBlue

@Composable
fun ProfileScreen(onNavigate: (AppDestination) -> Unit,
    displayName: String,
    email: String,
    currentListing: MarketListing?
) {
    AppScaffold(
        currentDestination = AppDestination.Profile,
        onNavigate = onNavigate,
        title = "Profile"
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
                        text = displayName,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = email,
                        color = QuinnipiacBlue,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = "Status: Verified Quinnipiac student",
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("MealPoints left", fontWeight = FontWeight.Bold)
                    Text(
                        text = "762",
                        style = MaterialTheme.typography.headlineMedium,
                        color = QuinnipiacBlue,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = QuinnipiacBlue)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Current listing",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = currentListing?.let { "${it.points} MealPoints for $${it.price}" }
                            ?: "No active listing",
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }
        }
    }
}
