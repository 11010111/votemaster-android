package de.multiplebytes.votemaster.domain.usecase.vote

import de.multiplebytes.votemaster.domain.model.Vote
import de.multiplebytes.votemaster.domain.repository.VoteRepository
import kotlinx.coroutines.flow.Flow

class VotesUseCase(
    private val voteRepository: VoteRepository
) {
    operator fun invoke(): Flow<List<Vote>> = voteRepository.observe()
}
