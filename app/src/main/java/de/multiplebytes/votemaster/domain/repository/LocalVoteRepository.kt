package de.multiplebytes.votemaster.domain.repository

import de.multiplebytes.votemaster.domain.model.VoteRecord

interface LocalVoteRepository {
    suspend fun votes(): List<VoteRecord>
    suspend fun create(vote: VoteRecord)
}
