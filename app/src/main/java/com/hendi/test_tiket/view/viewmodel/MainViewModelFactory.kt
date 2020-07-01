package com.hendi.test_tiket.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hendi.test_tiket.data.GithubRepository
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val githubRepository: GithubRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) return MainViewModel(
            githubRepository
        ) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}