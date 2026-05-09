package de.multiplebytes.votemaster.domain.repository

import de.multiplebytes.votemaster.domain.model.Vote
import kotlinx.coroutines.flow.Flow

interface VoteRepository {
    fun votes(): Flow<List<Vote>>
    suspend fun vote(exclude: List<String>): Result<Vote?>
    suspend fun create(vote: Vote): Result<Unit>
    suspend fun update(vote: Vote): Result<Unit>
    suspend fun delete(voteId: String): Result<Unit>
}
