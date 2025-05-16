package com.example.fundra

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
data class ChatMessage(val message: String, val isUser: Boolean)
class MessageAdapter(private val messages: List<ChatMessage>) :
    RecyclerView.Adapter<MessageAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val leftLayout: LinearLayout = itemView.findViewById(R.id.left_chat_view)
        val rightLayout: LinearLayout = itemView.findViewById(R.id.right_chat_view)
        val leftText: TextView = itemView.findViewById(R.id.left_chat_text_view)
        val rightText: TextView = itemView.findViewById(R.id.right_chat_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        if (message.isUser) {
            holder.rightLayout.visibility = View.VISIBLE
            holder.leftLayout.visibility = View.GONE
            holder.rightText.text = message.message
        } else {
            holder.leftLayout.visibility = View.VISIBLE
            holder.rightLayout.visibility = View.GONE
            holder.leftText.text = message.message
        }
    }

    override fun getItemCount(): Int = messages.size
}
