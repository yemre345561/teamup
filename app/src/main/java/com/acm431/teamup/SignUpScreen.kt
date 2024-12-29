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
import com.google.firebase.database.FirebaseDatabase

@Composable
fun SignUpScreen(userType: String, navController: NavHostController) {
    // State variables for input fields
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var extraInfo1 by remember { mutableStateOf("") }
    var extraInfo2 by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var cvFileUri by remember { mutableStateOf<Uri?>(null) }
    var errorMessage by remember { mutableStateOf("") } // Error message for validation

    // File Picker Launcher
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        cvFileUri = uri
    }

    // Colors
    val backgroundColor = Color(0xFFFFFCEF)
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
            text = "$userType Sign Up",
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

        // Display Error Message
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        // Upload CV and Sign Up Buttons
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

            // Sign Up Button
            Button(
                onClick = {
                    if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        errorMessage = "Please fill in all required fields."
                    } else {
                        saveUserToDatabase(
                            userType,
                            name,
                            surname,
                            email,
                            password,
                            extraInfo1,
                            extraInfo2,
                            gender,
                            cvFileUri?.toString()
                        )
                        navController.navigate("home")
                    }
                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = primaryBlue),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .height(48.dp)
            ) {
                Text("Sign Up", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

// Save User Data to Firebase
fun saveUserToDatabase(
    userType: String,
    name: String,
    surname: String,
    email: String,
    password: String,
    extraInfo1: String,
    extraInfo2: String,
    gender: String,
    cvUri: String?
) {
    val database = FirebaseDatabase.getInstance().getReference("users")
    val userId = database.push().key ?: return

    val userData = mapOf(
        "userType" to userType,
        "name" to name,
        "surname" to surname,
        "email" to email,
        "password" to password,
        "extraInfo1" to extraInfo1,
        "extraInfo2" to extraInfo2,
        "gender" to gender,
        "cvUri" to (cvUri ?: "Not Provided")
    )

    database.child(userId).setValue(userData)
}

// InputField
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
            unfocusedContainerColor = backgroundColor
        )
    )
}







