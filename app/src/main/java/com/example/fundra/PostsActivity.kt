package com.example.fundra

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.fundra.databinding.ActivityPostsBinding
import com.example.fundra.databinding.FragmentCompaniesBinding

class PostsActivity : Fragment() {
    private lateinit var binding: ActivityPostsBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
            ): View {
            binding = ActivityPostsBinding.inflate(inflater, container, false)
         return binding.root
        }
    }
