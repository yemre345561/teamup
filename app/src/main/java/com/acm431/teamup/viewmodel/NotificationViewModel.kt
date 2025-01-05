package com.acm431.teamup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acm431.teamup.data.model.Notification
import com.acm431.teamup.data.repository.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(private val repository: NotificationRepository) : ViewModel() {
    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadNotifications(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val notificationsList = repository.getNotifications(userId)
            val updatedNotifications = notificationsList.map { notification ->
                val updatedProfileImageUrl = repository.getUserProfileImageUrl(notification.realUserId)
                notification.copy(profileImageUrl = updatedProfileImageUrl ?: notification.profileImageUrl)
            }
            _notifications.value = updatedNotifications
            _isLoading.value = false
        }
    }
}
