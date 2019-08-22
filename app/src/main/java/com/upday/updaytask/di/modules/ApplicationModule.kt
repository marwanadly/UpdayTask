package com.upday.updaytask.di.modules

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.upday.updaytask.data.preferences.AppPreferences
import com.upday.updaytask.data.preferences.Preferences
import com.upday.updaytask.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import com.squareup.picasso.Picasso
import com.squareup.picasso.OkHttp3Downloader
import com.upday.updaytask.ui.main.ImagesAdapter


@Module(includes = [NetworkModule::class, DataModule::class])
class ApplicationModule(context: Context) {

    private var mContext: Context = context

    @Provides
    @ApplicationScope
    fun provideContext(): Context {
        return mContext
    }

    @Provides
    @ApplicationScope
    fun provideAppPreference(context: Context): Preferences {
        return AppPreferences(context)
    }

    @Provides
    @ApplicationScope
    fun providePicasso(context: Context, okHttp3Downloader: OkHttp3Downloader): Picasso {
        return Picasso.Builder(context)
            .downloader(okHttp3Downloader)
            .loggingEnabled(true)
            .build()
    }
}