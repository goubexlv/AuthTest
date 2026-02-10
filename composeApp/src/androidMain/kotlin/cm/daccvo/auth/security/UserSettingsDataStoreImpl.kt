package cm.daccvo.auth.security

import cm.daccvo.auth.utils.Constants.TOKEN_KEY
import cm.daccvo.auth.utils.Constants.USER_KEY
import cm.daccvo.auth.utils.Constants.USE_KEY
import cm.horion.models.response.ProfileResponse
import cm.horion.models.response.Token
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserSettingsDataStoreImpl(private val settings: Settings,private val secureStorage: SecureStorage) : UserSettingsDataStore {

    private val _tokenFlow = MutableStateFlow<String?>(getRefreshToken())
    override val tokenFlow: StateFlow<String?> = _tokenFlow

    private val _userFlow = MutableStateFlow(getUserSettings())
    override val userFlow: StateFlow<ProfileResponse?> = _userFlow

    private val _authState = MutableStateFlow<AuthState>(AuthState.Checking)
    override val authState: StateFlow<AuthState> = _authState

    private val _useFlow = MutableStateFlow<Boolean>(hasSeenOnboarding())
    override val useFlow: StateFlow<Boolean> = _useFlow

    // connexion verification
    override fun onAppStart() {
        val token = getTokenSettings()
        val hasSeenOnboarding = hasSeenOnboarding()

        _authState.value = when {
            !hasSeenOnboarding == false -> AuthState.CheckingFirstUse
            token != null -> AuthState.Authenticated
            else -> AuthState.Unauthenticated
        }
    }

    override fun onLoginSuccess(token: Token) {
        saveTokenSettings(token)
    }

    override fun logout() {
        clear()
        _authState.value = AuthState.Unauthenticated
    }

    override fun hasSeenOnboarding(): Boolean {
        return settings.getBoolean(USE_KEY, false)
    }

    override fun markOnboardingSeen(use: Boolean) {
        settings[USE_KEY] = use
        _useFlow.value = use
    }

    // TOKEN (secure)

    override fun saveTokenSettings(token: Token?) {
        if (token == null) return
        val json = Json.encodeToString(token)
        secureStorage.save(TOKEN_KEY, json)
        _tokenFlow.value = token.refreshToken
    }

    override fun getTokenSettings(): Token? {
        return secureStorage.read(TOKEN_KEY)?.let {
            runCatching { Json.decodeFromString<Token>(it) }.getOrNull()
        }
    }

    override fun getAccessToken(): String? =
        getTokenSettings()?.accessToken

    override fun getRefreshToken(): String? =
        getTokenSettings()?.refreshToken

    override fun setAccessToken(newToken: String?) {
        val current = getTokenSettings() ?: return
        saveTokenSettings(current.copy(accessToken = newToken.orEmpty()))
    }

    override fun setRefreshToken(newToken: String?) {
        val current = getTokenSettings() ?: return
        saveTokenSettings(current.copy(refreshToken = newToken.orEmpty()))
    }

    //  USER (non sensible → Settings)

    override fun saveUserSettings(user: ProfileResponse?) {
        if (user == null) return
        val json = Json.encodeToString(user)
        settings[USER_KEY] = json
        _userFlow.value = user
    }

    override fun getUserSettings(): ProfileResponse? {
        val json = settings.getString(USER_KEY, "")
        return json.takeIf { it.isNotEmpty() }?.let {
            runCatching { Json.decodeFromString<ProfileResponse>(it) }.getOrNull()
        }
    }

    // 🚪 LOGOUT
    override fun clear() {
        secureStorage.remove(TOKEN_KEY)
        settings.remove(USER_KEY)
        _tokenFlow.value = null
        _userFlow.value = null
        _useFlow.value = false
    }

}