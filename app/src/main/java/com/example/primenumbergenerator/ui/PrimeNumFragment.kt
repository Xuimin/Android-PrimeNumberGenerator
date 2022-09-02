package com.example.primenumbergenerator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.primenumbergenerator.databinding.FragmentPrimeNumBinding
import com.example.primenumbergenerator.ui.viewModel.PrimeViewModelImpl
import kotlinx.coroutines.*

class PrimeNumFragment : Fragment() {
    private lateinit var binding: FragmentPrimeNumBinding
    private val args: PrimeNumFragmentArgs by navArgs()
    private val primeViewModel: PrimeViewModelImpl by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrimeNumBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            NavHostFragment.findNavController(this@PrimeNumFragment).popBackStack()
        }

        // search all prime numbers in a range
        lifecycleScope.launch {
            if (primeViewModel.allPrime.isEmpty()) {
                primeViewModel.searchPrime(args.start, args.end)
            }

            delay(1500L)
            binding.tvResult.visibility = View.VISIBLE
            binding.progress.visibility = View.GONE

            if (primeViewModel.allPrime.isEmpty()) {
                delay(10L)
                binding.tvNoPrime.visibility = View.VISIBLE
                binding.tvPrimeNumber.visibility = View.GONE
            } else {
                val primeResult = primeViewModel.allPrime.sorted()
                binding.tvPrimeNumber.text = "Prime Numbers (${args.start} to ${args.end}):"
                binding.tvResult.text = primeResult.toString().replace("[", "").replace("]", "")
            }
        }

        // check if any prime numbers are found


        // set loading state
        primeViewModel.loading.asLiveData().observe(viewLifecycleOwner) {
            binding.tvResult.visibility = View.VISIBLE
            binding.progress.visibility = View.GONE
        }
    }
}