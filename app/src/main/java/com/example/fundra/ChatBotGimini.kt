package com.example.fundra

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ChatBotGimini : AppCompatActivity() {

    lateinit var editTextInput: EditText
    lateinit var editTextOutput: EditText
    lateinit var chat: Chat
    var stringBuilder: StringBuilder = java.lang.StringBuilder()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bot_gimini)

        editTextInput = findViewById(R.id.editTextTextInput)
        editTextOutput = findViewById(R.id.editTextTextOutput)

    }

    fun sendButton(view: View) {
        if (!::chat.isInitialized) {
            val generativeModel = GenerativeModel(
                modelName = "gemini-pro",
                apiKey = "AIzaSyCvyg7b0zNIf2PbMIC1FXRphrfA-MywDhQ"
            )
            chat = generativeModel.startChat(
                history = listOf(
                    content(role = "user") { text("Hello, I have 2 dogs in my house.") },
                    content(role = "model") { text("Great to meet you. What would you like to know?") }
                )
            )
            stringBuilder.append("Hello, I have 2 dogs in my house.\n\n")
            stringBuilder.append("Great to meet you. What would you like to know?\n\n")
        }

        stringBuilder.append(editTextInput.text.toString() + "\n\n")

        MainScope().launch {
            val result = chat.sendMessage(editTextInput.text.toString())
            stringBuilder.append(result.text + "\n\n")
            editTextOutput.setText(stringBuilder.toString())
            editTextInput.setText("")
        }
    }
}