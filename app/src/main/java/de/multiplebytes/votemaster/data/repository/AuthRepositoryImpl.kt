package de.multiplebytes.votemaster.data.repository

import de.multiplebytes.votemaster.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo

class AuthRepositoryImpl(
    private val supabaseClient: SupabaseClient
) : AuthRepository {
    override fun sessionStatus() = supabaseClient.auth.sessionStatus

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<Unit> = runCatching {
        supabaseClient.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): Result<UserInfo?> = runCatching {
        supabaseClient.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
    }

    override suspend fun signOut() {
        supabaseClient.auth.signOut()
    }
}
