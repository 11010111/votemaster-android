package de.multiplebytes.votemaster.presentation.feature.auth

import io.github.jan.supabase.auth.status.SessionStatus

data class AuthUiState(
    val sessionStatus: SessionStatus = SessionStatus.Initializing,
    val errorMessage: String? = null
)

sealed interface AuthIntent {
    data class SignIn(val email: String, val password: String) : AuthIntent
    data class SignUp(val email: String, val password: String, val photo: ByteArray?) : AuthIntent
    data object SignOut : AuthIntent
}
