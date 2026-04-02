package cm.daccvo.auth.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import cm.daccvo.auth.security.AppVerifier
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

lateinit var appContext: Context
    private set

fun initSettings(context: Context) {
    appContext = context.applicationContext
}

fun provideSettings(): Settings {
    val sharedPrefs = appContext.getSharedPreferences("user_settings", Context.MODE_PRIVATE)
    return SharedPreferencesSettings(sharedPrefs)
}

val allowedApps = listOf(
    "SHA256_DE_CV_APP",
    "SHA256_DE_AUTRE_APP"
)

//fun startAuthFlow(context: Context) {
//    val authPackageName = "com.votre.package.authapp" // Le package de ton application Auth
//    val myAuthSignature = "VOTRE_SHA256_REEL_ICI"    // La signature SHA-256 de ton APK Auth
//
//    // 1. Vérification de l'identité de l'application cible
//    if (AppVerifier.verifyAppAuthenticity(context, authPackageName, myAuthSignature)) {
//
//        // 2. L'application est authentique, on prépare l'Intent
//        val authUri = Uri.parse("authapp://login")
//            .buildUpon()
//            .appendQueryParameter("service", "cv-app")
//            .appendQueryParameter("callback", "cvapp://auth-callback")
//            .appendQueryParameter("state", generateRandomState()) // Sécurité anti-CSRF
//            .build()
//
//        val intent = Intent(Intent.ACTION_VIEW, authUri).apply {
//            setPackage(authPackageName) // Force l'ouverture uniquement par ton App Auth
//            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        }
//
//        context.startActivity(intent)
//
//    } else {
//        // 3. Alerte ! L'application installée n'est pas la tienne ou a été modifiée
//        Toast.makeText(context, "Erreur de sécurité : Application d'authentification non valide", Toast.LENGTH_LONG).show()
//        // Optionnel : Rediriger vers le Play Store pour installer la vraie application
//    }
//}