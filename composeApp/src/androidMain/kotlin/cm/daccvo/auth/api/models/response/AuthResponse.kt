package cm.horion.models.response

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
sealed class AuthResponse {
    @Serializable
    data class Success(
        val token: Token? = null,
//        val refreshToken: String? = null,
        val code: String? = null,
        val isVerified: Boolean = true,
        val requiresVerification: Boolean = false,
        val message: String? = null
    ): AuthResponse()

    @Serializable
    data class Error(
        val message: String
    ): AuthResponse()
}

@Serializable
data class  Token(
    val accessToken: String,
    val refreshToken: String
)


data class GeneratedToken(
    val token: String,
    val tokenId: String,
    val expiresAt: Date,
    val isRefresh: Boolean
)

data class GeneratedTokens(
    val tokens: Token,
    val message: String
)
