package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
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
            setSelectedTab(binding.posts)
        }
        binding.following.setOnClickListener {
            showFragment(FollowingActivity())
            setSelectedTab(binding.following)
        }
        binding.reels.setOnClickListener {
            showFragment(ReelsActivity())
            setSelectedTab(binding.reels)
        }

        binding.profile.setOnClickListener{
            val intent = Intent(this, Account_Activity::class.java)
            startActivity(intent)
        }
    }
    private fun setSelectedTab(selected: TextView) {
        val allTabs = listOf(binding.posts, binding.following, binding.reels)
        allTabs.forEach {
            it.setBackgroundResource(R.drawable.community_text) // default background
        }
        selected.setBackgroundResource(R.drawable.selected_text) // selected background
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