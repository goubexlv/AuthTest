package cm.daccvo.auth.api.models

sealed class Endpoint( val path: String){
    data object Root: Endpoint(path = "/")
    data object Login: Endpoint(path = "/login")
    data object Register: Endpoint(path = "/register")
    data object Verify: Endpoint(path = "/verify")
    data object Confirm: Endpoint(path = "/confirm")
    data object RefreshToken: Endpoint(path = "/refresh")
    data object ResetPassword: Endpoint(path = "/reset-password")
    data object ForgotPassword: Endpoint(path = "/forgot-password")
    data object Profile: Endpoint(path = "/profile")
    data object Email: Endpoint(path = "/email")
    data object Phone: Endpoint(path = "/phone")
    data object Logout: Endpoint(path = "/logout")
    data object LogoutAll: Endpoint(path = "/logout-all")
    data object Sessions: Endpoint(path = "/sessions")
    data object CheckStatus : Endpoint(path = "/status")
    data object Exchange : Endpoint(path = "/token/exchange")
}