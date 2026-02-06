package cm.horion.models.response

import kotlinx.serialization.Serializable

@Serializable
data class UserStatusResponse(
    val exists: Boolean,
    val isEmailVerified: Boolean,
    val isPhoneVerified: Boolean,
    val requiresVerification: Boolean,
    val identifier: String? = null
)
