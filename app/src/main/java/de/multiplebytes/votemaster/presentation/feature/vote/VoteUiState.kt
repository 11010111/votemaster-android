package de.multiplebytes.votemaster.presentation.feature.vote

import de.multiplebytes.votemaster.domain.model.Vote

sealed interface VoteUiState {
    data object Loading : VoteUiState
    data class Success(val vote: Vote) : VoteUiState
    data class Failure(val message: String) : VoteUiState
}
