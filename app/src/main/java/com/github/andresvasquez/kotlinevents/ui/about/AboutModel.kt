package com.github.andresvasquez.kotlinevents.ui.about

enum class AboutType {
    GITHUB,
    LINKEDIN,
    WHATSAPP
}

data class AboutModel(
    val type: AboutType,
    val intentValue: String
)