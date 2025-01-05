package com.acm431.teamup.data.repository

import com.acm431.teamup.data.model.Notification
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class NotificationRepository {
    private val database: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("notifications")

    // Bildirim Ekle
    suspend fun addNotification(notification: Notification) {
        val notificationRef = database.child(notification.userId).push()
        notificationRef.setValue(notification.copy(id = notificationRef.key ?: "")).await()
    }

    // Kullanıcıya Özel Bildirimleri Getir
    suspend fun getNotifications(userId: String): List<Notification> {
        return try {
            // Kullanıcının bildirimlerini al
            val snapshot = database.child(userId).get().await()
            val notifications = snapshot.children.mapNotNull { it.getValue(Notification::class.java) }

            // createdAt'e göre azalan şekilde sırala
            notifications.sortedByDescending { it.createdAt }
        } catch (e: Exception) {
            println("Notification Fetch Error: ${e.message}")
            emptyList()
        }
    }
    suspend fun getUserProfileImageUrl(userId: String): String? {
        return try {
            val snapshot = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(userId)
                .child("profileImageUrl")
                .get()
                .await()
            snapshot.getValue(String::class.java)
        } catch (e: Exception) {
            null
        }
    }
}
