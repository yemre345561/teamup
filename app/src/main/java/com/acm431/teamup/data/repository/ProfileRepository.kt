package com.acm431.teamup.data.repository

import android.net.Uri
import com.acm431.teamup.data.model.Post
import com.acm431.teamup.data.model.User
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class ProfileRepository {
    private val database = FirebaseDatabase.getInstance()
    private val userRef = database.getReference("users")
    private val postsRef = database.getReference("posts")
    private val storage = FirebaseStorage.getInstance()

    // Kullanıcı profilini getir
    suspend fun getUserProfile(userId: String): User? {
        return try {
            val snapshot = userRef.child(userId).get().await()
            val user = snapshot.getValue(User::class.java)
            user?.let {
                val userPosts = getUserPosts(userId)
                it.copy(posts = userPosts.map { post -> post.id })
            }
        } catch (e: Exception) {
            null
        }
    }

    // Kullanıcının postlarını getir
    suspend fun getUserPosts(userId: String): List<Post> {
        return try {
            val snapshot = postsRef.orderByChild("userId").equalTo(userId).get().await()
            snapshot.children.mapNotNull { it.getValue(Post::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Profil fotoğrafı yükle
    suspend fun uploadProfileImage(userId: String, imageUri: Uri): String? {
        return try {
            val ref = storage.getReference("profile_images/$userId.jpg")
            ref.putFile(imageUri).await()
            ref.downloadUrl.await().toString()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getUserProfileImageUrl(userId: String): String? {
        return try {
            val snapshot = userRef.child(userId).child("profileImageUrl").get().await()
            snapshot.getValue(String::class.java)
        } catch (e: Exception) {
            null
        }
    }

    // CV yükle
    suspend fun uploadCv(userId: String, cvUri: Uri): String? {
        return try {
            val ref = storage.getReference("cvs/$userId.pdf")
            ref.putFile(cvUri).await()
            ref.downloadUrl.await().toString()
        } catch (e: Exception) {
            null
        }
    }

    // Profil güncelle
    suspend fun updateProfile(userId: String, profileImageUrl: String?, cvUri: String?) {
        val updates = mutableMapOf<String, Any>()
        profileImageUrl?.let { updates["profileImageUrl"] = it }
        cvUri?.let { updates["cvUri"] = it }
        userRef.child(userId).updateChildren(updates).await()
    }
}
