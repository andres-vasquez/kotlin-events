package com.github.andresvasquez.kotlinevents.ui.about

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.github.andresvasquez.kotlinevents.databinding.FragmentAboutBinding

class AboutFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        val binding = FragmentAboutBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.githubModel = AboutModel(AboutType.GITHUB, "https://github.com/andres-vasquez")
        binding.linkedInModel =
            AboutModel(AboutType.LINKEDIN, "https://www.linkedin.com/in/andres-vasquez-161a73110/")
        binding.whatsappModel =
            AboutModel(AboutType.WHATSAPP, "https://api.whatsapp.com/send?phone=+59160507900")
        return binding.root
    }
}