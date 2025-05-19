package com.example.fundra

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ReportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        val reportImage = findViewById<ImageView>(R.id.report_image)
        val closeButton = findViewById<Button>(R.id.close_button)

        // Set the image (e.g., from intent extra or default)
        val imageRes = intent.getIntExtra("image_res", R.drawable.project_one)
        reportImage.setImageResource(imageRes)

        closeButton.setOnClickListener {
            finish()
        }
    }
}