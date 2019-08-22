package com.upday.updaytask.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.upday.updaytask.R
import com.upday.updaytask.UpdayTaskApplication
import com.upday.updaytask.di.components.ApplicationComponent
import com.upday.updaytask.di.components.DaggerMainComponent
import com.upday.updaytask.util.EndlessScrollListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private fun getApplicationComponent(): ApplicationComponent = (application as UpdayTaskApplication).component

    @Inject
    lateinit var mainViewModel: MainViewModel
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
        DaggerMainComponent.builder().applicationComponent(getApplicationComponent())
            .build().inject(this)
        mainViewModel.setRxSchedulers(Schedulers.io(), AndroidSchedulers.mainThread())
        setupImagesAdapter()
        mainViewModel.getImages(1)
    }

    private fun setupImagesAdapter() {
        val imagesAdapter = ImagesAdapter()
        DaggerMainComponent.builder().applicationComponent(getApplicationComponent())
            .build().inject(imagesAdapter)
        imagesListView.layoutManager = LinearLayoutManager(this)
        imagesListView.adapter = imagesAdapter
        mainViewModel.getImagesLiveData.observeForever {
            imagesAdapter.addAll(it!!)
            page += 1
        }
        imagesListView.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                mainViewModel.getImages(page)
            }

        })
    }
}
