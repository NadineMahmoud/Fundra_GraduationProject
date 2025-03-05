package com.example.fundra
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.R
import com.example.fundra.Sign_up

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Delay moving to Sign_up activity for 2 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, Sign_up::class.java))
            finish()
        }, 2000)
    }
}
