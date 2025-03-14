package com.example.fundra

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Message(val message: String, val sentBy: String) {
    companion object {
        const val SENT_BY_ME = "me"
        const val SENT_BY_BOT = "bot"
    }
}

class MessageAdapter(var messageList: MutableList<Message>) :
    RecyclerView.Adapter<MessageAdapter.MesgViewHolder>() {

    inner class MesgViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val leftChatView: LinearLayout = v.findViewById(R.id.left_chat_view)
        val leftTextView: TextView = v.findViewById(R.id.left_chat_text_view)
        val rightChatView: LinearLayout = v.findViewById(R.id.right_chat_view)
        val rightTextView: TextView = v.findViewById(R.id.right_chat_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MesgViewHolder {
        val chatView = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_item, parent, false)
        return MesgViewHolder(chatView)
    }

    override fun getItemCount(): Int = messageList.size

    override fun onBindViewHolder(holder: MesgViewHolder, position: Int) {
        val message = messageList[position]
        if (message.sentBy == Message.SENT_BY_ME) {
            holder.leftChatView.visibility = View.GONE
            holder.rightChatView.visibility = View.VISIBLE
            holder.rightTextView.text = message.message
        } else {
            holder.leftChatView.visibility = View.VISIBLE
            holder.rightChatView.visibility = View.GONE
            holder.leftTextView.text = message.message
        }
    }
}
