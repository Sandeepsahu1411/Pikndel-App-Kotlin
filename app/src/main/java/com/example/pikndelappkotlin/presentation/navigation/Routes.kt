package com.example.pikndelappkotlin.presentation.navigation

import kotlinx.serialization.Serializable

sealed class SubRoutes() {
    @Serializable
    object AuthSubRoute : SubRoutes()

    @Serializable
    object MainSubRoute : SubRoutes()
}

sealed class Routes() {

    @Serializable
    object SplashScreenRoute : Routes()

    @Serializable
    object LoginScreenRoute : Routes()

    @Serializable
    data class OtpScreenRoute(
        val phoneNumber: String
    ) : Routes()

    @Serializable
    object HomeScreenRoute : Routes()

    @Serializable
    object MoreScreenRoute : Routes()

    @Serializable
    object ReportScreenRoute : Routes()

    @Serializable
    object ProfileScreenRoute : Routes()

    @Serializable
    object NotificationScreenRoute : Routes()


}