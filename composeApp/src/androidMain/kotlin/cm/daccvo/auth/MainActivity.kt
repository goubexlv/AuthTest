package cm.daccvo.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cm.daccvo.auth.di.initKoin
import cm.daccvo.auth.security.SecureStorage
import cm.daccvo.auth.security.UserSettingsDataStore
import cm.daccvo.auth.security.UserSettingsDataStoreImpl
import org.koin.java.KoinJavaComponent.inject
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        initKoin()

        // Settings (russhwolf)
        val settings = Settings()
        // Secure storage
        val secureStorage = SecureStorage(this)

        // DataStore
        val userSettings = UserSettingsDataStoreImpl(
            settings = settings,
            secureStorage = secureStorage
        )

        userSettings.onAppStart() // 🔥 important
        setContent {

            App(userSettings)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {

}