package cm.daccvo.auth.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cm.daccvo.auth.security.UserSettingsDataStore
import cm.daccvo.auth.uiState.AccountUiState
import cm.daccvo.auth.uiState.ProfileDataUiState
import cm.horion.models.response.ProfileResponse
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val setting : UserSettingsDataStore
) : ViewModel(){

    var userInfoUiState by mutableStateOf(ProfileDataUiState())
        private set

    init {

        viewModelScope.launch {
            setting.userFlow.collect { user ->
                userInfoUiState = userInfoUiState.copy(
                    profile = ProfileResponse(
                        uuid = user?.uuid.orEmpty(),
                        email = user?.email.orEmpty(),
                        phone = user?.phone.orEmpty(),
                        isEmailVerified = user?.isEmailVerified == true,
                        isPhoneVerified = user?.isEmailVerified == true,
                        warning = user?.warning
                    ) ,
                )
            }


        }
    }

    fun logout() {
        setting.logout()
    }
    fun resetState() {
        userInfoUiState = ProfileDataUiState()
    }


}