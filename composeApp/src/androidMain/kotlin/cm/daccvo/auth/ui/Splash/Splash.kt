package cm.daccvo.auth.ui.Splash

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cm.daccvo.auth.security.UserSettingsDataStore

data class Splash(val userSettings: UserSettingsDataStore) : Screen {
    @Composable
    override fun Content() {
        SplashScreen(
            userSettings = userSettings
        )
    }
}