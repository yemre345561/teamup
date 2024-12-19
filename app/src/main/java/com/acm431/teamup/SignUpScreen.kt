package com.acm431.teamup

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController

@Composable
fun SignUpScreen(userType: String, navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var extraInfo1 by remember { mutableStateOf("") }
    var extraInfo2 by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var cvFileUri by remember { mutableStateOf<Uri?>(null) }

    // File Picker Launcher
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        cvFileUri = uri
    }

    val backgroundColor = Color(0xFFF8EECF)
    val inputBackground = Color(0xFFCEE6F3)
    val primaryBlue = Color(0xFF274472)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        // Back Button
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .size(48.dp)
                .padding(top = 8.dp, bottom = 16.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = primaryBlue)
        }

        // Logo
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.teamup_logo),
            contentDescription = "TeamUp Logo",
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp),
            contentScale = ContentScale.Fit
        )

        // Title
        Text(
            text = "TeamUp",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = primaryBlue,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // Subtitle
        Text(
            text = "${userType} Sign In",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = primaryBlue,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )

        // Input Fields
        InputField("Name", name, { name = it }, inputBackground)
        InputField("Surname", surname, { surname = it }, inputBackground)
        InputField("E-Mail", email, { email = it }, inputBackground, KeyboardType.Email)
        InputField("Password", password, { password = it }, inputBackground, KeyboardType.Password)
        InputField(if (userType == "Investor") "Company" else "University", extraInfo1, { extraInfo1 = it }, inputBackground)
        InputField(if (userType == "Investor") "Position" else "Department", extraInfo2, { extraInfo2 = it }, inputBackground)
        InputField("Gender", gender, { gender = it }, inputBackground)

        // Upload CV and Sign In Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Upload CV Button
            Button(
                onClick = { filePickerLauncher.launch("application/pdf") },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = if (cvFileUri == null) "Upload CV" else "CV Selected",
                    color = primaryBlue,
                    fontSize = 16.sp
                )
            }

            // Sign In Button
            Button(
                onClick = { /* Handle sign-in logic here */ },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = primaryBlue),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .height(48.dp)
            ) {
                Text("Sign in", color = Color.White, fontSize = 16.sp)
            }
        }

        // Display Selected CV File Name
        if (cvFileUri != null) {
            Text(
                text = "Selected File: ${cvFileUri?.lastPathSegment}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun InputField(
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    backgroundColor: Color,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        )
    )
}






