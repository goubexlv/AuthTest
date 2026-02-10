package cm.daccvo.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import cm.daccvo.auth.di.initKoin
import cm.daccvo.auth.security.SecureStorage
import cm.daccvo.auth.security.UserSettingsDataStore
import cm.daccvo.auth.security.UserSettingsDataStoreImpl
import cm.daccvo.auth.utils.appContext
import cm.daccvo.auth.utils.initSettings
import cm.daccvo.auth.utils.provideSettings
import org.koin.java.KoinJavaComponent.inject
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        initSettings(this)
        //initKoin()

        // Secure storage
        val secureStorage = SecureStorage(appContext)

        // DataStore
        val userSettings = UserSettingsDataStoreImpl(
            settings = provideSettings(),
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