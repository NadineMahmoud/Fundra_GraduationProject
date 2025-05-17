package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.fundra.databinding.ActivityCommunityBinding
import com.example.fundra.menu.Account_Activity

class CommunityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profile.setOnClickListener {
            val intent = Intent(this, Account_Activity::class.java)
            startActivity(intent)
        }
        binding.posts.setOnClickListener {
            showFragment(PostsActivity())
        }
        binding.following.setOnClickListener {
            showFragment(FollowingActivity())
        }
        binding.reels.setOnClickListener {
            showFragment(ReelsActivity())
        }

    }
        private fun showFragment(fragment: Fragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.fragment_enter,
                0
            )
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
    }
}