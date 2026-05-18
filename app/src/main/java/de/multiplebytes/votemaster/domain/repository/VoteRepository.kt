package de.multiplebytes.votemaster.domain.repository

import de.multiplebytes.votemaster.domain.model.Vote
import kotlinx.coroutines.flow.Flow

interface VoteRepository {
    fun observe(): Flow<List<Vote>>
    suspend fun create(profileId: String)
}
