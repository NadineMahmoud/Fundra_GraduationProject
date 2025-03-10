package com.example.fundra

import MessageAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class ChatBot : AppCompatActivity() {
    private val API_KEY =  BuildConfig.OPENAI_API_KEY
    private lateinit var recyclerView: RecyclerView
    private lateinit var welcomeText: TextView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var messageList: MutableList<Message>
    private lateinit var messageAdapter: MessageAdapter
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bot)

        messageList = ArrayList()
        recyclerView = findViewById(R.id.recycler_view)
        welcomeText = findViewById(R.id.chatT)
        messageEditText = findViewById(R.id.message_edit_text)
        sendButton = findViewById(R.id.send_bt)
        messageAdapter = MessageAdapter(messageList)
        recyclerView.adapter = messageAdapter

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager

        sendButton.setOnClickListener {
            val question = messageEditText.text.toString().trim()
            if (question.isNotEmpty()) {
                addToChat(question, Message.SENT_BY_ME)
                messageEditText.setText("")
                callAPI(question)
                welcomeText.visibility = View.GONE
            }
        }
    }

    private fun addToChat(message: String, sentBy: String) {
        runOnUiThread {
            messageList.add(Message(message, sentBy))
            messageAdapter.notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(messageAdapter.itemCount)
        }
    }

    private fun addResponse(response: String?) {
        response?.let {
            messageList.removeAt(messageList.size - 1)
            addToChat(it, Message.SENT_BY_BOT)
        }
    }

    private fun callAPI(question: String) {
        messageList.add(Message("Typing...", Message.SENT_BY_BOT))
        val jsonBody = JSONObject()
        try {
            jsonBody.put("model", "gpt-3.5-turbo")
            jsonBody.put("messages", listOf(JSONObject().apply {
                put("role", "user")
                put("content", question)
            }))
            jsonBody.put("max_tokens", 4000)
            jsonBody.put("temperature", 0)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val body = jsonBody.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .header("Authorization", "Bearer $API_KEY")
            .header("Content-Type", "application/json")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                addResponse("Failed to load response: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { responseBody ->
                    try {
                        val jsonObject = JSONObject(responseBody)
                        val choices = jsonObject.getJSONArray("choices")
                        val result = choices.getJSONObject(0).getJSONObject("message").getString("content")
                        addResponse(result.trim())
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        addResponse("Error parsing response")
                    }
                } ?: addResponse("Empty response from server")
            }
        })
    }

    companion object {
        private val JSON = "application/json; charset=utf-8".toMediaType()
    }
}
