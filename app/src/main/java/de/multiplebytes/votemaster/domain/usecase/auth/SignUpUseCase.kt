package de.multiplebytes.votemaster.domain.usecase.auth

import de.multiplebytes.votemaster.domain.repository.AuthRepository
import io.github.jan.supabase.auth.user.UserInfo

class SignUpUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        biography: String,
        email: String,
        password: String,
        photo: ByteArray
    ): Result<UserInfo?> =
        authRepository.signUp(
            name = name,
            biography = biography,
            email = email,
            password = password,
            photo = photo
        )
}
