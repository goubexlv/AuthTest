package cm.daccvo.auth.uiState

data class LoginUiState(
    var email: String = "",
    var phone: String = "",
    var password : String = "",
    var isAuthenticating : Boolean = false,
    var authErrorMessage : String? = null,
    var authenticationSucceed : Boolean = false
)
