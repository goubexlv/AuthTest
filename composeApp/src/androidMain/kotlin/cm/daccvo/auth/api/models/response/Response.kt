package cm.daccvo.auth.api.models.response

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val success : Boolean,
    val message : String
)
