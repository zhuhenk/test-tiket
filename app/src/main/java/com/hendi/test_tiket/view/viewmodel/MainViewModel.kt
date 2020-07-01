package com.hendi.test_tiket.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hendi.test_tiket.data.GithubRepository
import com.hendi.test_tiket.data.model.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val githubRepository: GithubRepository) : ViewModel() {

    private var perPage = 20
    private val loadingMessage = "Loading..."

    var keyword = ""
    var page = 1
    var isLoading = false

    var errorMessage = MutableLiveData<String>()
    var userResponse = MutableLiveData<UserResponse>()
    var errorLoadMore = MutableLiveData<String>()

    fun search(q: String) {
        if(q.isNullOrEmpty()){
            errorMessage.postValue("Please input keyword")
            return
        }

        page = 1
        keyword = ""
        isLoading = true

        errorMessage.postValue(loadingMessage)

        viewModelScope.launch(Dispatchers.IO) {
            val response = githubRepository.searchUser(q, page, perPage)
            isLoading = false
            response.successData?.let {
                if (it.items.size == 0) {
                    errorMessage.postValue("User not found")
                    userResponse.postValue(it)
                } else {
                    keyword = q
                    errorMessage.postValue("")
                    userResponse.postValue(it)
                }
            } ?: run {
                errorMessage.postValue(response.errorMessage)
            }
        }
    }

    fun loadMore() {
        isLoading = true
        page++
        viewModelScope.launch(Dispatchers.IO) {
            val response = githubRepository.searchUser(keyword, page, perPage)

            isLoading = false
            response.successData?.let {
                userResponse.postValue(it)
            } ?: run {
                page--
                errorLoadMore.postValue(response.errorMessage)
            }
        }
    }
}