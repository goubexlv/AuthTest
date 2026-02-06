package cm.daccvo.auth.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cm.daccvo.auth.api.usecase.AuthUseCase
import cm.daccvo.auth.uiState.AccountUiState
import cm.horion.models.domain.LoginMethod
import kotlinx.coroutines.launch

class AccountViewModel(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    var uiState by mutableStateOf(AccountUiState())
        private set


    fun login(model : LoginMethod){
        viewModelScope.launch {
            uiState = uiState.copy(isAuthenticating = true)
            val response = authUseCase.login(uiState.email,uiState.phone,uiState.password,model)
            uiState = if(response.success){
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
            uiState = uiState.copy(isAuthenticating = true)
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

    fun verify(model : LoginMethod) {
        viewModelScope.launch {
            uiState = uiState.copy(isAuthenticating = true)
            val response = authUseCase.verify(uiState.email,uiState.phone,model)
            uiState = if (response) {
                uiState.copy(
                    isAuthenticating = false,
                    authenticationSucceed = true
                )
            } else {
                uiState.copy(
                    isAuthenticating = false,
                    authErrorMessage = "Une erreur rencontrer ressayer plus tard"
                )
            }
        }
    }

    fun confirm(model : LoginMethod) {
        viewModelScope.launch {
            uiState = uiState.copy(isAuthenticating = true)
            val response = authUseCase.confirm(uiState.email,uiState.phone,uiState.code,model)
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

    fun clearErrorMessage() {
        uiState =  uiState.copy(authErrorMessage = null)
    }

}