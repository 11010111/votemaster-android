package de.multiplebytes.votemaster.domain.usecase.vote

import de.multiplebytes.votemaster.domain.model.Vote
import de.multiplebytes.votemaster.domain.repository.VoteRepository

class CreateVoteUseCase(
    private val voteRepository: VoteRepository
) {
    suspend operator fun invoke(vote: Vote) = voteRepository.create(vote = vote)
}
