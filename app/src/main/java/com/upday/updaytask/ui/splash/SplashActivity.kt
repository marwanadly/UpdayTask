package com.upday.updaytask.ui.splash

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.upday.updaytask.R
import com.upday.updaytask.UpdayTaskApplication
import com.upday.updaytask.di.components.ApplicationComponent
import com.upday.updaytask.di.components.DaggerMainComponent
import com.upday.updaytask.ui.main.MainActivity
import com.upday.updaytask.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    private fun getApplicationComponent(): ApplicationComponent = (application as UpdayTaskApplication).component

    @Inject
    lateinit var splashViewModel: SplashViewModel

    lateinit var tokenStatusObserver: Observer<Int>

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_splash)

        DaggerMainComponent.builder().applicationComponent(getApplicationComponent())
            .build().inject(this)

        splashViewModel.setRxSchedulers(Schedulers.io(),AndroidSchedulers.mainThread())
        splashViewModel.getToken(Constants.ApiConstants.CLIENT_ID,
            Constants.ApiConstants.CLIENT_SECRET,
            "0",
            Constants.ApiConstants.GRANT_TYPE,
            Constants.ApiConstants.TOKEN_REALM
        )

        tokenStatusObserver = Observer {
            when (it) {
                Constants.ApiConstants.GET_TOKEN_SUCCESS -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    this.finish()
                }

                Constants.ApiConstants.GET_TOKEN_FAILED -> {
                    Toast.makeText(this, "Error has occured", Toast.LENGTH_LONG).show()
                }
            }
        }

        splashViewModel.getTokenStatus.observeForever(tokenStatusObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        splashViewModel.getTokenStatus.removeObserver(tokenStatusObserver)
    }

}
