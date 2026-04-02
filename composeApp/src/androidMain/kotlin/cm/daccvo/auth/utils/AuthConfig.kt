package cm.daccvo.auth.utils

object AuthConfig {
    // Liste des signatures SHA-256 autorisées à demander des tokens
    val trustedAppSignatures = setOf(
        "SHA256_DE_TON_APP_CV",    // CV App
        "SHA256_DE_TON_APP_GAZ",   // Gaz App
        "SHA256_DE_AUTRE_APP"      // Future App
    )
}