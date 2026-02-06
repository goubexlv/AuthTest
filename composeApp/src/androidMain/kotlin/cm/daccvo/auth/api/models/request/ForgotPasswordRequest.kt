package cm.horion.models.request

import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordRequest(
    val email: String
) {
    init {
        require(email.isNotBlank()) { "L'email ne peut pas être vide" }
        require(email.contains('@') && email.contains('.')) { "Format d'email invalide" }
    }
}