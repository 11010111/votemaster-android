package de.multiplebytes.votemaster.presentation.feature.vote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.multiplebytes.votemaster.domain.usecase.profile.SingleProfileUseCase
import de.multiplebytes.votemaster.domain.usecase.vote.CreateVoteUseCase
import de.multiplebytes.votemaster.domain.usecase.vote.VotesUseCase
import de.multiplebytes.votemaster.presentation.common.BaseViewModel
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VoteViewModel(
    votesUseCase: VotesUseCase,
    private val singleProfileUseCase: SingleProfileUseCase,
    private val createVoteUseCase: CreateVoteUseCase
) : ViewModel(), BaseViewModel<VoteUiState, VoteIntent> {
    private val _uiState = MutableStateFlow(VoteUiState())

    @OptIn(ExperimentalCoroutinesApi::class)
    override val uiState: StateFlow<VoteUiState> = _uiState
        .flatMapLatest { state ->
            votesUseCase()
                .catch { emit(emptyList()) }
                .flatMapLatest { votes ->
                    flow {
                        emit(value = state.copy(voteStatus = VoteStatus.Loading))

                        val excludeIds = votes.map { it.profileId }

                        singleProfileUseCase(exclude = excludeIds)
                            .onSuccess { profile ->
                                emit(
                                    value = state.copy(
                                        voteStatus = VoteStatus.Success(profile = profile)
                                    )
                                )
                            }
                            .onFailure { exception ->
                                emit(
                                    value = state.copy(
                                        voteStatus = VoteStatus.Failure(
                                            message = when (exception) {
                                                is RestException -> "Service unavailable"
                                                else -> "Connection failed"
                                            }
                                        )
                                    )
                                )
                            }
                    }
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = VoteUiState()
        )

    override fun onIntent(intent: VoteIntent) {
        when (intent) {
            is VoteIntent.Upvote -> {
                create(profileId = intent.id)
            }

            is VoteIntent.Refresh -> {
                refresh()
            }
        }
    }

    private fun create(profileId: String) {
        viewModelScope.launch {
            createVoteUseCase(
                profileId = profileId
            )
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    refresh = System.currentTimeMillis()
                )
            }
        }
    }
}
