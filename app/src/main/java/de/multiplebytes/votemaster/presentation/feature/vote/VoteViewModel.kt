package de.multiplebytes.votemaster.presentation.feature.vote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.multiplebytes.votemaster.domain.model.Vote
import de.multiplebytes.votemaster.presentation.common.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class VoteViewModel : ViewModel(), BaseViewModel<VoteUiState, VoteIntent> {
    private val _uiState = MutableStateFlow(VoteUiState())
    override val uiState = _uiState.asStateFlow()

    init {
        loadVote()
    }

    override fun onIntent(intent: VoteIntent) {
        when (intent) {
            is VoteIntent.Inkrement -> {
                incrementPoints()
                loadVote()
            }

            is VoteIntent.Refresh -> {
                loadVote()
            }
        }
    }

    private fun loadVote() {
        viewModelScope.launch {
            try {
                _uiState.update { currentState ->
                    currentState.copy(
                        voteStatus = VoteStatus.Success(
                            vote = Vote(
                                image = "https://picsum.photos/402/878?random=${
                                    Random.nextInt(
                                        1,
                                        100
                                    )
                                }",
                                label = "Preview",
                                location = "Earth"
                            )
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        voteStatus = VoteStatus.Failure(
                            message = e.message ?: "Unknown error"
                        )
                    )
                }
            }
        }
    }

    private fun incrementPoints() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    credits = currentState.credits + 1
                )
            }
        }
    }
}
