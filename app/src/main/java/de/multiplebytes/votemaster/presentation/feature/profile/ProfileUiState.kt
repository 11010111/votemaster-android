package de.multiplebytes.votemaster.presentation.feature.profile

import de.multiplebytes.votemaster.domain.model.Profile

sealed interface ProfileStatus {
    data object Loading : ProfileStatus
    data class Success(val profile: Profile) : ProfileStatus
    data class Failure(val message: String) : ProfileStatus
}

data class ProfileUiState(
    val profileStatus: ProfileStatus = ProfileStatus.Loading
)

sealed interface ProfileIntent {
    data object Edit : ProfileIntent
    data object Retry : ProfileIntent
}
