package de.multiplebytes.votemaster.domain.usecase.auth

import de.multiplebytes.votemaster.domain.repository.AuthRepository

class SessionStatusUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.sessionStatus()
}
