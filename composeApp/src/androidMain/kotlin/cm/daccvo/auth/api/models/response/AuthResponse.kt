package cm.horion.models.response

import cm.daccvo.auth.security.JwtHelper
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

fun Token.isExpiredSoon(): Boolean {
    val expirationTime = JwtHelper.getExpirationDate(this.accessToken) ?: return true

    // Marge de sécurité de 5 minutes (300 000 ms)
    val bufferTime = 5 * 60 * 1000

    // Si (Maintenant + 5min) est plus grand que la date d'expiration, on rafraîchit
    return (System.currentTimeMillis() + bufferTime) >= expirationTime
}

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
