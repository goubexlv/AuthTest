package cm.horion.models.response

import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val uuid: String,
    val email: String,
    val phone: String?,
    val isEmailVerified: Boolean,
    val isPhoneVerified: Boolean,
    //val sessionInfo: UserAuthSessionData?,
    val warning: String?
)

