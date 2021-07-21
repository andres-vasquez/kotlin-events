package com.github.andresvasquez.kotlinevents.ui.list

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.github.andresvasquez.event_repository.model.EventListDomain
import com.github.andresvasquez.event_repository.model.NextTripSearch
import com.github.andresvasquez.kotlinevents.R
import com.github.andresvasquez.kotlinevents.utils.dateToString
import com.github.andresvasquez.kotlinevents.utils.parseDateToString
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlin.math.roundToInt

@BindingAdapter("eventThumbnail")
fun bindEventThumbnailImage(imageView: ImageView, event: EventListDomain?) {
    if (event != null) {

        Glide.with(imageView)
            .load(event.thumbnailImage)
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

@BindingAdapter("loadingVisibility")
fun bindLoadingVisibility(view: View, visible: Boolean) {
    if (visible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("dateAndAddress")
fun dateAndAddress(view: TextView, event: EventListDomain) {
    val value = dateToString(event.startDate) + ", " + event.locationName
    view.setText(value)
}


@BindingAdapter("priceReference")
fun priceReference(view: TextView, event: EventListDomain) {
    if (event.priceMin != null && event.priceMax != null) {
        val value = event.priceMin!!.roundToInt().toString() + " - " + event.priceMax!!.roundToInt()
            .toString() + " " + event.currency ?: ""
        view.setText(value)
    } else {
        view.setText(view.context.getString(R.string.unknown_price))
    }
}

@BindingAdapter("buildChips")
fun buildChips(view: ChipGroup, event: EventListDomain) {
    val inflator = LayoutInflater.from(view.context)
    val children = event.classifications.map { classification ->
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


@BindingAdapter("dateDisplayFrom")
fun dateDisplayFrom(view: TextView, value: String?) {
    view.setText(view.context.getString(R.string.from, parseDateToString(value)))
}

@BindingAdapter("dateDisplayTo")
fun dateDisplayTo(view: TextView, value: String?) {
    view.setText(view.context.getString(R.string.to, parseDateToString(value)))
}

@BindingAdapter("cityImageDisplay")
fun cityImageDisplay(view: ImageView, nextTrip: NextTripSearch?) {
    nextTrip?.let {
        when (it.city) {
            // New york
            com.github.andresvasquez.event_repository.utils.Constants.DEFAULT_NEXT_TRIP.city -> {
                view.setImageResource(R.drawable.nyc_header)
            }
            else -> {
                view.setImageResource(R.drawable.ic_baseline_map_24)
            }
        }
    }
}
