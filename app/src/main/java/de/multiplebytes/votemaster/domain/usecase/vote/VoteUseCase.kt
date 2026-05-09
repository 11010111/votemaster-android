package de.multiplebytes.votemaster.domain.usecase.vote

import de.multiplebytes.votemaster.domain.model.Vote
import de.multiplebytes.votemaster.domain.repository.VoteRepository

class VoteUseCase(
    private val voteRepository: VoteRepository
) {
    suspend operator fun invoke(exclude: List<String>): Result<Vote?> =
        voteRepository.vote(exclude = exclude)
}
