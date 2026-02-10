package cm.daccvo.auth.api.usecase

import android.util.Log
import cm.daccvo.auth.api.models.response.Response
import cm.daccvo.auth.api.repository.AuthRepository
import cm.horion.models.domain.LoginMethod
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class AuthUseCase : KoinComponent {

    private val repository : AuthRepository by inject()

    suspend fun register(email:String, phone:String, password:String) : Response {
        val newEmail = email.ifEmpty {
            null
        }

        val newPhone = phone.ifEmpty {
            null
        }

        return repository.register(newEmail,newPhone,password)
    }

    suspend fun login(email:String, phone:String, password:String,model: LoginMethod) : Response {
        val newEmail = email.ifEmpty {
            null
        }

        val newPhone = phone.ifEmpty {
            null
        }
        return repository.login(newEmail,newPhone,password,model)
    }

    suspend fun verify(email:String, phone:String, model: LoginMethod): Response {
        val newEmail = email.ifEmpty {
            null
        }

        val newPhone = phone.ifEmpty {
            null
        }

        return repository.verify(newEmail,newPhone,model)
    }

    suspend fun confirm(email:String, phone:String,code:String, model: LoginMethod): Response {
        val newEmail = email.ifEmpty {
            null
        }
        val newPhone = phone.ifEmpty {
            null
        }

        return repository.confirm(newEmail,newPhone,code,model)
    }

    suspend fun getUser() : Response? {
        return repository.getUser()
    }

}