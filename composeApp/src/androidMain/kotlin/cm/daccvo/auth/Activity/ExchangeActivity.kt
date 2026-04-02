package cm.daccvo.auth.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import cm.daccvo.auth.Activity.LoginActivity
import cm.daccvo.auth.api.repository.AuthRepository
import cm.daccvo.auth.api.usecase.AuthUseCase
import cm.daccvo.auth.security.AppVerifier
import cm.daccvo.auth.security.AuthState
import cm.daccvo.auth.security.SecureStorage
import cm.daccvo.auth.security.UserSettingsDataStore
import cm.daccvo.auth.security.UserSettingsDataStoreImpl
import cm.daccvo.auth.utils.AuthConfig
import cm.daccvo.auth.utils.appContext
import cm.daccvo.auth.utils.provideSettings
import cm.horion.models.response.isExpiredSoon
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class ExchangeActivity : ComponentActivity() {
    private lateinit var userSettings: UserSettingsDataStore
    private val authRepository: AuthRepository by lazy {
        KoinJavaComponent.getKoin().get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Secure storage
        val secureStorage = SecureStorage(appContext)

        // DataStore
        userSettings = UserSettingsDataStoreImpl(
            settings = provideSettings(),
            secureStorage = secureStorage
        )

        val caller = callingPackage
        val service = intent.data?.getQueryParameter("service")

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

        // 1. Lancer la vérification en coroutine
        lifecycleScope.launch {
            userSettings.onAppStart()

            val currentState = userSettings.authState.value

            if (currentState is AuthState.Authenticated) {
                // Tenter un rafraîchissement silencieux avant de générer le token d'échange
                val isTokenValid = refreshIfNecessary()

                if (isTokenValid) {
                    sendSuccessResponse()
                } else {
                    // Refresh a échoué (ex: refreshToken expiré), l'utilisateur doit se relogger
                    redirectToLogin()
                }
            } else {
                redirectToLogin()
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

    private fun sendSuccessResponse() {
        val service = intent.data?.getQueryParameter("service") ?: ""
        //val tokens = generateTokenForService(service)

        val resultIntent = Intent().apply {
//            putExtra("accessToken", tokens.accessToken)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun sendErrorResponse(resultCode: Int) {
        setResult(resultCode)
        finish()
    }

    private fun redirectToLogin() {
        val loginIntent = Intent(this, LoginActivity::class.java).apply {
            // On passe les paramètres originaux pour pouvoir revenir ici après le login
            data = intent.data
            // Important pour ne pas avoir de problèmes de pile d'activités
            addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
        }
        startActivity(loginIntent)
        finish()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0) // Supprime l'animation de fermeture
    }
}