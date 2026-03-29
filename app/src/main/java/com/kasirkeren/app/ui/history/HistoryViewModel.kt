package com.kasirkeren.app.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kasirkeren.app.data.di.AppContainer
import com.kasirkeren.app.data.repository.AppResult
import com.kasirkeren.app.data.repository.TransactionRepository
import com.kasirkeren.app.data.model.TransactionUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HistoryUiState(
    val loading: Boolean = true,
    val errorMessage: String? = null,
    val transactions: List<TransactionUi> = emptyList()
)

class HistoryViewModel(
    private val repository: TransactionRepository = AppContainer.transactionRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true, errorMessage = null)
            when (val result = repository.getTransactions()) {
                is AppResult.Success -> {
                    _uiState.value = HistoryUiState(
                        loading = false,
                        errorMessage = null,
                        transactions = result.data
                    )
                }

                is AppResult.Error -> {
                    _uiState.value = HistoryUiState(
                        loading = false,
                        errorMessage = result.message,
                        transactions = emptyList()
                    )
                }
            }
        }
    }
}
