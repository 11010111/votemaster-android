package de.multiplebytes.votemaster.domain.repository

import de.multiplebytes.votemaster.domain.model.Vote

interface VoteRepository {
    suspend fun votes(): List<Vote>
    suspend fun create(vote: Vote)
}
