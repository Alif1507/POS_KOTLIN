package com.kasirkeren.app.ui.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kasirkeren.app.data.model.CsvImportSummaryUi
import com.kasirkeren.app.data.model.ItemUi
import com.kasirkeren.app.data.di.AppContainer
import com.kasirkeren.app.data.repository.AppResult
import com.kasirkeren.app.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ItemFormUi(
    val id: Int? = null,
    val name: String = "",
    val category: String = "",
    val sellPrice: String = "",
    val buyPrice: String = "",
    val stock: String = ""
)

data class ItemsUiState(
    val loading: Boolean = true,
    val items: List<ItemUi> = emptyList(),
    val importSummary: CsvImportSummaryUi? = null
)

class ItemsViewModel(
    private val repository: ItemRepository = AppContainer.itemRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ItemsUiState())
    val uiState: StateFlow<ItemsUiState> = _uiState.asStateFlow()
    private val _messages = MutableSharedFlow<String>()
    val messages: SharedFlow<String> = _messages.asSharedFlow()

    init {
        refreshItems()
    }

    fun refreshItems() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            when (val result = repository.getItems()) {
                is AppResult.Success -> {
                    _uiState.update { state ->
                        state.copy(loading = false, items = result.data)
                    }
                }

                is AppResult.Error -> {
                    _uiState.update { it.copy(loading = false) }
                    _messages.emit(result.message)
                }
            }
        }
    }

    fun saveItem(form: ItemFormUi) {
        viewModelScope.launch {
            val sell = form.sellPrice.toIntOrNull() ?: 0
            val buy = form.buyPrice.toIntOrNull() ?: 0
            val stock = form.stock.toIntOrNull() ?: 0
            when (
                val result = repository.saveItem(
                    id = form.id,
                    name = form.name,
                    category = form.category,
                    sellPrice = sell,
                    buyPrice = buy,
                    stock = stock
                )
            ) {
                is AppResult.Success -> {
                    _messages.emit("Item berhasil disimpan")
                    refreshItems()
                }

                is AppResult.Error -> {
                    _messages.emit(result.message)
                }
            }
        }
    }

    fun deleteItem(itemId: Int) {
        viewModelScope.launch {
            when (val result = repository.deleteItem(itemId)) {
                is AppResult.Success -> {
                    _messages.emit("Item berhasil dihapus")
                    refreshItems()
                }

                is AppResult.Error -> {
                    _messages.emit(result.message)
                }
            }
        }
    }

    fun importCsv(fileName: String, fileBytes: ByteArray) {
        viewModelScope.launch {
            when (val result = repository.importCsv(fileName, fileBytes)) {
                is AppResult.Success -> {
                    _uiState.update { it.copy(importSummary = result.data) }
                    _messages.emit("Import CSV selesai")
                    refreshItems()
                }

                is AppResult.Error -> {
                    _messages.emit(result.message)
                }
            }
        }
    }

    fun clearImportSummary() {
        _uiState.update { it.copy(importSummary = null) }
    }
}
