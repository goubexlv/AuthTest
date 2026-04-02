package cm.daccvo.auth

import android.R.attr.statusBarColor
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cm.daccvo.auth.security.AuthState
import cm.daccvo.auth.security.UserSettingsDataStore
import cm.daccvo.auth.ui.Splash.Splash
import cm.daccvo.auth.ui.dashboard.Dashboard
import cm.daccvo.auth.ui.login.LoginEmail
import cm.daccvo.auth.ui.register.Register
import cm.daccvo.auth.viewModels.AccountViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun AuthApp(onLoginSuccess: () -> Unit){

    val viewModel: AccountViewModel = koinViewModel()
    LaunchedEffect(Unit) {
        viewModel.resetState()
    }
    val state = viewModel.uiState
    LaunchedEffect(state.isLoginSucceed) {

        if (state.isLoginSucceed) {
            onLoginSuccess()
        }
    }

    Navigator(LoginEmail)


}