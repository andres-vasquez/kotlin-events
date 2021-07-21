package com.github.andresvasquez.kotlinevents.ui.about

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.colorInt
import com.mikepenz.iconics.utils.sizeDp


@BindingAdapter("applyModel")
fun applyModel(imageView: ImageView, aboutModel: AboutModel) {
    when (aboutModel.type) {
        AboutType.GITHUB -> {
            imageView.setImageDrawable(
                IconicsDrawable(imageView.context, FontAwesome.Icon.faw_github).apply {
                    sizeDp = 24
                }
            )
        }
        AboutType.WHATSAPP -> {
            imageView.setImageDrawable(
                IconicsDrawable(imageView.context, FontAwesome.Icon.faw_whatsapp).apply {
                    colorInt = Color.parseColor("#3DD74C")
                    sizeDp = 24
                }
            )
        }
        AboutType.LINKEDIN -> {
            imageView.setImageDrawable(
                IconicsDrawable(imageView.context, FontAwesome.Icon.faw_linkedin).apply {
                    colorInt = Color.parseColor("#266899")
                    sizeDp = 24
                }
            )
        }
    }
    imageView.setOnClickListener {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(aboutModel.intentValue)
        imageView.context.startActivity(i)
    }
}