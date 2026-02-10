package cm.daccvo.auth.security

import android.content.Context
import cm.horion.models.response.ProfileResponse
import cm.horion.models.response.Token
import kotlinx.coroutines.flow.StateFlow

interface UserSettingsDataStore {
    val userFlow: StateFlow<ProfileResponse?>
    val tokenFlow: StateFlow<String?>
    val authState: StateFlow<AuthState>
    val useFlow: StateFlow<Boolean>

    fun getRefreshToken(): String?
    fun getAccessToken(): String?
    fun setAccessToken(newToken: String?)
    fun setRefreshToken(newToken: String?)

    fun saveTokenSettings(token: Token?)
    fun getTokenSettings() : Token?

    fun saveUserSettings(settingsData: ProfileResponse?)
    fun getUserSettings(): ProfileResponse?

    fun hasSeenOnboarding(): Boolean
    fun markOnboardingSeen(use : Boolean)

    //logique pour voir si user es connecter
    fun onAppStart()
    fun onLoginSuccess(token: Token)
    fun logout()

    fun clear()
}


