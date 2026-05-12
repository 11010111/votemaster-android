package de.multiplebytes.votemaster.domain.usecase.vote

import de.multiplebytes.votemaster.domain.model.Profile
import de.multiplebytes.votemaster.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class ProfilesUseCase(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke(): Flow<List<Profile>> = profileRepository.observe()
}
