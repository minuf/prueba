package com.marvel.marvelApp.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.marvelapp.R

fun ImageView.loadUrl(url: String) {
    Glide.with(this.context)
        .load(url)
        .apply(RequestOptions().override(600, 400))
        .placeholder(R.drawable.ic_launcher_background)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}