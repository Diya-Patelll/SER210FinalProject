package com.example.ser210finalproject.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ser210finalproject.ui.theme.QuinnipiacBlue
import com.example.ser210finalproject.ui.theme.QuinnipiacGold
import com.example.ser210finalproject.ui.theme.QuinnipiacGoldSoft

enum class AppDestination(val route: String, val label: String, val badge: String) {
    Marketplace("marketplace", "Market", "M"),
    Profile("profile", "Profile", "P"),
    Seller("seller", "Seller", "S")
}

@Composable
fun AppScaffold(
    currentDestination: AppDestination,
    onNavigate: (AppDestination) -> Unit,
    title: String,
    subtitle: String? = null,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(QuinnipiacBlue)
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = QuinnipiacGoldSoft
                    )
                }
            }
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                AppDestination.entries.forEach { destination ->
                    NavigationBarItem(
                        selected = destination == currentDestination,
                        onClick = { onNavigate(destination) },
                        icon = {
                            Row(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(
                                        if (destination == currentDestination) QuinnipiacGold else QuinnipiacGoldSoft
                                    )
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = destination.badge,
                                    color = QuinnipiacBlue,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        },
                        label = { Text(destination.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = QuinnipiacBlue,
                            selectedTextColor = QuinnipiacBlue,
                            indicatorColor = QuinnipiacGoldSoft,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}