package cm.daccvo.auth.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SecureStorage(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val securePrefs = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun save(key: String, value: String) {
        securePrefs.edit().putString(key, value).apply()
    }

    fun read(key: String): String? =
        securePrefs.getString(key, null)

    fun remove(key: String) {
        securePrefs.edit().remove(key).apply()
    }

    fun clear() {
        securePrefs.edit().clear().apply()
    }
}
