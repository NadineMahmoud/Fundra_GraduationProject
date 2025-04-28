package com.example.fundra.menu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundra.Home
import com.example.fundra.Message
import com.example.fundra.MessageAdapter
import com.example.fundra.databinding.ActivityChatBotBinding
import okhttp3.*
import com.google.gson.JsonParser
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class ChatBotActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBotBinding
    private lateinit var messageAdapter: MessageAdapter
    private val messageList = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()

        binding.backButton.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter(messageList)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatBotActivity)
            adapter = messageAdapter
        }
    }

    private fun setupListeners() {
        binding.sendButton.setOnClickListener {
            val userMessage = binding.messageEditText.text.toString()
            if (userMessage.isNotBlank()) {
                addMessage(Message(userMessage, Message.SENT_BY_ME))
                fetchGeminiResponse(userMessage)
                binding.messageEditText.text.clear()
            }
        }
    }

    private fun addMessage(message: Message) {
        messageList.add(message)
        messageAdapter.notifyItemInserted(messageList.size - 1)
        binding.recyclerView.scrollToPosition(messageList.size - 1)
    }

    private fun fetchGeminiResponse(userMessage: String) {
        val apiKey = ""
        val apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=$apiKey"
        val client = OkHttpClient()
        val requestBody = """
            {
                "contents": [
                    {
                        "role": "user",
                        "parts": [{"text": "$userMessage"}]
                    }
                ]
            }
        """.trimIndent()

        val request = Request.Builder()
            .url(apiUrl)
            .post(requestBody.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull()))
            .build()

        Thread {
            try {
                val response: Response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    response.body?.string()?.let { responseBody ->
                        val jsonResponse = JsonParser.parseString(responseBody).asJsonObject
                        val candidatesArray = jsonResponse.getAsJsonArray("candidates")
                        if (candidatesArray != null && candidatesArray.size() > 0) {
                            val firstCandidate = candidatesArray.get(0).asJsonObject
                            val contentArray = firstCandidate.getAsJsonArray("content")
                            if (contentArray != null && contentArray.size() > 0) {
                                val firstContent = contentArray.get(0).asJsonObject
                                val partsArray = firstContent.getAsJsonArray("parts")
                                if (partsArray != null && partsArray.size() > 0) {
                                    val botResponse =
                                        partsArray.get(0).asJsonObject.get("text")?.asString
                                    runOnUiThread {
                                        addMessage(
                                            Message(
                                                botResponse ?: "No response",
                                                Message.SENT_BY_BOT
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    runOnUiThread {
                        addMessage(Message("Error: ${response.code}", Message.SENT_BY_BOT))
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    addMessage(Message("Request failed", Message.SENT_BY_BOT))
                }
            }
        }.start()
    }
}
