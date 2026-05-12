package de.multiplebytes.votemaster.presentation.feature.vote

import de.multiplebytes.votemaster.domain.model.Profile

sealed interface VoteStatus {
    data object Loading : VoteStatus
    data class Success(val profile: Profile?) : VoteStatus
    data class Failure(val message: String) : VoteStatus
}

data class VoteUiState(
    val voteStatus: VoteStatus = VoteStatus.Loading
)

sealed interface VoteIntent {
    data class Upvote(val id: String) : VoteIntent
    data object Refresh : VoteIntent
}
