package cm.daccvo.auth.api.service

import android.util.Log
import cm.daccvo.auth.api.models.Endpoint
import cm.daccvo.auth.api.models.response.Response
import cm.daccvo.auth.api.utils.ApiCient.client
import cm.daccvo.auth.api.utils.Constants.AUTH_URL
import cm.daccvo.auth.security.UserSettingsDataStore
import cm.horion.models.domain.ClientType
import cm.horion.models.domain.LoginMethod
import cm.horion.models.request.LoginRequest
import cm.horion.models.request.RegisterRequest
import cm.horion.models.request.VerifyEmail
import cm.horion.models.request.VerifyPhone
import cm.horion.models.request.VerifyRequest
import cm.horion.models.response.AuthResponse
import cm.horion.models.response.ProfileResponse
import cm.horion.models.response.Token
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import models.request.ConfirmRequest

@Serializable
data class Error(val error : String)
@Serializable
data class Success(val message : String,val attemptsRemaining: String? = null,val canRetry : Boolean? =null ,val canRequestNewCode : Boolean? =null)

class AuthService(private val settingStore : UserSettingsDataStore)  {

    suspend fun register(request : RegisterRequest) : Response {
        val response: HttpResponse = client.post("$AUTH_URL${Endpoint.Register.path}") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            headers {
                append("X-Client-Type", ClientType.MOBILE.name)
            }
            setBody(request)
        }
        val responseText = response.bodyAsText()
        return if (response.status == HttpStatusCode.OK) {
                val res = Json.decodeFromString<Success>(responseText)
                Response(success = true, message = res.message)
            } else {
                val res = Json.decodeFromString<Error>(responseText)
                Response(success = false, message = res.error)
        }

    }

    suspend fun login(request : Any,model: LoginMethod) : Response {
        val response: HttpResponse = client.post("$AUTH_URL${Endpoint.Login.path}?method=${model.name}") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            headers {
                append("X-Client-Type", ClientType.MOBILE.name)
            }
            setBody(request)
        }
        val responseText = response.bodyAsText()
        return if (response.status == HttpStatusCode.OK) {
            val token = Json.decodeFromString<Token>(responseText)
            settingStore.onLoginSuccess(token)
            Response(success = true, message = "Connexion reussi")
        } else {
            val res = Json.decodeFromString<Error>(responseText)
            Response(success = false, message = res.error)
        }
    }

    suspend fun getUser() : Response? {
        val response: HttpResponse = client.get("$AUTH_URL${Endpoint.Profile.path}") {
            accept(ContentType.Application.Json)
            Log.d("CLIENT","${settingStore.getAccessToken()}")
            headers {
                append(HttpHeaders.Authorization, "Bearer ${settingStore.getAccessToken()}")
                append("X-Client-Type", ClientType.MOBILE.name)
            }
        }

        val responseText = response.bodyAsText()

        return if (response.status == HttpStatusCode.OK) {
            val user = Json.decodeFromString<ProfileResponse>(responseText)
            settingStore.saveUserSettings(user)
            Response(true,"donnee charger")
        }else if(response.status == HttpStatusCode.Unauthorized){
            null
        }else {
            Response(false,"donnee charger")
        }
    }

    suspend fun verify(request: Any, model: LoginMethod) : Response {
        val response: HttpResponse = client.post("$AUTH_URL${Endpoint.Verify.path}?method=${model.name}") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(request)
        }

        val responseText = response.bodyAsText()
        return if (response.status == HttpStatusCode.OK) {
            val token = response.body<Token>()
            settingStore.saveTokenSettings(token)
            Response(success = true, message = "verification echec")
        } else {
            val res = Json.decodeFromString<Error>(responseText)
            Response(success = false, message = res.error)
        }

    }

    suspend fun confirm(request: Any,model: LoginMethod) : Response {
        val response: HttpResponse = client.post("$AUTH_URL${Endpoint.Confirm.path}?method=${model.name}") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            setBody(request)
        }

        val responseText = response.bodyAsText()

        return if (response.status == HttpStatusCode.OK) {
            val res = Json.decodeFromString<Success>(responseText)
            Response(success = true, message = res.message)
        } else {
            val res = Json.decodeFromString<Error>(responseText)
            Log.d("CLIENT",res.error)
            Response(success = false, message = res.error)
        }
    }

}