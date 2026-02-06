package cm.daccvo.auth

import android.R.attr.statusBarColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cm.daccvo.auth.security.AuthState
import cm.daccvo.auth.security.UserSettingsDataStore
import cm.daccvo.auth.ui.Splash.Splash
import cm.daccvo.auth.ui.dashboard.Dashboard
import cm.daccvo.auth.ui.register.Register



@Composable
fun AuthApp(userSettings: UserSettingsDataStore){

    //var startScreen by remember { mutableStateOf<Screen>(Register) }
    val authState by userSettings.authState.collectAsState()

    val startScreen = remember(authState) {
        when (authState) {
            AuthState.Checking -> Splash(userSettings)
            AuthState.Unauthenticated -> Register
            is AuthState.Authenticated -> Dashboard
        }
    }

    Navigator(screen = startScreen)

}