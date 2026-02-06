package cm.horion.models.request

import kotlinx.serialization.Serializable


@Serializable
sealed interface LoginRequest {
    val password: String
}

@Serializable
data class LoginWithEmail(
    val email: String,
    override val password: String
) : LoginRequest

@Serializable
data class LoginWithPhone(
    val phone: String?,
    override val password: String
) : LoginRequest

@Serializable
data class RefreshTokenRequest(
    val refresh_token: String
)
