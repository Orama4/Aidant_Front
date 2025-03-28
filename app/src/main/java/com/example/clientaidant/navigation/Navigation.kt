package com.example.clientaidant.navigation


sealed class Destination(val route: String) {
    object Login : Destination("login")
    object Registration : Destination("registration")
    object ForgotPassword : Destination("ForgotPassword")
    object Verification : Destination("verification")
    object OnBoarding : Destination("onboarding")

}