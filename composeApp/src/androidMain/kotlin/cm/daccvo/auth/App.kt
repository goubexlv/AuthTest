package cm.daccvo.auth

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

import cm.daccvo.auth.theme.AppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun App(onLoginSuccess: () -> Unit) = AppTheme {
    val systemUiController = rememberSystemUiController()
    val isDarkTheme = isSystemInDarkTheme()

    // Couleur de la status bar selon le thème
    val statusBarColor = if (isDarkTheme) {
        Color.Black // ou MaterialTheme.colorScheme.surface pour thème sombre
    } else {
        Color.Black // ou MaterialTheme.colorScheme.surface pour thème clair
    }

    // Adapter les couleurs selon le thème
//    SideEffect {
//        systemUiController.setStatusBarColor(
//            color = statusBarColor,
//            darkIcons = !isDarkTheme
//        )
//    }


        AuthApp(onLoginSuccess)

}

