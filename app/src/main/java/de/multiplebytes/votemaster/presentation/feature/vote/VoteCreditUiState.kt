package de.multiplebytes.votemaster.presentation.feature.vote

data class VoteCreditUiState(
    val credits: Int = 0,
    val voteUiState: VoteUiState = VoteUiState.Loading
)
