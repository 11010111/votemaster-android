package de.multiplebytes.votemaster.data.repository

import de.multiplebytes.votemaster.data.local.VoteDao
import de.multiplebytes.votemaster.domain.model.Vote
import de.multiplebytes.votemaster.domain.repository.VoteRepository

class VoteRepositoryImpl(
    private val voteDao: VoteDao
) : VoteRepository {
    override suspend fun votes(): List<Vote> = voteDao.votes()

    override suspend fun create(vote: Vote) {
        voteDao.create(vote = vote)
    }
}
