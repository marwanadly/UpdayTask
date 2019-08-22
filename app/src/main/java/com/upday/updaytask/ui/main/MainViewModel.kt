package com.upday.updaytask.ui.main

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import com.upday.updaytask.data.DataManager
import com.upday.updaytask.data.remote.images.Image
import com.upday.updaytask.util.Constants
import io.reactivex.Scheduler
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import javax.inject.Inject

class MainViewModel @Inject constructor(private val dataManager: DataManager) {

    private lateinit var ioScheduler: Scheduler
    private lateinit var uiScheduler: Scheduler
    var getImagesLiveData: MutableLiveData<ArrayList<Image>> = MutableLiveData()
    var imagesLoaded: MutableLiveData<Int> = MutableLiveData()


    fun setRxSchedulers(io: Scheduler, ui: Scheduler) {
        ioScheduler = io
        uiScheduler = ui
    }

    @SuppressLint("CheckResult")
    fun getImages(pageNumber: Int) {
        dataManager.getImages(pageNumber).subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe({ list -> getImagesLiveData.postValue(list.data);imagesLoaded.postValue(Constants.ApiConstants.IMAGES_LOADED_SUCCESSFULLY) },
                { err ->
                    if(err is HttpException){
                        val statusCode = err.code()
                        if (statusCode == 401) {
                            imagesLoaded.postValue(Constants.ApiConstants.IMAGES_LOAD_RETRY)
                            getToken()
                            getImages(pageNumber)
                        } else {
                            imagesLoaded.postValue(Constants.ApiConstants.IMAGES_LOAD_FAILURE)
                            Timber.e("Error fetching images: ${err.message()}")
                        }
                    }else if(err is SocketTimeoutException){
                        imagesLoaded.postValue(Constants.ApiConstants.IMAGES_LOAD_TIMEOUT)
                        Timber.e("Status code: ${err.message}")
                    }

                }
            )
    }

    @SuppressLint("CheckResult")
    fun getToken() {
        dataManager.getToken(
            Constants.ApiConstants.CLIENT_ID,
            Constants.ApiConstants.CLIENT_SECRET,
            "0",
            Constants.ApiConstants.GRANT_TYPE,
            Constants.ApiConstants.TOKEN_REALM
        )
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe({ response ->
                dataManager.saveTokenPreferences(response.token)
            }, { error ->
                Timber.e("Retrofit Error: $error")
            })
    }

}