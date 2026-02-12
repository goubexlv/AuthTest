package cm.daccvo.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource

import authtest.composeapp.generated.resources.Res
import authtest.composeapp.generated.resources.compose_multiplatform
import cm.daccvo.auth.security.UserSettingsDataStore
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun App(userSettings: UserSettingsDataStore) {
    val systemUiController = rememberSystemUiController()
    val isDarkTheme = isSystemInDarkTheme()

    // Couleur de la status bar selon le thème
    val statusBarColor = if (isDarkTheme) {
        Color.Black // ou MaterialTheme.colorScheme.surface pour thème sombre
    } else {
        Color.Black // ou MaterialTheme.colorScheme.surface pour thème clair
    }

    // Adapter les couleurs selon le thème
    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = !isDarkTheme
        )
    }

    Surface(
        modifier = Modifier
            .statusBarsPadding()
            .systemBarsPadding()
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        AuthApp(userSettings)

    }
}

val LocalStatusBarColor = compositionLocalOf<Color> { Color.Unspecified }