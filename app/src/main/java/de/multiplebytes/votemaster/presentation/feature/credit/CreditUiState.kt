package de.multiplebytes.votemaster.presentation.feature.credit

data class CreditUiState(
    val credits: Int = 0
)

sealed interface CreditIntent {
    data object Inkrement : CreditIntent
}
