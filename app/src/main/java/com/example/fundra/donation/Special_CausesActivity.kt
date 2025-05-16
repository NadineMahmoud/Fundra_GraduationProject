package com.example.fundra.donation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.FirstSpecialCaseActivity
import com.example.fundra.databinding.ActivitySpecialCausesBinding

class Special_CausesActivity : AppCompatActivity() {
    private lateinit var biding : ActivitySpecialCausesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        biding = ActivitySpecialCausesBinding.inflate(layoutInflater)
        setContentView(biding.root)

        biding.backButton.setOnClickListener{
            val intent : Intent = Intent(this, BaheyaActivity::class.java)
            startActivity(intent)
        }
        biding.imageCaseOne.setOnClickListener{
            val intent : Intent = Intent(this, FirstSpecialCaseActivity::class.java)
            startActivity(intent)
        }




    }
}