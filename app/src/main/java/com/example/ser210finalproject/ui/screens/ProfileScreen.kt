package com.example.ser210finalproject.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ser210finalproject.ui.theme.QuinnipiacBlue
import com.example.ser210finalproject.ui.theme.QuinnipiacBlueLight
import com.example.ser210finalproject.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    onNavigate: (AppDestination) -> Unit,
    profileViewModel: ProfileViewModel,
    displayName: String,
    email: String,
    onLogout: () -> Unit
) {
    val myListings by profileViewModel.myListings.collectAsState(initial = emptyList())
    val myPurchases by profileViewModel.myPurchases.collectAsState(initial = emptyList())
    val logoutInteractionSource = remember { MutableInteractionSource() }
    val logoutHovered by logoutInteractionSource.collectIsHoveredAsState()
    val logoutColor by animateColorAsState(
        targetValue = if (logoutHovered) QuinnipiacBlueLight else QuinnipiacBlue,
        label = "logoutHover"
    )
    AppScaffold(
        currentDestination = AppDestination.Profile,
        onNavigate = onNavigate,
        title = "Profile"
    ) { modifier ->
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
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
                        Button(
                            onClick = onLogout,
                            interactionSource = logoutInteractionSource,
                            colors = ButtonDefaults.buttonColors(containerColor = logoutColor),
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .hoverable(logoutInteractionSource)
                        ) {
                            Text("Log Out")
                        }
                    }
                }
            }
            item{
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
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
            }

            item {
                Text(
                    text = "My Listings",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
                items(myListings) { listing ->
                    val deleteInteractionSource = remember(listing.listedId) { MutableInteractionSource() }
                    val deleteHovered by deleteInteractionSource.collectIsHoveredAsState()
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "${listing.pointsAmount} MealPoints",
                                    color = QuinnipiacBlue,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "$${listing.askingPrice}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = QuinnipiacBlue,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                            Button(
                                onClick = { profileViewModel.deleteListing(listing) },
                                interactionSource = deleteInteractionSource,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (deleteHovered) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.errorContainer,
                                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.hoverable(deleteInteractionSource)
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            item {
                Text(
                    text = "My Purchases",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            items(myPurchases) { purchase ->
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Bought from ${profileViewModel.formatUserName(purchase.purchase.sellerId)}",
                            color = QuinnipiacBlue,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Mealpoints: ${profileViewModel.formatNumber(purchase.pointsAmount)}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = QuinnipiacBlue,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            text = "Paid: $${purchase.purchase.pricePaid}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = QuinnipiacBlue,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            text = "Date: ${profileViewModel.formatDate(purchase.purchase.timestamp)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
