package models.request

import kotlinx.serialization.Serializable

@Serializable
open class ConfirmRequest

@Serializable
data class ConfirmEmail(
    val email : String? = null,
    val code : String? = null
): ConfirmRequest()

@Serializable
data class ConfirmPhone(
    val phone : String? = null,
    val code : String? = null
): ConfirmRequest()
