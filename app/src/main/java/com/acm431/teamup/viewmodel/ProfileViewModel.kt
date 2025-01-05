package com.acm431.teamup.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acm431.teamup.data.model.Post
import com.acm431.teamup.data.model.User
import com.acm431.teamup.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {
    private val _userProfile = MutableStateFlow<User?>(null)
    val userProfile: StateFlow<User?> = _userProfile

    private val _userPosts = MutableStateFlow<List<Post>>(emptyList())
    val userPosts: StateFlow<List<Post>> = _userPosts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadUserProfile(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val profile = repository.getUserProfile(userId)
            _userProfile.value = profile
            profile?.let {
                _userPosts.value = repository.getUserPosts(userId)
            }
            _isLoading.value = false
        }
    }

    fun uploadProfileImage(userId: String, imageUri: Uri) {
        viewModelScope.launch {
            _isLoading.value = true
            val imageUrl = repository.uploadProfileImage(userId, imageUri)
            imageUrl?.let {
                repository.updateProfile(userId, profileImageUrl = it, cvUri = null)
                loadUserProfile(userId)
            }
            _isLoading.value = false
        }
    }
}
