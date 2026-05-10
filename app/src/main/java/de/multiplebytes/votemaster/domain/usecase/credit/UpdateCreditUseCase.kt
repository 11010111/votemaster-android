package de.multiplebytes.votemaster.domain.usecase.credit

import de.multiplebytes.votemaster.domain.repository.CreditRepository

class UpdateCreditUseCase(
    private val creditRepository: CreditRepository
) {
    suspend operator fun invoke(count: Int) = creditRepository.upsert(count = count)
}
