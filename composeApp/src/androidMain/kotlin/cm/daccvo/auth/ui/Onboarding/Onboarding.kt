package cm.daccvo.auth.ui.Onboarding

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cm.daccvo.auth.security.UserSettingsDataStore
import cm.daccvo.auth.ui.login.LoginEmail
import cm.daccvo.auth.ui.login.LoginPhone

data class Onboarding(val userSettings: UserSettingsDataStore) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        OnboardingScreen(
            onSkip = {
                userSettings.markOnboardingSeen(true)
                navigator.replaceAll(LoginEmail)
            },
            onFinish = {
                userSettings.markOnboardingSeen(true)
                navigator.replaceAll(LoginEmail)
            }
        )
    }

}