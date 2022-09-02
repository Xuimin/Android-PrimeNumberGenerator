package com.example.primenumbergenerator.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.primenumbergenerator.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGenerate.setOnClickListener {
            val start = binding.etStartNum.text.toString()
            val end = binding.etEndNum.text.toString()

            inputConditions(start, end, view)
        }
    }

    // Conditions for the inputs value
    private fun inputConditions(start: String, end: String, view: View) {
        if(TextUtils.isEmpty(start) || TextUtils.isEmpty(end)) { Snackbar.make(requireContext(), view, "No empty inputs", Snackbar.LENGTH_SHORT).show() }
        else if(start.toInt() < 0 || end.toInt() < 0) { Snackbar.make(requireContext(), view, "No negative values", Snackbar.LENGTH_SHORT).show() }
        else if(start.toInt() > end.toInt()) { Snackbar.make(requireContext(), view, "Start value cannot be greater then End value", Snackbar.LENGTH_SHORT).show() }
        else showAllPrime(start.toInt(), end.toInt())
    }

    // Function to pass input arguments and show it in the next screen
    private fun showAllPrime(start: Int, end: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToPrimeNumFragment(start, end)
        NavHostFragment.findNavController(this@HomeFragment).navigate(action)
    }
}