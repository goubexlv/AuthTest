package cm.daccvo.auth.ui.verify

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cm.daccvo.auth.ui.dashboard.Dashboard
import cm.daccvo.auth.viewModels.AccountViewModel
import cm.horion.models.domain.LoginMethod
import org.koin.compose.viewmodel.koinViewModel

data class Verify(
    val email : String,
    val phone : String,
    val method : LoginMethod
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel : AccountViewModel = koinViewModel()
        viewModel.updateModel(method)
        viewModel.updatePhone(phone)
        viewModel.updateEmail(email)
        //viewModel.clearErrorMessage()
        VerificationCodeScreen(
            uiState = viewModel.uiState,
            onCodeChange = viewModel::updateCode,
            onVerification = {
                viewModel.confirm()
            },
            onNavigaterToResult = {
                navigator.push(Confirm)
            },
            onResendCode = {
                viewModel.verify()
            }
        )
    }
}