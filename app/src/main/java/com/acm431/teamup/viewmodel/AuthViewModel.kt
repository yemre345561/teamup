package com.acm431.teamup.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acm431.teamup.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    private val _autoLoginState = MutableStateFlow<Boolean>(false)
    val autoLoginState: StateFlow<Boolean> = _autoLoginState

    private val _userDetails = MutableStateFlow<Map<String, String>?>(null)
    val userDetails: StateFlow<Map<String, String>?> = _userDetails

    private val _passwordChangeState = MutableStateFlow<ChangePasswordState>(ChangePasswordState.Idle)
    val passwordChangeState: StateFlow<ChangePasswordState> = _passwordChangeState

    init {
        checkAutoLogin()
    }

    // Kullanıcı bilgilerini yükle
    fun loadUserDetails() {
        viewModelScope.launch {
            val details = authRepository.getUserDetails()
            _userDetails.value = details
        }
    }

    // 🟢 Auto Login
    private fun checkAutoLogin() {
        viewModelScope.launch {
            _autoLoginState.value = auth.currentUser != null
        }
    }

    // 🔐 Login
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val user = authRepository.login(email, password)
            if (user != null) {
                _loginState.value = LoginState.Success
            } else {
                _loginState.value = LoginState.Error("Login failed. Please check your credentials.")
            }
        }
    }

    fun register(
        userType: String,
        name: String,
        surname: String,
        email: String,
        password: String,
        extraInfo1: String,
        extraInfo2: String,
        gender: String,
        cvUri: Uri?
    ) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            val success = authRepository.register(
                userType, name, surname, email, password,
                extraInfo1, extraInfo2, gender, cvUri
            )
            if (success) {
                _registerState.value = RegisterState.Success
            } else {
                _registerState.value = RegisterState.Error("Registration failed. Please try again.")
            }
        }
    }

    // 🔄 Log Out
    fun logOut() {
        viewModelScope.launch {
            auth.signOut()
            _autoLoginState.value = false
        }
    }

    fun changePassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            _passwordChangeState.value = ChangePasswordState.Loading
            val success = authRepository.changePassword(oldPassword, newPassword)
            if (success) {
                _passwordChangeState.value = ChangePasswordState.Success
            } else {
                _passwordChangeState.value = ChangePasswordState.Error("Failed to change password.")
            }
        }
    }
}

// 🟠 Change Password State
sealed class ChangePasswordState {
    object Idle : ChangePasswordState()
    object Loading : ChangePasswordState()
    object Success : ChangePasswordState()
    data class Error(val message: String) : ChangePasswordState()
}

// Login Durumları
sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}
