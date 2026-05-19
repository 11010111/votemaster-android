package de.multiplebytes.votemaster.domain.repository

import de.multiplebytes.votemaster.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun observe(): Flow<List<Profile>>
    suspend fun select(exclude: List<String>): Result<Profile?>
    suspend fun selectUserProfile(): Result<Profile?>
    suspend fun create(profile: Profile): Result<Unit>
    suspend fun update(profile: Profile): Result<Unit>
    suspend fun delete(userId: String): Result<Unit>
}
