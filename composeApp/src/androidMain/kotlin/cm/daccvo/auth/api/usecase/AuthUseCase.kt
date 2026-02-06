package cm.daccvo.auth.api.usecase

import cm.daccvo.auth.api.models.response.Response
import cm.daccvo.auth.api.repository.AuthRepository
import cm.horion.models.domain.LoginMethod
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class AuthUseCase : KoinComponent {

    private val repository : AuthRepository by inject()

    suspend fun register(email:String?, phone:String?, password:String) : Response {
        return repository.register(email,phone,password)
    }

    suspend fun login(email:String?, phone:String?, password:String,model: LoginMethod) : Response {
        return repository.login(email,phone,password,model)
    }

    suspend fun verify(email:String?, phone:String?, model: LoginMethod): Boolean {
        return repository.verify(email,phone,model)
    }

    suspend fun confirm(email:String?, phone:String?,code:String, model: LoginMethod): Response {
        return repository.confirm(email,phone,code,model)
    }

}