package de.multiplebytes.votemaster.presentation.feature.vote

import de.multiplebytes.votemaster.domain.model.Vote

sealed interface VoteUiState {
    data object Loading : VoteUiState
    data class Success(val vote: Vote) : VoteUiState
    data class Failure(val message: String) : VoteUiState
}

data class VoteContract(
    val credits: Int = 0,
    val voteUiState: VoteUiState = VoteUiState.Loading
)

sealed interface VoteIntent {
    data object Inkrement : VoteIntent
    data object Refresh : VoteIntent
}
