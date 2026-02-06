package models.response

import kotlinx.serialization.Serializable

@Serializable
sealed class ConfirmResponse {
    @Serializable
    data object Success : ConfirmResponse() {
        override fun toString() = "Code vérifié avec succès"
    }

    @Serializable
    data object InvalidCode : ConfirmResponse() {
        override fun toString() = "Code invalide"
    }

    @Serializable
    data object ExpiredCode : ConfirmResponse() {
        override fun toString() = "Code expiré"
    }

    @Serializable
    data object MaxAttemptsReached : ConfirmResponse() {
        override fun toString() = "Trop de tentatives, veuillez demander un nouveau code"
    }

    @Serializable
    data class Error(val message: String) : ConfirmResponse()
}