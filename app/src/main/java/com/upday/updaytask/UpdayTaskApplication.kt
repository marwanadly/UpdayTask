package com.upday.updaytask

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import com.upday.updaytask.di.components.ApplicationComponent
import com.upday.updaytask.di.components.DaggerApplicationComponent
import com.upday.updaytask.di.modules.ApplicationModule
import timber.log.Timber

class UpdayTaskApplication : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) { Timber.plant(Timber.DebugTree()) }

        if (LeakCanary.isInAnalyzerProcess(this)) { return }

        LeakCanary.install(this)

        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
        component.inject(this)
    }
}