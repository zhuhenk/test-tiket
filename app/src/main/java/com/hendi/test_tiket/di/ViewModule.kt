package com.hendi.test_tiket.di

import com.hendi.test_tiket.view.adapter.UserAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModule() {
    @Provides
    @Singleton
    fun userAdapter(): UserAdapter =
        UserAdapter()
}