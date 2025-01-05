package com.acm431.teamup.ui.login

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.acm431.teamup.R
import com.acm431.teamup.data.repository.AuthRepository
import com.acm431.teamup.viewmodel.AuthViewModel
import com.acm431.teamup.viewmodel.AuthViewModelFactory
import com.acm431.teamup.viewmodel.RegisterState

@Composable
fun SignUpScreen(userType: String, navController: NavHostController) {
    // ViewModel Bağlantısı
    val authRepository = remember { AuthRepository() }
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(authRepository))

    // State Değişkenleri
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var extraInfo1 by remember { mutableStateOf("") }
    var extraInfo2 by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var cvFileUri by remember { mutableStateOf<Uri?>(null) }

    val registerState by authViewModel.registerState.collectAsState()

    // Dosya Seçici
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        cvFileUri = uri
    }

    // Renkler
    val backgroundColor = Color(0xFFFFFCEF)
    val inputBackground = Color(0xFFCEE6F3)
    val primaryBlue = Color(0xFF274472)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Geri Butonu
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.Start)
                .size(48.dp)
                .padding(top = 8.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = primaryBlue)
        }

        // Logo
        Image(
            painter = painterResource(id = R.drawable.teamup_logo),
            contentDescription = "TeamUp Logo",
            modifier = Modifier
                .size(140.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Fit
        )

        // Başlık
        Text(
            text = "TeamUp",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = primaryBlue,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "$userType Sign Up",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = primaryBlue,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Input Alanları
        InputField("Name", name, { name = it }, inputBackground)
        InputField("Surname", surname, { surname = it }, inputBackground)
        InputField("E-Mail", email, { email = it }, inputBackground, KeyboardType.Email)
        InputField("Password", password, { password = it }, inputBackground, KeyboardType.Password)
        InputField(
            if (userType == "Investor") "Company" else "University",
            extraInfo1,
            { extraInfo1 = it },
            inputBackground
        )
        InputField(
            if (userType == "Investor") "Position" else "Department",
            extraInfo2,
            { extraInfo2 = it },
            inputBackground
        )
        InputField("Gender", gender, { gender = it }, inputBackground)

        // Dosya Seçici ve Kayıt Butonları
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
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
                    fontSize = 14.sp
                )
            }

            Button(
                onClick = {
                    authViewModel.register(
                        userType,
                        name,
                        surname,
                        email,
                        password,
                        extraInfo1,
                        extraInfo2,
                        gender,
                        cvFileUri
                    )
                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = primaryBlue),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .height(48.dp)
            ) {
                Text("Sign Up", color = Color.White, fontSize = 14.sp)
            }
        }

        // Durum Mesajları
        when (registerState) {
            is RegisterState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            }
            is RegisterState.Success -> {
                navController.navigate("home") {
                    popUpTo("signup") { inclusive = true }
                }
            }
            is RegisterState.Error -> {
                Text(
                    text = (registerState as RegisterState.Error).message,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            else -> {}
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
            unfocusedContainerColor = backgroundColor
        )
    )
}
