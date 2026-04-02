package cm.daccvo.auth.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import cm.daccvo.auth.api.usecase.AuthUseCase
import cm.daccvo.auth.security.AppVerifier
import cm.daccvo.auth.security.UserSettingsDataStore
import cm.daccvo.auth.utils.AuthConfig
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class LogoutActivity : ComponentActivity() {

    private lateinit var userSettings: UserSettingsDataStore
    private val authRepository: AuthUseCase by lazy {
        KoinJavaComponent.getKoin().get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val caller = callingPackage

        // 1. Sécurité : Seules tes apps peuvent demander un logout global
        val isTrusted = caller?.let {
            AppVerifier.isAppTrusted(this, it, AuthConfig.trustedAppSignatures)
        } ?: false

        if (!isTrusted) {
            setResult(RESULT_CANCELED)
            finish()
            return
        }

        // 2. Action de déconnexion
        lifecycleScope.launch {
            try {
                // Optionnel : Prévenir le backend pour révoquer le Refresh Token
                authRepository.logout()

                // Effacer les jetons et réinitialiser l'état local (DataStore)
                userSettings.clear()

                setResult(RESULT_OK)
            } catch (e: Exception) {
                setResult(RESULT_FIRST_USER) // Erreur lors du logout
            } finally {
                finish()
                overridePendingTransition(0, 0)
            }
        }
    }
}