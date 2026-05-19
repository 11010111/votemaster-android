package de.multiplebytes.votemaster.presentation.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.multiplebytes.votemaster.domain.usecase.profile.UserProfileUseCase
import de.multiplebytes.votemaster.presentation.common.BaseViewModel
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userProfileUseCase: UserProfileUseCase
) : ViewModel(), BaseViewModel<ProfileUiState, ProfileIntent> {
    private val _uiState = MutableStateFlow(ProfileUiState())
    override val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        fetchUserProfile()
    }

    override fun onIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.Edit -> {

            }

            is ProfileIntent.Retry -> {
                fetchUserProfile()
            }
        }
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    profileStatus = ProfileStatus.Loading
                )
            }

            userProfileUseCase()
                .onSuccess { profile ->
                    profile?.let {
                        _uiState.update { currentState ->
                            currentState.copy(
                                profileStatus = ProfileStatus.Success(profile = it)
                            )
                        }
                    }
                }
                .onFailure { exception ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            profileStatus = ProfileStatus.Failure(
                                message = when (exception) {
                                    is RestException -> "Service unavailable"
                                    else -> "Connection failed"
                                }
                            )
                        )
                    }
                }
        }
    }
}
