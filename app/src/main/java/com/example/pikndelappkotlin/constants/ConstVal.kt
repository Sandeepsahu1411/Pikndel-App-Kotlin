package com.example.pikndelappkotlin.constants

import com.example.pikndelappkotlin.BuildConfig

object ConstVal {
    // Central app constants
    const val API_BASE_PATH: String = "/api/v1/"

    // Accessor for the Maps API key injected via BuildConfig (kept out of VCS)
    val GOOGLE_MAPS_API_KEY: String
        get() = BuildConfig.MAPS_API_KEY
}


