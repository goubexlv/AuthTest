package cm.daccvo.auth.api.repository

import cm.daccvo.auth.api.models.response.Response
import cm.horion.models.domain.LoginMethod
import cm.horion.models.response.AuthResponse


interface AuthRepository {

    suspend fun register(email:String?, phone:String?, password:String) : Response

    suspend fun login(email:String?, phone:String?, password:String,model: LoginMethod) : Response

    suspend fun verify(email:String?, phone:String?, model: LoginMethod): Boolean

    suspend fun confirm(email:String?, phone:String?,code:String, model: LoginMethod): Response


}