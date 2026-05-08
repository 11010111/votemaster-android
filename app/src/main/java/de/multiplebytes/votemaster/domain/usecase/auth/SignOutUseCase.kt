package de.multiplebytes.votemaster.domain.usecase.auth

import de.multiplebytes.votemaster.domain.repository.AuthRepository

class SignOutUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() = authRepository.signOut()
}
