package cm.daccvo.auth.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import authtest.composeapp.generated.resources.Res
import cm.daccvo.auth.api.usecase.AuthUseCase
import cm.daccvo.auth.uiState.AccountUiState
import cm.horion.models.domain.LoginMethod
import kotlinx.coroutines.launch

class AccountViewModel(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    var uiState by mutableStateOf(AccountUiState())
        private set

    init {
        uiState = uiState.copy(
            isAuthenticating = false,
            authenticationSucceed = false,
            //authErrorMessage = null
        )
    }

    fun login(){
        viewModelScope.launch {
            uiState = uiState.copy(
                isAuthenticating = true,
                authenticationSucceed = false,
                authErrorMessage = null
            )
            val response = authUseCase.login(uiState.email,uiState.phone,uiState.password,uiState.model)
            uiState = if(response.success){
                authUseCase.getUser()
                uiState.copy(
                    isAuthenticating = false,
                    authenticationSucceed = true
                )

            } else {
                uiState.copy(
                    isAuthenticating = false,
                    authErrorMessage = response.message
                )
            }
        }
    }

    fun registe() {
        viewModelScope.launch {
            uiState = uiState.copy(
                isAuthenticating = true,
                authenticationSucceed = false,
                authErrorMessage = null
                )
            val response = authUseCase.register(uiState.email,uiState.phone,uiState.password)

            uiState = if (response.success) {
                uiState.copy(
                    isAuthenticating = false,
                    authenticationSucceed = true
                )
            } else {
                uiState.copy(
                    isAuthenticating = false,
                    authErrorMessage = response.message
                )
            }
        }
    }

    fun verify() {
        viewModelScope.launch {
            uiState = uiState.copy(
                isAuthenticating = true,
                authenticationSucceed = false,
                authErrorMessage = null
            )
            val response = authUseCase.verify(uiState.email,uiState.phone,uiState.model)
            uiState = if (response.success) {
                uiState.copy(
                    isAuthenticating = false,
                    authenticationSucceed = true
                )
            } else {
                uiState.copy(
                    isAuthenticating = false,
                    authErrorMessage = response.message
                )
            }
        }
    }

    fun confirm() {
        viewModelScope.launch {
            uiState = uiState.copy(
                isAuthenticating = true,
                authenticationSucceed = false,
                authErrorMessage = null
            )
            val response = authUseCase.confirm(uiState.email,uiState.phone,uiState.code,uiState.model)
            uiState = if (response.success) {
                uiState.copy(
                    isAuthenticating = false,
                    authenticationSucceed = true
                )
            } else {
                uiState.copy(
                    isAuthenticating = false,
                    authErrorMessage = response.message
                )
            }
        }
    }



    fun updateEmail(input: String){
        uiState = uiState.copy(email = input)
    }

    fun updatePhone(input: String){
        uiState = uiState.copy(phone = input)
    }

    fun updateCode(input: String){
        uiState = uiState.copy(code = input)
    }

    fun updatePassword(input: String){
        uiState = uiState.copy(password = input)
    }

    fun updateConfirmPassword(input: String){
        uiState = uiState.copy(confirmPassword = input)
    }

    fun updateModel(input: LoginMethod){
        uiState = uiState.copy(model = input)
    }

    fun clearErrorMessage() {
        uiState =  uiState.copy(authErrorMessage = null)
    }

    fun resetState() {
        uiState = AccountUiState()
    }


}