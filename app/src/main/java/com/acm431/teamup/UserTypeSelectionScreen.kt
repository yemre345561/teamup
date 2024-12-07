package com.acm431.teamup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun UserTypeSelectionScreen(navController: NavHostController, onUserTypeSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F5E7))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select User Type",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
            color = Color(0xFF274472),
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Button(
            onClick = { onUserTypeSelected("Investor") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Investor")
        }
        Button(
            onClick = { onUserTypeSelected("Student") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Student")
        }
        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Back")
        }
    }
}

