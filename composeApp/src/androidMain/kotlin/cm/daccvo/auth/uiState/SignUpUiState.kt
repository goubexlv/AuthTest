package cm.daccvo.auth.uiState

data class AccountUiState(
    var phone: String = "",
    var email: String = "",
    var password : String = "",
    var confirmPassword : String = "",
    var code : String = "",
    var isAuthenticating : Boolean = false,
    var authErrorMessage : String? = null,
    var authenticationSucceed : Boolean = false,
    var isLoading: Boolean = true
)
