package com.hendi.test_tiket.di

import com.hendi.test_tiket.data.GithubRepository
import com.hendi.test_tiket.data.GithubService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class GithubModule() {
    @Provides
    @Singleton
    fun githubService(retrofit: Retrofit): GithubService =
        retrofit.create(GithubService::class.java)

    @Provides
    @Singleton
    fun githubRepository(githubService: GithubService): GithubRepository =
        GithubRepository(githubService)
}