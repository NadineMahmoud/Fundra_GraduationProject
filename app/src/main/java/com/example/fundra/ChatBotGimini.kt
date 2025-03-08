package com.example.fundra

import android.os.Bundle
import android.util.Log
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

    private lateinit var editTextInput: EditText
    private lateinit var editTextOutput: EditText
    private lateinit var chat: Chat
    private var stringBuilder: StringBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bot_gimini)

        editTextInput = findViewById(R.id.editTextTextInput)
        editTextOutput = findViewById(R.id.editTextTextOutput)

        // إنشاء الموديل أول ما تفتح الشاشة
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

        // إضافة الحوار الأول لمربع النص
        stringBuilder.append("Hello, I have 2 dogs in my house.\n\n")
        stringBuilder.append("Great to meet you. What would you like to know?\n\n")
        editTextOutput.setText(stringBuilder.toString())
    }

    fun sendButton(view: View) {
        val userInput = editTextInput.text.toString().trim()
        if (userInput.isEmpty()) return // منع الإرسال الفارغ

        stringBuilder.append("You: $userInput\n\n")
        editTextOutput.setText(stringBuilder.toString())

        MainScope().launch {
            val result = chat.sendMessage(userInput)
            stringBuilder.append("Bot: ${result.text}\n\n")
            editTextOutput.setText(stringBuilder.toString())

            editTextInput.setText("") // مسح حقل الإدخال بعد الإرسال
        }
    }
}
