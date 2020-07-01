package com.hendi.test_tiket

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.hendi.test_tiket.data.GithubRepository
import com.hendi.test_tiket.data.ResponseHelper
import com.hendi.test_tiket.data.model.User
import com.hendi.test_tiket.data.model.UserResponse
import com.hendi.test_tiket.view.viewmodel.MainViewModel
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GithubTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: GithubRepository

    @Mock
    private lateinit var usersObserver: Observer<UserResponse>

    @Mock
    private lateinit var errorObserver: Observer<String>

    @Test
    fun startSearchSuccess() = runBlockingTest {
        Mockito.doReturn(
            ResponseHelper(
                UserResponse(
                    1,
                    false,
                    arrayListOf(User(1, "hendi", "http"))
                ), null
            )
        )
            .`when`(repository)
            .searchUser("h", 1, 20)

        val viewModel = MainViewModel(repository)
        viewModel.search("h")
        viewModel.userResponse.observeForever(usersObserver)
        Mockito.verify(repository).searchUser("h", 1, 20)
        Mockito.verify(usersObserver).onChanged(
            UserResponse(
                1,
                false,
                arrayListOf(User(1, "hendi", "http"))
            )
        )
        viewModel.userResponse.removeObserver(usersObserver)
    }

    @Test
    fun startSearchEmpty() = runBlockingTest {
        Mockito.doReturn(
            ResponseHelper(
                UserResponse(
                    1,
                    false,
                    arrayListOf()
                ), null
            )
        )
            .`when`(repository)
            .searchUser("h", 1, 20)

        val viewModel = MainViewModel(repository)
        viewModel.search("h")
        viewModel.errorMessage.observeForever(errorObserver)
        Mockito.verify(repository).searchUser("h", 1, 20)
        Mockito.verify(errorObserver).onChanged(
            "User not found"
        )
        viewModel.errorMessage.removeObserver(errorObserver)
    }

    @Test
    fun startSearchError() = runBlockingTest {
        Mockito.doReturn(ResponseHelper(null, "Something went wrong"))
            .`when`(repository)
            .searchUser("h", 1, 20)

        val viewModel = MainViewModel(repository)
        viewModel.search("h")
        viewModel.errorMessage.observeForever(errorObserver)
        Mockito.verify(repository).searchUser("h", 1, 20)
        Mockito.verify(errorObserver).onChanged(
            "Something went wrong"
        )
        viewModel.errorMessage.removeObserver(errorObserver)
    }

    @Test
    fun loadMoreSuccess() = runBlockingTest {
        Mockito.doReturn(
            ResponseHelper(
                UserResponse(
                    1,
                    false,
                    arrayListOf(User(1, "hendi", "http"))
                ), null
            )
        )
            .`when`(repository)
            .searchUser("h", 2, 20)

        val viewModel = MainViewModel(repository)
        viewModel.keyword = "h"
        viewModel.page = 1
        viewModel.loadMore()

        viewModel.userResponse.observeForever(usersObserver)
        Mockito.verify(repository).searchUser("h", 2, 20)
        Mockito.verify(usersObserver).onChanged(
            UserResponse(
                1,
                false,
                arrayListOf(User(1, "hendi", "http"))
            )
        )
        viewModel.userResponse.removeObserver(usersObserver)
    }
}