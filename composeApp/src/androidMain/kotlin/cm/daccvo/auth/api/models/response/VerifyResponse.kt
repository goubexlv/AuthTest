package cm.horion.models.response


import kotlinx.serialization.Serializable

@Serializable
sealed class VerifyResponse {
    @Serializable
    data class Success(
        //val type: NotificationType? = null,
        val item: String? = null,
        val code: String? = null
    ) : VerifyResponse() {
        override fun toString() = "Code envoye avec succès"
    }

    @Serializable
    data object InvalidCode : VerifyResponse() {
        override fun toString() = "Code invalide"
    }

    @Serializable
    data object ExpiredCode : VerifyResponse() {
        override fun toString() = "Code expiré"
    }

    @Serializable
    data class Error(val message: String) : VerifyResponse()
}