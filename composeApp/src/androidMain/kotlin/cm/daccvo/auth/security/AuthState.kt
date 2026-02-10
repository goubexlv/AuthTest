package cm.daccvo.auth.security

import cm.horion.models.response.ProfileResponse

sealed class AuthState {
    object Unauthenticated : AuthState()
    object CheckingFirstUse : AuthState()
    object Checking : AuthState()
    object Authenticated : AuthState()
}