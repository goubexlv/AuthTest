package cm.horion.models.request

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class RegisterRequest(
    val email: String? = null,
    val phone: String? = null,
    val password: String = "",
    val code: String = "",
)
