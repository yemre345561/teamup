package com.acm431.teamup.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.acm431.teamup.R
import com.acm431.teamup.data.repository.AuthRepository
import com.acm431.teamup.viewmodel.AuthViewModel
import com.acm431.teamup.viewmodel.AuthViewModelFactory
import com.acm431.teamup.viewmodel.LoginState

@Composable
fun LoginScreen(navController: NavHostController) {
    // AuthRepository oluştur
    val authRepository = remember { AuthRepository() }

    // AuthViewModel oluştur (Factory ile)
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(authRepository)
    )

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginState by authViewModel.loginState.collectAsState()
    val autoLoginState by authViewModel.autoLoginState.collectAsState()

    val backgroundColor = Color(0xFFFFFCEF)
    val inputBackground = Color(0xFFD2E4F3)
    val primaryBlue = Color(0xFF274472)

    // 🚀 **Auto Login Kontrolü**
    LaunchedEffect(autoLoginState) {
        if (autoLoginState) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    // 🚨 **Auto Login Yükleniyor Ekranı**
    if (autoLoginState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    // 📝 **Login Ekranı**
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.teamup_logo),
            contentDescription = "TeamUp Logo",
            modifier = Modifier
                .size(140.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Fit
        )

        // Title
        Text(
            text = "TeamUp",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = primaryBlue,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Email Input
        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email", color = Color.Gray) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = inputBackground,
                unfocusedContainerColor = inputBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(25.dp),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Password Input
        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password", color = Color.Gray) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = inputBackground,
                unfocusedContainerColor = inputBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(25.dp),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        Button(
            onClick = {
                authViewModel.login(email, password)
            },
            colors = ButtonDefaults.buttonColors(containerColor = primaryBlue),
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(50.dp)
        ) {
            Text(text = "Log in", fontSize = 18.sp, color = Color.White)
        }

        // Login State Feedback
        when (loginState) {
            is LoginState.Loading -> CircularProgressIndicator()
            is LoginState.Success -> {
                navController.navigate("home") { popUpTo("login") { inclusive = true } }
            }
            is LoginState.Error -> {
                Text(
                    text = (loginState as LoginState.Error).message,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            else -> {}
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Register Button
        OutlinedButton(
            onClick = { navController.navigate("userTypeSelection") },
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = primaryBlue),
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(40.dp)
        ) {
            Text(text = "Register", fontSize = 14.sp)
        }
    }
}
