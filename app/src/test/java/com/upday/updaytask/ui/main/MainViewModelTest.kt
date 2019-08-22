package com.upday.updaytask.ui.main

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.upday.updaytask.data.DataManager
import com.upday.updaytask.data.remote.auth.Auth
import com.upday.updaytask.data.remote.images.ImagesResponse
import com.upday.updaytask.util.Constants
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException

class MainViewModelTest {

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var dataManager: DataManager

    @Mock
    lateinit var mockImageResponse: ImagesResponse

    @Mock
    lateinit var mockAuthResponse: Auth

    @Mock
    lateinit var observer: Observer<Int>


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel(dataManager)
        mainViewModel.setRxSchedulers(Schedulers.trampoline(), Schedulers.trampoline())
        mainViewModel.imagesLoaded.observeForever(observer)
    }

    @Test
    fun `Get list of images when page number is 1`() {
        whenever(dataManager.getImages(1)).thenReturn(Observable.just(mockImageResponse))
        mainViewModel.getImages(1)
        verify(observer, times(1)).onChanged(Constants.ApiConstants.IMAGES_LOADED_SUCCESSFULLY)
    }

    @Test
    fun `Error when trying to get more than 1000 images`() {
        val errorResponse = Response.error<ImagesResponse>(403, ResponseBody.create(null, ""))
        whenever(dataManager.getImages(1000)).thenReturn(Observable.error(HttpException(errorResponse)))
        mainViewModel.getImages(1000)
        verify(observer, times(1)).onChanged(Constants.ApiConstants.IMAGES_LOAD_FAILURE)
    }

    @Test
    fun `Error when trying to get images due to internet problem`() {
        val errorResponse = SocketTimeoutException()
        whenever(dataManager.getImages(1)).thenReturn(Observable.error(errorResponse))
        mainViewModel.getImages(1)
        verify(observer, times(1)).onChanged(Constants.ApiConstants.IMAGES_LOAD_TIMEOUT)
    }

    @Test
    fun `Retry images fetching when token expired`(){
        val errorResponse = Response.error<ImagesResponse>(401, ResponseBody.create(null, ""))
        whenever(dataManager.getImages(1)).thenReturn(Observable.error(HttpException(errorResponse)))
        mainViewModel.getImages(1)
        verify(observer, times(1)).onChanged(Constants.ApiConstants.IMAGES_LOAD_RETRY)
        whenever(dataManager.getToken(Constants.ApiConstants.CLIENT_ID,
            Constants.ApiConstants.CLIENT_SECRET,
            "0",
            Constants.ApiConstants.GRANT_TYPE,
            Constants.ApiConstants.TOKEN_REALM)).thenReturn(Observable.just(mockAuthResponse))
        whenever(dataManager.getImages(1)).thenReturn(Observable.just(mockImageResponse))
        mainViewModel.getImages(1)
        verify(observer,times(1)).onChanged(Constants.ApiConstants.IMAGES_LOADED_SUCCESSFULLY)
    }

}