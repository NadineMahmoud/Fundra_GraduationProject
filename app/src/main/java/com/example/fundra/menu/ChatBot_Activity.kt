package com.example.fundra.menu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundra.ChatMessage
import com.example.fundra.Home
import com.example.fundra.MessageAdapter
import com.example.fundra.databinding.ActivityChatBotBinding
class ChatBotActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBotBinding
    private lateinit var adapter: MessageAdapter
    private val messages = mutableListOf<ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val staticQA = mapOf(
            "what is crowdfunding?" to "Crowdfunding is a method of raising money from a large number of people, typically via the internet.",
            "what is donation-based crowdfunding?" to "It involves people donating money to a cause without expecting anything in return.",
            "what is reward-based crowdfunding?" to "It gives contributors a reward, like a product or service, in exchange for support.",
            "what is revenue-based crowdfunding?" to "Investors earn returns based on a percentage of the business's revenue.",
            "how does investing in crowdfunding work?" to "You support a business in exchange for future returns, rewards, or equity.",
            "is crowdfunding safe?" to "It can be safe if you use trusted platforms and understand the risks."
        )

        adapter = MessageAdapter(messages)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        binding.sendButton.setOnClickListener {
            val userInput = binding.messageEditText.text.toString().trim()
            if (userInput.isNotEmpty()) {
                addMessage(userInput, true)
                val reply = staticQA[userInput.lowercase()] ?: "Sorry, I don't understand this question yet."
                addMessage(reply, false)
                binding.messageEditText.text.clear()
            }
        }

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, Home::class.java))
            finish()
        }
    }

    private fun addMessage(text: String, isUser: Boolean) {
        messages.add(ChatMessage(text, isUser))
        adapter.notifyItemInserted(messages.size - 1)
        binding.recyclerview.scrollToPosition(messages.size - 1)
    }
}
