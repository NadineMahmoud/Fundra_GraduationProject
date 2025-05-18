package com.example.fundra.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fundra.FirstProjectActivity
import com.example.fundra.databinding.FragmentProjectsBinding

class ProjectsFragment :Fragment() {
    lateinit var viewBinding: FragmentProjectsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentProjectsBinding.inflate(
            inflater,
            container,
            false
        )
        viewBinding.FirstLarge.setOnClickListener {
            val intent = Intent(requireContext(), FirstProjectActivity::class.java)
            startActivity(intent)
        }
        return viewBinding.root



    }
}