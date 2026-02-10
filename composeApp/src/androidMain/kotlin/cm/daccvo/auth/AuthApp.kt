package cm.daccvo.auth

import android.app.Application
import cm.daccvo.auth.di.initKoin

class AuthApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}