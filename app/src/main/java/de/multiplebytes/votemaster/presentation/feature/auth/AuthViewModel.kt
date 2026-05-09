package de.multiplebytes.votemaster.presentation.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.multiplebytes.votemaster.domain.usecase.auth.SessionStatusUseCase
import de.multiplebytes.votemaster.domain.usecase.auth.SignInUseCase
import de.multiplebytes.votemaster.domain.usecase.auth.SignOutUseCase
import de.multiplebytes.votemaster.domain.usecase.auth.SignUpUseCase
import de.multiplebytes.votemaster.presentation.common.BaseViewModel
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    sessionStatusUseCase: SessionStatusUseCase,
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel(), BaseViewModel<AuthUiState, AuthIntent> {
    private val _uiState = MutableStateFlow(AuthUiState())
    override val uiState: StateFlow<AuthUiState> = combine(
        _uiState,
        sessionStatusUseCase()
    ) { uiState, sessionStatus ->
        uiState.copy(sessionStatus = sessionStatus)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AuthUiState()
        )

    override fun onIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.SignIn -> {
                signIn(email = intent.email, password = intent.password)
            }

            is AuthIntent.SignUp -> {
                signUp(email = intent.email, password = intent.password)
            }

            is AuthIntent.SignOut -> {
                signOut()
            }
        }
    }

    private fun signIn(email: String, password: String) {
        viewModelScope.launch {
            signInUseCase(email = email, password = password)
                .onSuccess {
                    _uiState.update { currentState ->
                        currentState.copy(errorMessage = null)
                    }
                }
                .onFailure { exception ->
                    if (exception is RestException) {
                        _uiState.update { currentState ->
                            currentState.copy(errorMessage = exception.error)
                        }
                    }
                }
        }
    }

    private fun signUp(email: String, password: String) {
        viewModelScope.launch {
            signUpUseCase(email = email, password = password)
                .onSuccess {
                    _uiState.update { currentState ->
                        currentState.copy(errorMessage = null)
                    }
                }
                .onFailure { exception ->
                    if (exception is RestException) {
                        _uiState.update { currentState ->
                            currentState.copy(errorMessage = exception.error)
                        }
                    }
                }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            signOutUseCase()
        }
    }
}
