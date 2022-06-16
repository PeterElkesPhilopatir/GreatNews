package com.peter.greatnews.framework.presentation

import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.peter.greatnews.R


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    try {
        imgView.clipToOutline = true
        var imgUri = imgUrl!!.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.mipmap.ic_launcher)
            )
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgView)

    } catch (e: Exception) {
        Log.e("BindingAdapter", e.message.toString())
        imgView.setImageResource(R.mipmap.ic_launcher)
    }
}

