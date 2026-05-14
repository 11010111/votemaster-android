package de.multiplebytes.votemaster.presentation.feature.vote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.multiplebytes.votemaster.domain.model.Vote
import de.multiplebytes.votemaster.domain.usecase.vote.CreateVoteUseCase
import de.multiplebytes.votemaster.domain.usecase.vote.VotesUseCase
import de.multiplebytes.votemaster.domain.usecase.profile.SingleProfileUseCase
import de.multiplebytes.votemaster.presentation.common.BaseViewModel
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VoteViewModel(
    private val singleProfileUseCase: SingleProfileUseCase,
    private val votesUseCase: VotesUseCase,
    private val createVoteUseCase: CreateVoteUseCase
) : ViewModel(), BaseViewModel<VoteUiState, VoteIntent> {
    private val _uiState = MutableStateFlow(VoteUiState())
    override val uiState = _uiState.asStateFlow()

    init {
        fetchNextProfile()
    }

    override fun onIntent(intent: VoteIntent) {
        when (intent) {
            is VoteIntent.Upvote -> {
                createVote(id = intent.id)
            }

            is VoteIntent.Refresh -> {
                fetchNextProfile()
            }
        }
    }

    private fun fetchNextProfile() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    voteStatus = VoteStatus.Loading
                )
            }

            val votes = votesUseCase().map { vote -> vote.id }

            singleProfileUseCase(exclude = votes)
                .onSuccess { profile ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            voteStatus = VoteStatus.Success(profile = profile)
                        )
                    }
                }
                .onFailure { exception ->
                    when (exception) {
                        is RestException -> {
                            _uiState.update { currentState ->
                                currentState.copy(
                                    voteStatus = VoteStatus.Failure(
                                        message = "Service unavailable"
                                    )
                                )
                            }
                        }

                        else -> {
                            _uiState.update { currentState ->
                                currentState.copy(
                                    voteStatus = VoteStatus.Failure(
                                        message = "Connection failed"
                                    )
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun createVote(id: String) {
        viewModelScope.launch {
            createVoteUseCase(vote = Vote(id = id))
            fetchNextProfile()
        }
    }
}
