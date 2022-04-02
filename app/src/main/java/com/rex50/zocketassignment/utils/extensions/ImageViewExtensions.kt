package com.rex50.zocketassignment.utils.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun ImageView.loadImage(
    url: String?,
    diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.AUTOMATIC,
    animate: Boolean = false,
    configure: (RequestBuilder<Drawable>.() -> Unit)? = null
) {
    val request = Glide.with(context)
        .load(url)
        .diskCacheStrategy(diskCacheStrategy)

    if(animate) {
        request.transition(DrawableTransitionOptions.withCrossFade())
    }

    if (configure != null) {
        request.configure()
    }
    request.into(this)
}