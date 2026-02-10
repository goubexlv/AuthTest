package cm.daccvo.auth.uiState

import android.util.Log
import cm.horion.models.domain.LoginMethod

data class AccountUiState(
    var phone: String = "",
    var email: String = "",
    var password : String = "",
    var confirmPassword : String = "",
    var code : String = "",
    var model : LoginMethod = LoginMethod.EMAIL,
    var isAuthenticating : Boolean = false,
    var authErrorMessage : String? = null,
    var authenticationSucceed : Boolean = false,
    var isLoading: Boolean = true
)
