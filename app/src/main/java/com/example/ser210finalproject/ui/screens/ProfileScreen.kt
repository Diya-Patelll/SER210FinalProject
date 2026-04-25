package com.example.ser210finalproject.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ser210finalproject.ui.theme.QuinnipiacBlue
import com.example.ser210finalproject.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(onNavigate: (AppDestination) -> Unit,
    profileViewModel: ProfileViewModel,
    displayName: String,
    email: String
) {
    val myListing by profileViewModel.myListings.collectAsState(initial = emptyList())
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
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
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
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("MealPoints left", fontWeight = FontWeight.Bold)
                    Text(
                        text = "425",
                        style = MaterialTheme.typography.headlineMedium,
                        color = QuinnipiacBlue,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }
            // listing header
            Text(
                text = "My Listings",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // my listings
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(myListing) { listing ->
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface
                        ),

                        // drop shadow
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        ),
                        modifier = Modifier.fillMaxWidth()

                    ) {
                        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Column(modifier = Modifier
                                .padding(24.dp)
                                .weight(1f)
                            ){
                                Text(
                                    text = "Points: ${listing.pointsAmount}",
                                    color = QuinnipiacBlue,
                                    style = MaterialTheme.typography.headlineMedium,
                                    modifier = Modifier.padding(top = 6.dp)
                                )
                                Text(
                                    text = "Price: $${listing.askingPrice}",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = QuinnipiacBlue,
                                )
                            }
                            Button(onClick = {profileViewModel.deleteListing(listing)},
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.errorContainer,
                                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}
