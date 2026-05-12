package de.multiplebytes.votemaster.domain.repository

import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun sessionStatus(): Flow<SessionStatus>
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signUp(
        name: String,
        biography: String,
        email: String,
        password: String,
        photo: ByteArray
    ): Result<UserInfo?>

    suspend fun signOut()
}
