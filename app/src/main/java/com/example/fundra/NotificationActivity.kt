package com.example.fundra

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notifications.NotificationAdapter

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewNotifications)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val notificationItems = listOf(
            NotificationItem.SectionHeader("Today"),
            NotificationItem.Notification(
                "Lokin",
                "THE profits ARE EXPECTED TO EXCEED 20% !"
            ),
            NotificationItem.Notification(
                "M-ANGEL",
                "HURRY UP AND BUY MORE SHARES TO ENSURE GREATER PROFIT !"
            ),
            NotificationItem.SectionHeader("Yesterday"),
            NotificationItem.Notification(
                "MORE POINTS",
                "YOU RECEIVED 20 ADDITIONAL POINTS!"
            ),
            NotificationItem.Notification(
                "Lokin",
                "THE profits ARE EXPECTED TO EXCEED 20% !"
            ),
            NotificationItem.Notification(
                "M-ANGEL",
                "HURRY UP AND BUY MORE SHARES TO ENSURE GREATER PROFIT !"
            ),
            NotificationItem.Notification(
                "MORE POINTS",
                "YOU RECEIVED 20 ADDITIONAL POINTS!"
            ),
            NotificationItem.Notification(
                "Lokin",
                "THE profits ARE EXPECTED TO EXCEED 20% !"
            ),
            NotificationItem.Notification(
                "MORE POINTS",
                "YOU RECEIVED 20 ADDITIONAL POINTS!"
            )
        )

        val adapter = NotificationAdapter(notificationItems)
        recyclerView.adapter = adapter

        val backArrow: ImageView = findViewById(R.id.backButton)
        backArrow.setOnClickListener {
            finish()
        }
    }
}
