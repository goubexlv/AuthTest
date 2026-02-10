package cm.daccvo.auth.ui.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cm.daccvo.auth.ui.login.LoginEmail
import cm.daccvo.auth.ui.verify.Verify
import cm.daccvo.auth.viewModels.AccountViewModel
import cm.horion.models.domain.LoginMethod
import org.koin.compose.viewmodel.koinViewModel

object Register : Screen {

    @Composable
    override fun Content() {
        val viewModel : AccountViewModel = koinViewModel()
        LaunchedEffect(Unit) {
            viewModel.resetState()
        }
        val navigator = LocalNavigator.currentOrThrow
        CreateAccountScreen(
            uiState = viewModel.uiState,
            onSignUpClick = viewModel::registe,
            onEmailChange = viewModel::updateEmail,
            onPasswordChange = viewModel::updatePassword,
            onConfirmPasswordChange = viewModel::updateConfirmPassword,
            onLoginClick= {
                navigator.push(LoginEmail)
            },
            onNavigateToVerifieCode = {
                viewModel.verify()
                navigator.push(Verify(viewModel.uiState.email,viewModel.uiState.phone,viewModel.uiState.model))
            },
            onModelChange = viewModel::updateModel
        )
    }
}