package cm.horion.models.request

import kotlinx.serialization.Serializable

@Serializable
open class VerifyRequest

@Serializable
data class VerifyEmail(
    val email: String? = null
): VerifyRequest()

@Serializable
data class VerifyPhone(
    val phone: String? = null
): VerifyRequest()