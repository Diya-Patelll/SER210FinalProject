package com.example.ser210finalproject.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ser210finalproject.ui.theme.QuinnipiacBlue
import com.example.ser210finalproject.ui.theme.QuinnipiacBlueLight
import com.example.ser210finalproject.ui.theme.QuinnipiacGoldSoft
import com.example.ser210finalproject.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onLoginSuccess: (String) -> Unit
) {
    var quEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(QuinnipiacBlue, QuinnipiacBlueLight, QuinnipiacGoldSoft)
                )
            )
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Sign in",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )


                OutlinedTextField(
                    value = quEmail,
                    onValueChange = { quEmail = it },
                    label = { Text("Quinnipiac email") },
                    placeholder = { Text("name.lastname@qu.edu") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = errorMessage != null
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        val email = quEmail.trim().lowercase()

                        if (loginViewModel.isValidQuEmail(email)) {
                            coroutineScope.launch {
                                loginViewModel.loginOrRegister(email)
                                onLoginSuccess(email)
                            }
                        } else {
                            errorMessage = "Login with QU Email"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Enter marketplace")
                }
                TextButton(
                    onClick = {
                        val demoEmail = "bob.cat@quinnipiac.edu"
                        coroutineScope.launch {
                            loginViewModel.loginOrRegister(demoEmail)
                            onLoginSuccess(demoEmail)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Continue with demo access", color = QuinnipiacBlue)
                }
            }
        }
    }
}
