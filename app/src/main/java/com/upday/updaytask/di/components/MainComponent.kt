package com.upday.updaytask.di.components

import com.upday.updaytask.di.scope.ActivityScope
import com.upday.updaytask.ui.main.ImagesAdapter
import com.upday.updaytask.ui.main.MainActivity
import com.upday.updaytask.ui.splash.SplashActivity
import dagger.Component

@Component(dependencies = [ApplicationComponent::class])
@ActivityScope
interface MainComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(imagesAdapter: ImagesAdapter)
    fun inject(splashActivity: SplashActivity)

}