package cm.daccvo.auth.security

import cm.horion.models.response.ProfileResponse

sealed class AuthState {
    object Unauthenticated : AuthState()
    object Checking : AuthState()
    object Authenticated : AuthState()
}