package com.example.primenumbergenerator.ui.viewModel

interface PrimeViewModel {
    suspend fun searchPrime(start: Int, end: Int)
    fun checkPrime(num: Int): Boolean
}