package com.example.ser210finalproject.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ser210finalproject.ui.theme.QuinnipiacBlue
import com.example.ser210finalproject.ui.theme.QuinnipiacBlueLight
import com.example.ser210finalproject.viewmodel.ListingDetailViewModel
import java.util.Locale

@Composable
fun ListingDetailScreen(
    viewModel: ListingDetailViewModel,
    onNavigateBack: () -> Unit
) {
    val listing by viewModel.listing.collectAsState(initial = null)
    val backInteractionSource = remember { MutableInteractionSource() }
    val backHovered by backInteractionSource.collectIsHoveredAsState()
    val confirmInteractionSource = remember { MutableInteractionSource() }
    val confirmHovered by confirmInteractionSource.collectIsHoveredAsState()
    val backColor by animateColorAsState(
        targetValue = if (backHovered) QuinnipiacBlueLight else QuinnipiacBlue,
        label = "detailBackHover"
    )
    val confirmColor by animateColorAsState(
        targetValue = if (confirmHovered) QuinnipiacBlueLight else QuinnipiacBlue,
        label = "detailConfirmHover"
    )

    Column(modifier = Modifier.padding(16.dp)){

        Button(onClick = onNavigateBack,
            interactionSource = backInteractionSource,
            modifier = Modifier
                .padding(top = 20.dp)
                .hoverable(backInteractionSource),
            colors = ButtonDefaults.buttonColors(
                containerColor = backColor
            )) {
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
            Text(
                text = "MealPoints: ${item.pointsAmount}",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // price info
            Text(
                text = "Price: $${item.askingPrice}",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = QuinnipiacBlue
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Ratio: ${
                    if (item.askingPrice > 0.0) {
                        String.format(Locale.US, "%.2f points per dollar", item.pointsAmount / item.askingPrice)
                    } else {
                        "0 points per dollar"
                    }
                }",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { },
                interactionSource = confirmInteractionSource,
                modifier = Modifier
                    .hoverable(confirmInteractionSource),
                colors = ButtonDefaults.buttonColors(
                    containerColor = confirmColor
                )
            ) {
                Text("Confirm Purchase")
            }
        }
    }
}
