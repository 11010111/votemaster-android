package de.multiplebytes.votemaster.domain.usecase.vote

import de.multiplebytes.votemaster.domain.model.Vote
import de.multiplebytes.votemaster.domain.repository.VoteRepository

class VotesUseCase(
    private val voteRepository: VoteRepository
) {
    suspend operator fun invoke(): List<Vote> = voteRepository.votes()
}
