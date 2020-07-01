package com.hendi.test_tiket.data

import com.hendi.test_tiket.data.model.UserResponse

class GithubRepository(private val githubService: GithubService) {
    suspend fun searchUser(q: String, page: Int, perPage: Int): ResponseHelper<UserResponse> {
        return try {
            val userResponse = githubService.searchUser(q, page, perPage)
            ResponseHelper(userResponse, null)
        } catch (err: Exception) {
            ResponseHelper(null, err.message)
        }
    }
}