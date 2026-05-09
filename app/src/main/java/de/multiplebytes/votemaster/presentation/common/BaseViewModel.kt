package de.multiplebytes.votemaster.presentation.common

import kotlinx.coroutines.flow.StateFlow

interface BaseViewModel<STATE, INTENT> {
    val uiState: StateFlow<STATE>
    fun onIntent(intent: INTENT)
}
