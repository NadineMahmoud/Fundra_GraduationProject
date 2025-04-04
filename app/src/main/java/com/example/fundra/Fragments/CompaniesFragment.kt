package com.example.fundra.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fundra.FirstCompanyActivity
import com.example.fundra.databinding.FragmentCompaniesBinding

class CompaniesFragment : Fragment() {
    private lateinit var viewBinding: FragmentCompaniesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentCompaniesBinding.inflate(inflater, container, false)

        viewBinding.Firstcompany.setOnClickListener {
            val intent = Intent(requireContext(), FirstCompanyActivity::class.java)
            startActivity(intent)
        }

        return viewBinding.root
    }
}
