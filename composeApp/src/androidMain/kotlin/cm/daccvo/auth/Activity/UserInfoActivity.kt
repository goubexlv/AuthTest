package cm.daccvo.auth.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import cm.daccvo.auth.Activity.LoginActivity
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

class UserInfoActivity : ComponentActivity() {

    private lateinit var userSettings: UserSettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Initialisation (Assure-toi que c'est fait ici si pas de Koin/Hilt global)
        initSettings(this)
        val secureStorage = SecureStorage(applicationContext)
        userSettings = UserSettingsDataStoreImpl(provideSettings(), secureStorage)

        val caller = callingPackage

        // 2. Sécurité : On ne donne pas les infos à n'importe qui
        val isTrusted = caller?.let {
            AppVerifier.isAppTrusted(this, it, AuthConfig.trustedAppSignatures)
        } ?: false

        if (!isTrusted) {
            setResult(Activity.RESULT_CANCELED)
            finish()
            return
        }

        // 3. Récupération et envoi des données
//        lifecycleScope.launch {
//            try {
//                // On récupère les infos stockées (User est un data class avec nom, email, etc.)
//                val user = userSettings.getUserData()
//
//                if (user != null) {
//                    val resultIntent = Intent().apply {
//                        putExtra("user_id", user.id)
//                        putExtra("name", user.name)
//                        putExtra("email", user.email)
//                        putExtra("profile_picture", user.avatarUrl)
//                    }
//                    setResult(Activity.RESULT_OK, resultIntent)
//                } else {
//                    setResult(Activity.RESULT_CANCELED)
//                }
//            } catch (e: Exception) {
//                setResult(Activity.RESULT_FIRST_USER)
//            } finally {
//                finish()
//                overridePendingTransition(0, 0)
//            }
//        }
    }
}