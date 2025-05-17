package com.example.fundra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.fundra.databinding.ActivityPostsBinding
import com.example.fundra.databinding.ActivityReelsBinding

class ReelsActivity : Fragment() {
    private lateinit var binding: ActivityReelsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityReelsBinding.inflate(inflater, container, false)
        return binding.root
    }
}
