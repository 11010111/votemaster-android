package de.multiplebytes.votemaster.domain.usecase.auth

import de.multiplebytes.votemaster.domain.repository.AuthRepository

class SignInUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> =
        authRepository.signIn(email = email, password = password)
}
