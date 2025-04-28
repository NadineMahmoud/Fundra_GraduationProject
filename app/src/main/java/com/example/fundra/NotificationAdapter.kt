package com.example.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import com.example.fundra.NotificationItem
import com.example.fundra.R

class NotificationAdapter(private val items: List<NotificationItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_NOTIFICATION = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is NotificationItem.SectionHeader -> TYPE_HEADER
            is NotificationItem.Notification -> TYPE_NOTIFICATION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_section_header, parent, false)
            SectionHeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_notification, parent, false)
            NotificationViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is NotificationItem.SectionHeader -> {
                (holder as SectionHeaderViewHolder).bind(item)
            }
            is NotificationItem.Notification -> {
                (holder as NotificationViewHolder).bind(item)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class SectionHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sectionHeader: TextView = itemView.findViewById(R.id.sectionHeader)
        private val markAsReadButton: TextView = itemView.findViewById(R.id.markAsReadButton)

        fun bind(header: NotificationItem.SectionHeader) {
            sectionHeader.text = header.title
            if (header.title == "Today") {
                markAsReadButton.visibility = View.VISIBLE
            } else {
                markAsReadButton.visibility = View.GONE
            }

            markAsReadButton.setOnClickListener {
                Log.d("NotificationAdapter", "Mark as read clicked for ${header.title}")
            }
        }
    }

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val notificationTitle: TextView = itemView.findViewById(R.id.notificationTitle)
        private val notificationMessage: TextView = itemView.findViewById(R.id.notificationMessage)
        private val iconLokin: ImageView = itemView.findViewById(R.id.icon_lokin)
        private val iconAngle: ImageView = itemView.findViewById(R.id.icon_angle)
        private val iconCoins: ImageView = itemView.findViewById(R.id.icon_coins)

        fun bind(notification: NotificationItem.Notification) {
            notificationTitle.text = notification.title
            notificationMessage.text = notification.message

            iconLokin.visibility = View.GONE
            iconAngle.visibility = View.GONE
            iconCoins.visibility = View.GONE

            val title = notification.title.trim().uppercase()
            Log.d("NotificationAdapter", "Title after trim and uppercase: '$title'")

            when (title) {
                "LOKIN" -> {
                    Log.d("NotificationAdapter", "Showing ic_lokin for LOKIN")
                    iconLokin.visibility = View.VISIBLE
                }
                "M-ANGEL" -> {
                    Log.d("NotificationAdapter", "Showing ic_angle for M-ANGEL")
                    iconAngle.visibility = View.VISIBLE
                }
                "MORE POINTS" -> {
                    Log.d("NotificationAdapter", "Showing ic_coins for MORE POINTS")
                    iconCoins.visibility = View.VISIBLE
                }
                else -> {
                    Log.d("NotificationAdapter", "No matching title for icons: $title")
                }
            }
        }
    }
}