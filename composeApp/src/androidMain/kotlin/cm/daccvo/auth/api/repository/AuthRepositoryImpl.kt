package cm.daccvo.auth.api.repository

import cm.daccvo.auth.api.models.response.Response
import cm.daccvo.auth.api.service.AuthService
import cm.horion.models.domain.LoginMethod
import cm.horion.models.request.LoginWithEmail
import cm.horion.models.request.LoginWithPhone
import cm.horion.models.request.RegisterRequest
import cm.horion.models.request.VerifyEmail
import cm.horion.models.request.VerifyPhone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.request.ConfirmEmail
import models.request.ConfirmPhone


class AuthRepositoryImpl (
    private val authService: AuthService
) : AuthRepository {
    override suspend fun register(email: String?, phone: String?, password: String): Response {
        return withContext(Dispatchers.IO){
            val request = RegisterRequest(email = email,phone = phone, password = password)
            authService.register(request)
        }
    }

    override suspend fun login(email: String?, phone: String?, password: String, model: LoginMethod): Response {
        return withContext(Dispatchers.IO){
            val request = if (email != null){
                LoginWithEmail(email = email, password = password)
            } else {
                LoginWithPhone(phone = phone, password = password)
            }
            authService.login(request,model = model)
        }
    }

    override suspend fun verify(email: String?, phone: String?, model: LoginMethod): Boolean {
        return withContext(Dispatchers.IO){
            val request = if (email != null){
                VerifyEmail(email = email)
            } else {
                VerifyPhone(phone = phone)
            }
            authService.verify(request,model)
        }
    }

    override suspend fun confirm(email: String?, phone: String?, code: String, model: LoginMethod) : Response {
        return withContext(Dispatchers.IO){
            val request = if (email != null){
                ConfirmEmail(email = email,code = code)
            } else {
                ConfirmPhone(phone = phone,code = code)
            }
            authService.confirm(request,model)
        }
    }


}