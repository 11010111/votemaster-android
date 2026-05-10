package de.multiplebytes.votemaster.presentation.feature.credit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.multiplebytes.votemaster.domain.usecase.credit.CreditUseCase
import de.multiplebytes.votemaster.domain.usecase.credit.UpdateCreditUseCase
import de.multiplebytes.votemaster.presentation.common.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CreditViewModel(
    creditUseCase: CreditUseCase,
    private val updateCreditUseCase: UpdateCreditUseCase
) : ViewModel(), BaseViewModel<CreditUiState, CreditIntent> {
    override val uiState: StateFlow<CreditUiState> = creditUseCase()
        .map { credit ->
            CreditUiState(credits = credit.count)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CreditUiState()
        )

    override fun onIntent(intent: CreditIntent) {
        when (intent) {
            is CreditIntent.Increment -> {
                increment(count = intent.count)
            }
        }
    }

    private fun increment(count: Int) {
        viewModelScope.launch {
            updateCreditUseCase(count = uiState.value.credits + count)
        }
    }
}
