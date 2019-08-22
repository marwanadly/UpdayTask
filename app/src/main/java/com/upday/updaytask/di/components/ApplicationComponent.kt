package com.upday.updaytask.di.components

import android.content.Context
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.upday.updaytask.UpdayTaskApplication
import com.upday.updaytask.api.AuthApi
import com.upday.updaytask.api.ImagesApi
import com.upday.updaytask.data.DataManager
import com.upday.updaytask.data.preferences.Preferences
import com.upday.updaytask.di.modules.ApplicationModule
import com.upday.updaytask.di.scope.ApplicationScope
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(application: UpdayTaskApplication)
    fun getAppContext(): Context
    fun getAppPreference(): Preferences
    fun getDataManager(): DataManager
    fun getPicasso(): Picasso
}