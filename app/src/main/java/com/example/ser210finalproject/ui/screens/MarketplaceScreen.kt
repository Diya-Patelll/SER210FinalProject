package com.example.ser210finalproject.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ser210finalproject.ui.theme.QuinnipiacBlue
import com.example.ser210finalproject.ui.theme.QuinnipiacBlueLight
import com.example.ser210finalproject.viewmodel.MarketplaceSortOption
import com.example.ser210finalproject.viewmodel.MarketplaceViewModel

@Composable
fun MarketplaceScreen(
    onNavigate: (AppDestination) -> Unit,
    onListingClick: (Int) -> Unit,
    viewModel: MarketplaceViewModel,
    currentUserEmail: String
) {
    val listings by viewModel.filteredListings.collectAsState(initial = emptyList())
    val selectedSortOption by viewModel.selectedSortOption.collectAsState()
    var filterMenuExpanded by remember { mutableStateOf(false) }
    val filterInteractionSource = remember { MutableInteractionSource() }
    val filterHovered by filterInteractionSource.collectIsHoveredAsState()

    AppScaffold(
        currentDestination = AppDestination.Marketplace,
        onNavigate = onNavigate,
        title = "Marketplace"
    ) { modifier ->
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(20.dp),
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
                    val filterColor by animateColorAsState(
                        targetValue = if (filterHovered) QuinnipiacBlueLight else QuinnipiacBlue,
                        label = "filterButtonHover"
                    )

                    Column {
                        Button(
                            onClick = { filterMenuExpanded = true },
                            interactionSource = filterInteractionSource,
                            modifier = Modifier.hoverable(filterInteractionSource),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = filterColor
                            )
                        ) {
                            Text(
                                if (selectedSortOption == MarketplaceSortOption.DEFAULT) {
                                    "Filter"
                                } else {
                                    selectedSortOption.label
                                }
                            )
                        }

                        DropdownMenu(
                            expanded = filterMenuExpanded,
                            onDismissRequest = { filterMenuExpanded = false }
                        ) {
                            MarketplaceSortOption.entries.forEach { sortOption ->
                                DropdownMenuItem(
                                    text = { Text(sortOption.label) },
                                    onClick = {
                                        viewModel.setSortOption(sortOption)
                                        filterMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            items(listings) { listing ->
                val isOwnListing = viewModel.isOwnListing(listing.sellerID,currentUserEmail)
                val buttonInteractionSource = remember(listing.listedId) { MutableInteractionSource() }
                val buttonHovered by buttonInteractionSource.collectIsHoveredAsState()
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = viewModel.formatSellerName(listing.sellerID),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = viewModel.formatMoney(listing.askingPrice),
                                color = QuinnipiacBlue,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${viewModel.formatNumber(listing.pointsAmount)} MealPoints",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            Button(
                                onClick = {onListingClick(listing.listedId)},
                                enabled = !isOwnListing,
                                shape = RoundedCornerShape(8.dp),
                                interactionSource = buttonInteractionSource,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (buttonHovered && !isOwnListing) QuinnipiacBlueLight else QuinnipiacBlue
                                ),
                                modifier = Modifier.hoverable(buttonInteractionSource)
                            ) {
                                Text(text = if(isOwnListing) "My Listing" else "Buy")
                            }
                        }
                        Text(
                            text = viewModel.formatPointsPerDollar(listing.pointsAmount, listing.askingPrice),
                            color = QuinnipiacBlue,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }
            }
        }
    }
}
