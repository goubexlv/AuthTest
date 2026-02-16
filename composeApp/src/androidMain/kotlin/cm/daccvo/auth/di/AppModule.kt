package cm.daccvo.auth.di

import android.content.Context
import cm.daccvo.auth.api.repository.AuthRepository
import cm.daccvo.auth.api.repository.AuthRepositoryImpl
import cm.daccvo.auth.api.service.AuthService
import cm.daccvo.auth.api.usecase.AuthUseCase
import cm.daccvo.auth.security.AuthState
import cm.daccvo.auth.security.SecureStorage
import cm.daccvo.auth.security.UserSettingsDataStore
import cm.daccvo.auth.security.UserSettingsDataStoreImpl
import cm.daccvo.auth.utils.appContext
import cm.daccvo.auth.utils.provideSettings
import cm.daccvo.auth.viewModels.AccountViewModel
import cm.daccvo.auth.viewModels.DashboardViewModel
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<UserSettingsDataStore> { UserSettingsDataStoreImpl(provideSettings(), get()) }
    single { SecureStorage(appContext) }
    single { AuthUseCase() }
    single { AuthService(get()) }
    viewModel { AccountViewModel(get()) }
    viewModel { DashboardViewModel(get(),get()) }

}

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}