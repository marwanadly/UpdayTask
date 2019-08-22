package com.upday.updaytask.di.modules

import com.upday.updaytask.api.AuthApi
import com.upday.updaytask.api.ImagesApi
import com.upday.updaytask.data.DataManager
import com.upday.updaytask.data.preferences.Preferences
import com.upday.updaytask.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    @ApplicationScope
    fun providesDataManager(authApi: AuthApi, imagesApi: ImagesApi, preferences: Preferences): DataManager {
        return DataManager(authApi, imagesApi, preferences)
    }
}