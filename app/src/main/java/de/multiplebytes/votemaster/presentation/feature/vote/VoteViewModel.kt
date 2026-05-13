package de.multiplebytes.votemaster.presentation.feature.vote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.multiplebytes.votemaster.domain.model.Vote
import de.multiplebytes.votemaster.domain.usecase.vote.CreateVoteUseCase
import de.multiplebytes.votemaster.domain.usecase.vote.VotesUseCase
import de.multiplebytes.votemaster.domain.usecase.vote.SingleProfileUseCase
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
        loadVote()
    }

    override fun onIntent(intent: VoteIntent) {
        when (intent) {
            is VoteIntent.Upvote -> {
                createLocalVote(id = intent.id)
            }

            is VoteIntent.Refresh -> {
                loadVote()
            }
        }
    }

    private fun loadVote() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    voteStatus = VoteStatus.Loading
                )
            }

            val localVotes = votesUseCase().map { vote -> vote.id }

            singleProfileUseCase(exclude = localVotes)
                .onSuccess { vote ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            voteStatus = VoteStatus.Success(profile = vote)
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

    private fun createLocalVote(id: String) {
        viewModelScope.launch {
            createVoteUseCase(vote = Vote(id = id))
            loadVote()
        }
    }
}
