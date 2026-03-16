package de.multiplebytes.votemaster.presentation.feature.vote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.multiplebytes.votemaster.domain.model.Vote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class VoteViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(VoteCreditUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadVote()
    }

    fun loadVote() {
        viewModelScope.launch {
            try {
                _uiState.update { currentState ->
                    currentState.copy(
                        voteUiState = VoteUiState.Success(
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
                        voteUiState = VoteUiState.Failure(
                            message = e.message ?: "Unknown error"
                        )
                    )
                }
            }
        }
    }

    fun incrementPoints() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    credits = currentState.credits + 1
                )
            }
        }
    }
}
