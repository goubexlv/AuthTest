package cm.daccvo.auth.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.lifecycleScope
import cm.daccvo.auth.App
import cm.daccvo.auth.api.usecase.AuthUseCase
import cm.daccvo.auth.security.AppVerifier
import cm.daccvo.auth.security.AuthState
import cm.daccvo.auth.security.SecureStorage
import cm.daccvo.auth.security.UserSettingsDataStore
import cm.daccvo.auth.security.UserSettingsDataStoreImpl
import cm.daccvo.auth.utils.AuthConfig
import cm.daccvo.auth.utils.appContext
import cm.daccvo.auth.utils.initSettings
import cm.daccvo.auth.utils.provideSettings
import cm.horion.models.response.isExpiredSoon
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class LoginActivity : ComponentActivity() {

    private var service: String? = null
    private lateinit var userSettings: UserSettingsDataStore
    private val authRepository: AuthUseCase by lazy {
        KoinJavaComponent.getKoin().get()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val caller = callingPackage
        Log.d("Test",caller.toString())
        initSettings(this)
        val secureStorage = SecureStorage(appContext)

        // DataStore
        userSettings = UserSettingsDataStoreImpl(
            settings = provideSettings(),
            secureStorage = secureStorage
        )

        service = intent.data?.getQueryParameter("service")

        // On vérifie si l'application qui appelle est dans notre cercle de confiance
//        val isTrusted = caller?.let {
//            AppVerifier.isAppTrusted(this, it, AuthConfig.trustedAppSignatures)
//        } ?: false
//
//        if (!isTrusted || service == null) {
//            // Si l'app n'est pas reconnue, on ne donne aucun token
//            sendErrorResponse(RESULT_CANCELED)
//            return
//        }

        // 2. Vérification de la session existante
//        lifecycleScope.launch {
//            // On s'assure que le DataStore a chargé l'état
//            userSettings.onAppStart()
//
//            val currentState = userSettings.authState.value
//
//            if (currentState is AuthState.Authenticated) {
//                // L'utilisateur est DÉJÀ connecté !
//                // On rafraîchit si besoin pour envoyer un token tout neuf
//                val isTokenValid = refreshIfNecessary()
//
//                if (isTokenValid) {
//                    val currentTokens = userSettings.getTokenSettings()
//                    if (currentTokens != null) {
//                        handleSuccess()
//                        return@launch // On arrête tout ici, l'activité se ferme
//                    }
//                }
//            }
//        }

        setContent {

            MaterialTheme {
                App(
                    onLoginSuccess = {
                        handleSuccess()
                    }
                )
            }
        }
    }

    private suspend fun refreshIfNecessary(): Boolean {
        val currentToken = userSettings.getTokenSettings() ?: return false

        // Si le token expire dans moins de 5 minutes, on rafraîchit
        return if (currentToken.isExpiredSoon()) {
            try {
                // Appel à ton API Ktor pour échanger le refreshToken contre un nouveau couple de tokens
                val response = authRepository.refreshToken()
                if (response != null && response.success) true else false
            } catch (e: Exception) {
                false
            }
        } else {
            true // Le token est encore bon
        }
    }

    private fun sendErrorResponse(resultCode: Int) {
        setResult(resultCode)
        finish()
    }

    private fun handleSuccess() {
        Log.d("Test","connexion reussi")
        // 1. Préparer les données de retour
        val resultIntent = Intent().apply {
            putExtra("status", "SUCCESS")
        }

        // 2. Définir le résultat pour l'appelant (CV App)
        setResult(RESULT_OK, resultIntent)
        finish() // Ferme Auth App et retourne à CV App

    }
    override fun finish() {
        super.finish()
        // Optionnel : supprimer l'animation pour un effet plus "intégré"
        overridePendingTransition(0, 0)
    }
}