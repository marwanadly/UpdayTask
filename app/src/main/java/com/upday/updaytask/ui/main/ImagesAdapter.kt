package com.upday.updaytask.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.upday.updaytask.R
import com.upday.updaytask.data.remote.images.Image
import kotlinx.android.synthetic.main.item_image.view.*
import javax.inject.Inject

class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    @Inject lateinit var picasso: Picasso
    private val imagesList: ArrayList<Image> = ArrayList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ImageViewHolder {
        return ImageViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_image, p0, false))
    }

    override fun getItemCount(): Int = imagesList.size

    override fun onBindViewHolder(imageViewHolder: ImageViewHolder, position: Int) {

        picasso.load(imagesList[position].assets.preview.imageURL)
            .into(imageViewHolder.imageView)
    }

    fun addAll(list: ArrayList<Image>){
        imagesList.addAll(list)
        notifyDataSetChanged()
    }

    class ImageViewHolder(imageContainer: View) : RecyclerView.ViewHolder(imageContainer) {
        val imageView = imageContainer.imageView!!
    }
}