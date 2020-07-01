package com.hendi.test_tiket.di

import com.hendi.test_tiket.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, ViewModule::class, GithubModule::class])
interface AppComponent {
    fun inject(activity: MainActivity);
}