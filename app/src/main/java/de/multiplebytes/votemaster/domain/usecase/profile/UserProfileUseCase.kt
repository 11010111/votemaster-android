package de.multiplebytes.votemaster.domain.usecase.profile

import de.multiplebytes.votemaster.domain.model.Profile
import de.multiplebytes.votemaster.domain.repository.ProfileRepository

class UserProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(): Result<Profile?> = profileRepository.selectUserProfile()
}
