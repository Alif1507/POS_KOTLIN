package com.kasirkeren.app.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kasirkeren.app.data.model.DashboardSummaryUi
import com.kasirkeren.app.data.di.AppContainer
import com.kasirkeren.app.data.repository.AppResult
import com.kasirkeren.app.data.repository.DashboardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DashboardUiState(
    val loading: Boolean = true,
    val summary: DashboardSummaryUi? = null,
    val errorMessage: String? = null
)

class DashboardViewModel(
    private val repository: DashboardRepository = AppContainer.dashboardRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true, errorMessage = null)
            when (val result = repository.getDashboardSummary()) {
                is AppResult.Success -> {
                    _uiState.value = DashboardUiState(
                        loading = false,
                        summary = result.data,
                        errorMessage = null
                    )
                }

                is AppResult.Error -> {
                    _uiState.value = DashboardUiState(
                        loading = false,
                        summary = null,
                        errorMessage = result.message
                    )
                }
            }
        }
    }
}
