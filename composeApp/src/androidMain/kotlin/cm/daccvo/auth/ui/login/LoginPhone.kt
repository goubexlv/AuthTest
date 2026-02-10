package cm.daccvo.auth.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cm.daccvo.auth.ui.dashboard.Dashboard
import cm.daccvo.auth.ui.register.Register
import cm.daccvo.auth.viewModels.AccountViewModel
import cm.horion.models.domain.LoginMethod
import org.koin.compose.viewmodel.koinViewModel

object LoginPhone : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel : AccountViewModel = koinViewModel()

        LaunchedEffect(Unit) {
            viewModel.resetState()
        }
        viewModel.updateModel(LoginMethod.PHONE)

        LoginScreenPhone(
            onEmailLoginClick = {
                navigator.push(LoginEmail)
            },
            onRegisterClick={
                navigator.push(Register)
            },
            uiState = viewModel.uiState,
            onPhoneChange = viewModel::updatePhone,
            onPasswordChange = viewModel::updatePassword,
            onLoginClick = {
                viewModel.login()
            },
            onNavigateToDashboard = {
                navigator.replaceAll(Dashboard)
            }
        )
    }
}