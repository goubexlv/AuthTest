package cm.daccvo.auth.ui.dashboard

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cm.daccvo.auth.ui.login.LoginEmail
import cm.daccvo.auth.viewModels.DashboardViewModel
import org.koin.compose.viewmodel.koinViewModel

object Profile : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel : DashboardViewModel = koinViewModel()
        UserProfileScreen(
            user = viewModel.userInfoUiState,
            onLogoutClick = {
                navigator.push(LoginEmail)
                viewModel.logout()
            },
            onBackClick= {
                navigator.push(Dashboard)
            }
        )
    }
}