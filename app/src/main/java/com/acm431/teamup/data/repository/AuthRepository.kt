package com.acm431.teamup.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    private val database = FirebaseDatabase.getInstance().getReference("users")

    private val storage = FirebaseStorage.getInstance()

    // 📤 CV Dosyasını Yükle ve URL Al
    suspend fun uploadCvToStorage(cvUri: Uri, userId: String): String? {
        return try {
            val storageRef = storage.reference.child("files/${userId}_cv.pdf")
            storageRef.putFile(cvUri).await()
            storageRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun changePassword(oldPassword: String, newPassword: String): Boolean {
        return try {
            val user = auth.currentUser ?: return false
            val email = user.email ?: return false

            // Eski şifreyi doğrula
            auth.signInWithEmailAndPassword(email, oldPassword).await()

            user.updatePassword(newPassword).await()

            // Do not keep the password in Realtime Database. Remove any older copy.
            // Auth has already changed, so a failed cleanup does not undo the update.
            try {
                database.child(user.uid).child("password").removeValue().await()
            } catch (cleanup: Exception) {
                println("Could not remove stored password: ${cleanup.message}")
            }

            true
        } catch (e: Exception) {
            println("Error changing password: ${e.message}")
            false
        }
    }

    // 📝 Kullanıcı Kayıt
    suspend fun register(
        userType: String,
        name: String,
        surname: String,
        email: String,
        password: String,
        extraInfo1: String,
        extraInfo2: String,
        gender: String,
        cvUri: Uri?
    ): Boolean {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: return false

            val cvUrl = if (cvUri != null) {
                uploadCvToStorage(cvUri, userId)
            } else {
                null
            }

            val userData = mapOf(
                "userType" to userType,
                "name" to name,
                "surname" to surname,
                "email" to email,
                "extraInfo1" to extraInfo1,
                "extraInfo2" to extraInfo2,
                "gender" to gender,
                "cvUrl" to (cvUrl ?: "Not Provided")
            )

            database.child(userId).setValue(userData).await()
            true
        } catch (e: Exception) {
            false
        }
    }


    // Kullanıcı Girişi
    suspend fun login(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            null
        }
    }

    // Kullanıcı Giriş Yapmış mı Kontrolü
    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    // Kullanıcı Bilgilerini Getir
    suspend fun getUserDetails(): Map<String, String>? {
        val userId = auth.currentUser?.uid ?: return null
        return try {
            val snapshot = database.child(userId).get().await()
            snapshot.value as? Map<String, String>
        } catch (e: Exception) {
            null
        }
    }

    // Oturumu Kapatma
    fun logout() {
        auth.signOut()
    }

    // Şu Anki Kullanıcı
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }


}
