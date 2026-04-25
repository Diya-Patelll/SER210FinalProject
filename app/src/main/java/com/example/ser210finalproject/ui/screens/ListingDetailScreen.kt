package com.example.ser210finalproject.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ser210finalproject.viewmodel.ListingDetailViewModel

@Composable
fun ListingDetailScreen(
    viewModel: ListingDetailViewModel,
    onNavigateBack: () -> Unit
) {
    val listing by viewModel.listing.collectAsState(initial = null)

    Column(modifier = Modifier.padding(16.dp)){

        Button(onClick = onNavigateBack,
            modifier = Modifier.padding(top = 20.dp)) {
            Text("Back")
        }

        Spacer(modifier = Modifier.height(24.dp))

        listing?.let { item ->
            Text(
                text = "Listing Details",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // points info
            Text(text = "Mealpoints: ${item.pointsAmount}",
                style = MaterialTheme.typography.headlineMedium)

            // price info
            Text(text = "Price: $${item.askingPrice}")

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = { }) {
                Text("Confirm Purchase")
            }
        }
    }
}