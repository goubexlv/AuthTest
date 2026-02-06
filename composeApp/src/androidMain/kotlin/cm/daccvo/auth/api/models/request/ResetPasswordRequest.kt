package cm.horion.models.request

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
    val email: String,
    val code: String,
    val newPassword: String
) {
    init {
        require(email.isNotBlank()) { "Email cannot be blank" }
        require(code.length == 6) { "Verification code must be 6 digits" }
        require(newPassword.length >= 8) { "Password must be at least 8 characters" }
    }
}