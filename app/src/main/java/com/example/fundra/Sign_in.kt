package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fundra.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class Sign_in : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signInText.setOnClickListener {
            val intent = Intent(this, Sign_up::class.java)
            startActivity(intent)
            finish()
        }
        binding.forgetPassword.setOnClickListener{
            val intent = Intent(this, ForgetPass::class.java)
            startActivity(intent)
            finish()
        }

        binding.SignInBtn.setOnClickListener {
            val email = binding.emailET.text.toString().trim()
            val password = binding.passwordET.text.toString().trim()
          //  val remember = binding.remember.isChecked

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(
                    email,
                    password
                ) // ✅ استخدمي تسجيل الدخول بدل إنشاء حساب جديد
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, Home::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                "Error: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e("FirebaseAuth", "Exception: ${task.exception?.message}")
                        }
                    }
            } else {
                Toast.makeText(this, "Empty Fields Are Not Allowed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}