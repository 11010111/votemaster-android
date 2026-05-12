package de.multiplebytes.votemaster.presentation.feature.auth

import io.github.jan.supabase.auth.status.SessionStatus

data class AuthUiState(
    val sessionStatus: SessionStatus = SessionStatus.Initializing,
    val errorMessage: String? = null
)

sealed interface AuthIntent {
    data class SignIn(val email: String, val password: String) : AuthIntent
    data class SignUp(
        val name: String,
        val biography: String,
        val email: String,
        val password: String,
        val photo: ByteArray
    ) : AuthIntent {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as SignUp

            if (name != other.name) return false
            if (biography != other.biography) return false
            if (email != other.email) return false
            if (password != other.password) return false
            if (!photo.contentEquals(other.photo)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = name.hashCode()
            result = 31 * result + biography.hashCode()
            result = 31 * result + email.hashCode()
            result = 31 * result + password.hashCode()
            result = 31 * result + photo.contentHashCode()
            return result
        }
    }

    data object SignOut : AuthIntent
}
