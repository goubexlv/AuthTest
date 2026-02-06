package cm.daccvo.auth.api.service

import cm.daccvo.auth.api.models.Endpoint
import cm.daccvo.auth.api.models.response.Response
import cm.daccvo.auth.api.utils.ApiCient.client
import cm.daccvo.auth.api.utils.Constants.AUTH_URL
import cm.daccvo.auth.security.UserSettingsDataStore
import cm.horion.models.domain.ClientType
import cm.horion.models.domain.LoginMethod
import cm.horion.models.request.LoginRequest
import cm.horion.models.request.RegisterRequest
import cm.horion.models.request.VerifyRequest
import cm.horion.models.response.AuthResponse
import cm.horion.models.response.Token
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
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
import io.ktor.http.headers
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import models.request.ConfirmRequest

@Serializable
data class Error(val error : String)
@Serializable
data class Success(val message : String,val attemptsRemaining: String? = null,val canRetry : Boolean? =null,val canRequestNewCode : Boolean? =null)

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

    suspend fun login(request : LoginRequest,model: LoginMethod) : Response {
        val response: HttpResponse = client.post("$AUTH_URL${Endpoint.Login.path}") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            url {
                parameter("model", model.name)
            }
            headers {
                append("X-Client-Type", ClientType.MOBILE.name)
            }
            setBody(request)
        }

        val responseText = response.bodyAsText()
        return if (response.status == HttpStatusCode.OK) {
            val token = response.body<Token>()
            settingStore.saveTokenSettings(token)
            Response(success = true, message = "Connexion reussi")
        } else {
            val res = Json.decodeFromString<Error>(responseText)
            Response(success = false, message = res.error)
        }
    }

    suspend fun verify(request: VerifyRequest, model: LoginMethod) : Boolean {
        val response: HttpResponse = client.post("$AUTH_URL${Endpoint.Verify.path}") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            url {
                parameter("model", model.name)
            }
            headers {
                append("X-Client-Type", ClientType.MOBILE.name)
            }
            setBody(request)
        }

        return when (response.status) {
            HttpStatusCode.OK -> true
            HttpStatusCode.BadRequest -> false
            HttpStatusCode.Unauthorized -> false
            else -> false
        }

    }

    suspend fun confirm(request: ConfirmRequest,model: LoginMethod) : Response {
        val response: HttpResponse = client.post("$AUTH_URL${Endpoint.Confirm.path}") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            headers {
                append("X-Client-Type", ClientType.MOBILE.name)
            }
            url {
                parameter("model", model.name)
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

}