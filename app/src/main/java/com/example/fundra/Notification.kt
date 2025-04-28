package com.example.fundra


sealed class NotificationItem {
    data class SectionHeader(val title: String) : NotificationItem()
    data class Notification(val title: String, val message: String) : NotificationItem()
}
