package com.github.andresvasquez.kotlinevents.ui.detail

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.github.andresvasquez.event_repository.model.EventDetailDomain
import com.github.andresvasquez.kotlinevents.R
import com.github.andresvasquez.kotlinevents.utils.Constants
import com.github.andresvasquez.kotlinevents.utils.dateToString
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import timber.log.Timber
import kotlin.math.roundToInt

@BindingAdapter("buildCoverImage")
fun buildCoverImage(imageView: ImageView, event: EventDetailDomain?) {
    event?.let {
        Glide.with(imageView)
            .load(event.coverImage)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    imageView.contentDescription =
                        imageView.context.getString(R.string.event_description_error)
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?, model: Any?, target: Target<Drawable>?,
                    dataSource: DataSource?, isFirstResource: Boolean
                ): Boolean {
                    imageView.contentDescription =
                        imageView.context.getString(
                            R.string.event_description,
                            event.name
                        )
                    return false
                }

            })
            .into(imageView)
    }
}

@BindingAdapter("buildEventProperties")
fun buildEventProperties(layout: LinearLayout, event: EventDetailDomain?) {
    event?.let {
        //Date
        layout.addView(
            createSubTitle(
                layout.context,
                layout.context.getString(R.string.detail_date)
            )
        )
        layout.addView(createContent(layout.context, dateToString(it.startDate)))

        //Prices
        layout.addView(
            createSubTitle(
                layout.context,
                layout.context.getString(R.string.detail_price)
            )
        )
        if (it.priceMin != null && it.priceMax != null) {
            val value =
                it.priceMin!!.roundToInt().toString() + " - " + it.priceMax!!.roundToInt()
                    .toString() + " " + it.currency ?: ""
            layout.addView(createContent(layout.context, value))
        } else {
            layout.addView(
                createContent(
                    layout.context,
                    layout.context.getString(R.string.unknown_price)
                )
            )
        }

        //Info
        layout.addView(
            createSubTitle(
                layout.context,
                layout.context.getString(R.string.detail_info)
            )
        )
        layout.addView(createContent(layout.context, it.info))

        //Covid
        layout.addView(
            createSubTitle(
                layout.context,
                layout.context.getString(R.string.detail_covid_info)
            )
        )
        layout.addView(createContent(layout.context, it.covidInfo))

        //Adult
        layout.addView(
            createSubTitle(
                layout.context,
                layout.context.getString(R.string.detail_adult_only)
            )
        )
        layout.addView(
            createContent(
                layout.context,
                if (it.adult) layout.context.getString(R.string.yes) else layout.context.getString(
                    R.string.no
                )
            )
        )
    }
}

@BindingAdapter("buildLocationProperties")
fun buildLocationProperties(layout: LinearLayout, event: EventDetailDomain?) {
    event?.let {
        //Location Name
        layout.addView(
            createSubTitle(
                layout.context,
                layout.context.getString(R.string.detail_location_name)
            )
        )
        layout.addView(createContent(layout.context, it.locationName))

        //Address
        layout.addView(
            createSubTitle(
                layout.context,
                layout.context.getString(R.string.detail_location_address)
            )
        )
        layout.addView(createContent(layout.context, it.address))

        //City
        layout.addView(
            createSubTitle(
                layout.context,
                layout.context.getString(R.string.detail_city)
            )
        )
        layout.addView(createContent(layout.context, it.city + " - " + it.postalCode))
    }
}

fun createSubTitle(context: Context, text: String?): TextView {
    val tv = TextView(context)
    val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    params.setMargins(0, 20, 0, 8)
    tv.layoutParams = params

    tv.setText(text ?: "")
    tv.setTextSize(14f)
    tv.setTextColor(context.resources.getColor(R.color.colorPrimary))

    return tv
}

fun createContent(context: Context, text: String?): TextView {
    val tv = TextView(context)
    tv.layoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    tv.setText(text ?: "")
    tv.setTextSize(16f)
    return tv
}


@BindingAdapter("viewLocationVisibility")
fun viewLocationVisibility(view: View, event: EventDetailDomain?) {
    event?.let {
        if (event.longitude != null && event.latitude != null) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}

@BindingAdapter("buildLocationImage")
fun buildLocationImage(imageView: ImageView, event: EventDetailDomain?) {
    event?.let {
        if (it.longitude != null && it.latitude != null) {
            var url = Constants.BASE_GMAP_URL + it.latitude + "," + it.longitude
            url += "&markers=" + it.latitude + "," + it.longitude
            url += "&zoom=17&size=640x480&scale=2&maptype=roadmap&format=png"
            url += "&key=" + Constants.BASE_GMAP_API_KEY
            Glide.with(imageView)
                .load(url)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Timber.e(e)
                        imageView.contentDescription =
                            imageView.context.getString(R.string.event_description_error)
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?, model: Any?, target: Target<Drawable>?,
                        dataSource: DataSource?, isFirstResource: Boolean
                    ): Boolean {
                        imageView.contentDescription =
                            imageView.context.getString(
                                R.string.event_location_description,
                                it.name
                            )
                        return false
                    }

                })
                .into(imageView)
        }
    }
}

@BindingAdapter("buildChipsDetails")
fun buildChipsDetails(view: ChipGroup, event: EventDetailDomain?) {
    event?.let {
        val inflator = LayoutInflater.from(view.context)
        val children = it.classifications.map { classification ->
            val chip = inflator.inflate(R.layout.chip, view, false) as Chip
            chip.text = classification
            chip.tag = classification
            chip
        }

        view.removeAllViews()
        for (chip in children) {
            view.addView(chip)
        }
    }
}