package com.hendi.test_tiket.data

import com.hendi.test_tiket.data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("/search/users")
    suspend fun searchUser(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UserResponse
}