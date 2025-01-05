package com.acm431.teamup.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acm431.teamup.data.model.Post
import com.acm431.teamup.data.model.Comment
import com.acm431.teamup.data.repository.PostRepository
import com.acm431.teamup.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository) : ViewModel() {
    private val _savedPosts = MutableStateFlow<List<String>>(emptyList())
    val savedPosts: StateFlow<List<String>> = _savedPosts
    val posts: StateFlow<List<Post>> = repository.posts

    private val _searchResults = MutableStateFlow<List<Post>>(emptyList())
    val searchResults: StateFlow<List<Post>> = _searchResults

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading

    private val _uploadResult = MutableStateFlow(false)
    val uploadResult: StateFlow<Boolean> = _uploadResult

    init {
        startListeningToPosts()
    }

    private fun startListeningToPosts() {
        viewModelScope.launch {
            repository.startListeningToPosts()
            _isLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.stopListeningToPosts()
    }

    fun createPostWithMedia(
        title: String,
        caption: String,
        photos: List<Uri>,
        files: List<Uri>,
        tags: String,
        userId: String,
        username: String,
        userType: String
    ) {
        viewModelScope.launch {
            try {
                _isUploading.value = true
                val success = repository.createPostWithMedia(
                    title, caption, photos, files, tags, userId, username, userType
                )
                if (success) {
                    println("Post successfully uploaded to the database!")
                } else {
                    println("Failed to upload post to the database.")
                }
            } catch (e: Exception) {
                println("ViewModel Exception: ${e.message}")
            } finally {
                _isUploading.value = false
            }
        }
    }

    fun getSortedComments(post: Post): List<Comment> {
        return repository.getSortedComments(post)
    }

//    fun loadPosts() {
//        viewModelScope.launch {
//            _isLoading.value = true
//            _posts.value = repository.getPosts()
//            _isLoading.value = false
//        }
//    }

//    fun loadPostsByUserType(userType: String) {
//        viewModelScope.launch {
//            _isLoading.value = true
//            _posts.value = repository.getPostsByUserType(userType)
//            _isLoading.value = false
//        }
//    }

    private val _userProfileImages = MutableStateFlow<Map<String, String>>(emptyMap())
    val userProfileImages: StateFlow<Map<String, String>> = _userProfileImages
    val profileRepository = ProfileRepository()

    fun loadUserProfileImage(userId: String) {
        viewModelScope.launch {
            if (!_userProfileImages.value.containsKey(userId)) {
                val imageUrl = profileRepository.getUserProfileImageUrl(userId)
                imageUrl?.let {
                    _userProfileImages.value += (userId to it)
                }
            }
        }
    }

    fun toggleSavePost(postId: String, userId: String) {
        viewModelScope.launch {
            repository.toggleSavePost(postId, userId)
            loadSavedPosts(userId)
        }
    }

    fun loadSavedPosts(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _savedPosts.value = repository.getSavedPosts(userId).map { it.id }
            _isLoading.value = false
        }
    }

    fun searchPosts(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _searchResults.value = repository.searchPosts(query)
            _isLoading.value = false
        }
    }

    fun toggleLike(postId: String, userId: String, username: String, ownerId: String, profileImageUrl: String) {
        viewModelScope.launch {
            repository.toggleLike(postId, userId,username,ownerId,profileImageUrl)

        }
    }

    fun addComment(postId: String, comment: Comment, ownerId: String, realUserId: String, profileImageUrl: String) {
        viewModelScope.launch {
            repository.addComment(postId, comment,ownerId,realUserId,profileImageUrl)

        }
    }

//    fun createPost(post: Post) {
//        viewModelScope.launch {
//            repository.createPost(post)
//            loadPosts()
//        }
//    }
}
