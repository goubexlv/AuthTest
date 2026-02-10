package cm.daccvo.auth.ui.verify

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cm.daccvo.auth.ui.login.LoginEmail

object Confirm : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        VerificationSuccessScreen(
            onContinue = {
                navigator.replaceAll(LoginEmail)
            },
            onClose = {
                navigator.replaceAll(LoginEmail)
            }
        )
    }

}