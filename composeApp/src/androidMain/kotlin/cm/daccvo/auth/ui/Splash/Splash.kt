package cm.daccvo.auth.ui.Splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cm.daccvo.auth.security.AuthState
import cm.daccvo.auth.security.UserSettingsDataStore
import cm.daccvo.auth.ui.dashboard.Dashboard
import cm.daccvo.auth.ui.register.Register
import kotlinx.coroutines.delay

data class Splash(val userSettings: UserSettingsDataStore) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val authState by userSettings.authState.collectAsState()
//        LaunchedEffect(Unit) {
//            delay(3000)
//            userSettings.onAppStart()
//        }
//
//        LaunchedEffect(authState) {
//            when (authState) {
//                AuthState.Unauthenticated ->
//                    navigator.replaceAll(Register)
//
//                AuthState.Authenticated ->
//                    navigator.replaceAll(Dashboard)
//
//                AuthState.Checking -> Unit
//            }
//        }

        SplashScreen(
            onSplashFinished = {
                if (authState is AuthState.Authenticated) {
                    navigator.replaceAll(Dashboard)
                } else {
                    navigator.replaceAll(Register)
                }
            }
        )
    }
}