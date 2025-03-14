package com.example.fundra.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fundra.databinding.FragmentDonationBinding

class DonationFragment : Fragment() {
    lateinit var viewBinding: FragmentDonationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentDonationBinding.inflate(inflater,
            container,
            false)
        return viewBinding.root

    }
}