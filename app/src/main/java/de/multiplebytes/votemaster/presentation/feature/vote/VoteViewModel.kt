package de.multiplebytes.votemaster.presentation.feature.vote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.multiplebytes.votemaster.domain.model.VoteRecord
import de.multiplebytes.votemaster.domain.usecase.vote.CreateLocalVoteUseCase
import de.multiplebytes.votemaster.domain.usecase.vote.AllLocalVotesUseCase
import de.multiplebytes.votemaster.domain.usecase.vote.SingleVoteUseCase
import de.multiplebytes.votemaster.presentation.common.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VoteViewModel(
    private val singleVoteUseCase: SingleVoteUseCase,
    private val allLocalVotesUseCase: AllLocalVotesUseCase,
    private val createLocalVoteUseCase: CreateLocalVoteUseCase
) : ViewModel(), BaseViewModel<VoteUiState, VoteIntent> {
    private val _uiState = MutableStateFlow(VoteUiState())
    override val uiState = _uiState.asStateFlow()

    init {
        loadVote()
    }

    override fun onIntent(intent: VoteIntent) {
        when (intent) {
            is VoteIntent.Upvote -> {
                createLocalVote(
                    vote = VoteRecord(id = intent.id)
                )
                loadVote()
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

            val localVotes = allLocalVotesUseCase().map { vote -> vote.id }

            singleVoteUseCase(exclude = localVotes)
                .onSuccess { vote ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            voteStatus = VoteStatus.Success(vote = vote)
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            voteStatus = VoteStatus.Failure(
                                message = exception.message ?: "Unknown error"
                            )
                        )
                    }
                }
        }
    }

    private fun createLocalVote(vote: VoteRecord) {
        viewModelScope.launch {
            createLocalVoteUseCase(vote = vote)
        }
    }
}
