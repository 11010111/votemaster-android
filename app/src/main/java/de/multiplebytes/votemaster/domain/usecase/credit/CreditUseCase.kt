package de.multiplebytes.votemaster.domain.usecase.credit

import de.multiplebytes.votemaster.domain.model.Credit
import de.multiplebytes.votemaster.domain.repository.CreditRepository
import kotlinx.coroutines.flow.Flow

class CreditUseCase(
    private val creditRepository: CreditRepository
) {
    operator fun invoke(): Flow<Credit> = creditRepository.credit()
}
