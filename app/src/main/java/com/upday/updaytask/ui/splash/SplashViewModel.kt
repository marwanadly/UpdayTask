package com.upday.updaytask.ui.splash

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import com.upday.updaytask.data.DataManager
import com.upday.updaytask.util.Constants
import io.reactivex.Scheduler
import timber.log.Timber
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val dataManager: DataManager) {

    private lateinit var ioScheduler: Scheduler
    private lateinit var uiScheduler: Scheduler
    val getTokenStatus = MutableLiveData<Int>()

    fun setRxSchedulers(io: Scheduler, ui: Scheduler) {
        ioScheduler = io
        uiScheduler = ui
    }

    @SuppressLint("CheckResult")
    fun getToken( clientID: String,
                  clientSecret: String,
                  code: String,
                  grantType: String,
                  tokenRealm: String) {
        dataManager.getToken(
            clientID,
            clientSecret,
            code,
            grantType,
            tokenRealm
           )
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe({ response ->
                dataManager.saveTokenPreferences("Bearer "+response.token)
                Timber.i("Bearer ${response.token}")
                getTokenStatus.postValue(Constants.ApiConstants.GET_TOKEN_SUCCESS)
            }, { error ->
                getTokenStatus.postValue(Constants.ApiConstants.GET_TOKEN_FAILED)
                Timber.e("Retrofit Error: $error")
            })
    }
}