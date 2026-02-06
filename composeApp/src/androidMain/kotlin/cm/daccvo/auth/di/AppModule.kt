package cm.daccvo.auth.di

import cm.daccvo.auth.api.repository.AuthRepository
import cm.daccvo.auth.api.repository.AuthRepositoryImpl
import cm.daccvo.auth.api.service.AuthService
import cm.daccvo.auth.api.usecase.AuthUseCase
import cm.daccvo.auth.security.SecureStorage
import cm.daccvo.auth.security.UserSettingsDataStore
import cm.daccvo.auth.security.UserSettingsDataStoreImpl
import cm.daccvo.auth.viewModels.AccountViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<AuthRepository> { AuthRepositoryImpl(get()) }

    viewModel { AccountViewModel(get()) }

    single { AuthUseCase() }

    single { AuthService(get()) }
    single { SecureStorage(get()) }

    single<UserSettingsDataStore> { UserSettingsDataStoreImpl(get(),get()) }
}

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}