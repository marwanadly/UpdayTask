package com.upday.updaytask.ui.splash

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.upday.updaytask.data.DataManager
import com.upday.updaytask.data.remote.auth.Auth
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


class SplashViewModelTest {

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    private lateinit var splashViewModel: SplashViewModel

    @Mock
    lateinit var dataManager: DataManager

    @Mock
    lateinit var mockTokenResponse: Auth

    @Mock
    lateinit var observer: Observer<Int>


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        splashViewModel = SplashViewModel(dataManager)
        splashViewModel.setRxSchedulers(Schedulers.trampoline(), Schedulers.trampoline())
        splashViewModel.getTokenStatus.observeForever(observer)
    }

    @Test
    fun `Get token successfully`() {
        whenever(
            dataManager.getToken(
                Constants.ApiConstants.CLIENT_ID,
                Constants.ApiConstants.CLIENT_SECRET,
                "0",
                Constants.ApiConstants.GRANT_TYPE,
                Constants.ApiConstants.TOKEN_REALM
            )
        ).thenReturn(Observable.just(mockTokenResponse))
        splashViewModel.getToken(
            Constants.ApiConstants.CLIENT_ID,
            Constants.ApiConstants.CLIENT_SECRET,
            "0",
            Constants.ApiConstants.GRANT_TYPE,
            Constants.ApiConstants.TOKEN_REALM
        )
        verify(observer, times(1)).onChanged(Constants.ApiConstants.GET_TOKEN_SUCCESS)
    }

    @Test
    fun `Error in getting token with wrong params`() {
        val errorResponse = Response.error<Auth>(401, ResponseBody.create(null, ""))
        whenever(
            dataManager.getToken(
                Constants.ApiConstants.CLIENT_ID,
                Constants.ApiConstants.CLIENT_SECRET + "455",
                "0",
                Constants.ApiConstants.GRANT_TYPE,
                Constants.ApiConstants.TOKEN_REALM
            )
        ).thenReturn(Observable.error(HttpException(errorResponse)))
        splashViewModel.getToken(
            Constants.ApiConstants.CLIENT_ID,
            Constants.ApiConstants.CLIENT_SECRET + "455",
            "0",
            Constants.ApiConstants.GRANT_TYPE,
            Constants.ApiConstants.TOKEN_REALM
        )
        verify(observer, times(1)).onChanged(Constants.ApiConstants.GET_TOKEN_FAILED)
    }
}