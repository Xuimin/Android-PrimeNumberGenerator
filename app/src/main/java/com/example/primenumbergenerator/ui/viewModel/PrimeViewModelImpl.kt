package com.example.primenumbergenerator.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.lang.Math.floor

class PrimeViewModelImpl : PrimeViewModel, ViewModel() {

    val allPrime = mutableListOf<Int>()

    private val _loading: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val loading: SharedFlow<Boolean> = _loading

    // Search prime number within range
    override suspend fun searchPrime(start: Int, end: Int) {
        val allNum = mutableListOf<Int>()
        for (i in start..end) { allNum.add(i) }

        val difference: Int = end - start
        val numOfCoroutine: Int = floor(difference.toDouble() / 1000).toInt()

        // run one coroutine when numbers is less than 1000
        if (difference < 1000) {
            viewModelScope.launch {
                async {
                    for (i in 0 until allNum.size) {
                        if (checkPrime(allNum[i])) {
                            allPrime.add(allNum[i])
                        }
                    }
                }
            }
        } else {
            viewModelScope.launch {
                (1..numOfCoroutine).map {
                    for (i in (it * 1000 - 1000)..(it * 1000)) {
                        async {
                            if (checkPrime(allNum[i])) {
                                allPrime.add(allNum[i])
                            }
                        }
                    }
                }
            }
            viewModelScope.launch {
                async {
                    for (i in numOfCoroutine * 1000 until allNum.size) {
                        if (checkPrime(allNum[i])) {
                            allPrime.add(allNum[i])
                        }
                    }
                }
            }
        }

        viewModelScope.launch {
            _loading.emit(false)
        }
    }

    // Function to check prime number within a range
    override fun checkPrime(num: Int): Boolean {
        var isPrime = false

        for (i in 2..num) {
            if (num % i == 0) {
                if (num == i) isPrime = true
                break
            }
        }
        return isPrime
    }
}