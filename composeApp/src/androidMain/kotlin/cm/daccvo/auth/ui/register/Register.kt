package cm.daccvo.auth.ui.register

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cm.daccvo.auth.ui.login.LoginEmail
import cm.daccvo.auth.viewModels.AccountViewModel
import org.koin.compose.viewmodel.koinViewModel

object Register : Screen {

    @Composable
    override fun Content() {
        val viewModel : AccountViewModel = koinViewModel()
        val navigator = LocalNavigator.currentOrThrow
        CreateAccountScreen(
            uiState = viewModel.uiState,
            onSignUpClick = viewModel::registe,
            onEmailChange = viewModel::updateEmail,
            onPasswordChange = viewModel::updatePassword,
            onConfirmPasswordChange = viewModel::updateConfirmPassword,
            onLoginClick= {
                navigator.push(LoginEmail)
            }
        )
    }
}