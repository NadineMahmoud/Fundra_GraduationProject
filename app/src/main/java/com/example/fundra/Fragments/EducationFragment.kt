package com.example.fundra.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fundra.databinding.FragmentEducationBinding

class EducationFragment : Fragment() {
    lateinit var viewBinding: FragmentEducationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentEducationBinding.inflate(inflater,
            container,
            false)
        return viewBinding.root

    }
}