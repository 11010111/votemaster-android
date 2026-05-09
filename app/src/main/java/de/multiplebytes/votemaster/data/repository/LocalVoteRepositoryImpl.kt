package de.multiplebytes.votemaster.data.repository

import de.multiplebytes.votemaster.data.local.VoteDao
import de.multiplebytes.votemaster.domain.model.VoteRecord
import de.multiplebytes.votemaster.domain.repository.LocalVoteRepository

class LocalVoteRepositoryImpl(
    private val voteDao: VoteDao
) : LocalVoteRepository {
    override suspend fun votes(): List<VoteRecord> = voteDao.votes()

    override suspend fun create(vote: VoteRecord) {
        voteDao.create(vote = vote)
    }
}
