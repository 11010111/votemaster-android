package de.multiplebytes.votemaster.domain.usecase.profile

import de.multiplebytes.votemaster.domain.model.Profile
import de.multiplebytes.votemaster.domain.repository.ProfileRepository

class SingleProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(exclude: List<String>): Result<Profile?> =
        profileRepository.select(exclude = exclude)
}