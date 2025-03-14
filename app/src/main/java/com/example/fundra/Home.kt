package com.example.fundra

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fundra.Fragments.CompaniesFragment
import com.example.fundra.Fragments.DonationFragment
import com.example.fundra.Fragments.EducationFragment
import com.example.fundra.Fragments.HealthFragments
import com.example.fundra.Fragments.ProjectsFragment
import com.example.fundra.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showFragment(CompaniesFragment())

        binding.companies.setOnClickListener {
            showFragment(CompaniesFragment())
        }
        binding.projects.setOnClickListener {
            showFragment(ProjectsFragment())
        }

        binding.health.setOnClickListener {
            showFragment(HealthFragments())
        }

        binding.edu.setOnClickListener {
            showFragment(EducationFragment())
        }
        binding.donation.setOnClickListener {
            showFragment(DonationFragment())
        }
        binding.menuNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, Home::class.java))
                    true
                }
                R.id.navigation_chatbot -> {
                    startActivity(Intent(this, ChatBotActivity::class.java))
                    true
                }
                R.id.navigation_balance -> {
                    startActivity(Intent(this, Wallet_Activity::class.java))
                    true
                }
                R.id.navigation_account -> {
                    startActivity(Intent(this, Account_Activity::class.java))
                    true
                }
                else -> false
            }
        }

    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}
