package cm.daccvo.auth

import android.app.Application
import cm.daccvo.auth.di.initKoin
import cm.daccvo.auth.utils.initSettings

class AuthApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        initSettings(this)
    }
}