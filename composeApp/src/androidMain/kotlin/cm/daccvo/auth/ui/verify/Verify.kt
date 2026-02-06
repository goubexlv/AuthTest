package cm.daccvo.auth.ui.verify

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cm.daccvo.auth.viewModels.AccountViewModel
import cm.horion.models.domain.LoginMethod
import org.koin.compose.viewmodel.koinViewModel

data class Verify(val model : LoginMethod) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel : AccountViewModel = koinViewModel()
        VerificationCodeScreen(
            uiState = viewModel.uiState,
            onCodeChange = viewModel::updateCode,
            onVerification = {
                viewModel.confirm(model)
            }
        )
    }
}