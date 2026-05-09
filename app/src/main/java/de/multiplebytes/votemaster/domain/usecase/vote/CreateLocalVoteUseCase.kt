package de.multiplebytes.votemaster.domain.usecase.vote

import de.multiplebytes.votemaster.domain.model.VoteRecord
import de.multiplebytes.votemaster.domain.repository.LocalVoteRepository

class CreateLocalVoteUseCase(
    private val localVoteRepository: LocalVoteRepository
) {
    suspend operator fun invoke(vote: VoteRecord) = localVoteRepository.create(vote = vote)
}
