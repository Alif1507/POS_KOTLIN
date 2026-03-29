package com.kasirkeren.app.ui.pos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kasirkeren.app.data.model.CartLineUi
import com.kasirkeren.app.data.model.ItemUi
import com.kasirkeren.app.data.di.AppContainer
import com.kasirkeren.app.data.repository.AppResult
import com.kasirkeren.app.data.repository.CheckoutRepository
import com.kasirkeren.app.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PosUiState(
    val loading: Boolean = true,
    val paymentLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedCategory: String = "Semua",
    val items: List<ItemUi> = emptyList(),
    val cart: List<CartLineUi> = emptyList()
) {
    val totalQty: Int
        get() = cart.sumOf { it.qty }

    val grandTotal: Int
        get() = cart.sumOf { it.subtotal }

    val categories: List<String>
        get() = listOf("Semua") + items.map { it.category }.distinct()

    fun filteredItems(): List<ItemUi> {
        val byCategory = if (selectedCategory == "Semua") items else items.filter { it.category == selectedCategory }
        if (searchQuery.isBlank()) return byCategory
        return byCategory.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }
}

sealed interface PosNavEvent {
    data class OpenMidtrans(val orderId: String, val redirectUrl: String) : PosNavEvent
    data object GoDashboard : PosNavEvent
}

class PosViewModel(
    private val itemRepository: ItemRepository = AppContainer.itemRepository,
    private val checkoutRepository: CheckoutRepository = AppContainer.checkoutRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PosUiState())
    val uiState: StateFlow<PosUiState> = _uiState.asStateFlow()

    private val _messages = MutableSharedFlow<String>()
    val messages: SharedFlow<String> = _messages.asSharedFlow()
    private val _navEvents = MutableSharedFlow<PosNavEvent>()
    val navEvents: SharedFlow<PosNavEvent> = _navEvents.asSharedFlow()

    init {
        refreshItems()
    }

    fun refreshItems() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            when (val result = itemRepository.getItems()) {
                is AppResult.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            loading = false,
                            items = result.data,
                            selectedCategory = if (state.selectedCategory in (listOf("Semua") + result.data.map { it.category }.distinct())) {
                                state.selectedCategory
                            } else {
                                "Semua"
                            }
                        )
                    }
                }

                is AppResult.Error -> {
                    _uiState.update { it.copy(loading = false) }
                    _messages.emit(result.message)
                }
            }
        }
    }

    fun updateSearch(value: String) {
        _uiState.update { it.copy(searchQuery = value) }
    }

    fun selectCategory(value: String) {
        _uiState.update { it.copy(selectedCategory = value) }
    }

    fun addToCart(item: ItemUi) {
        val currentState = _uiState.value
        val existing = currentState.cart.find { it.item.id == item.id }
        val nextQty = (existing?.qty ?: 0) + 1
        if (nextQty > item.stock) {
            viewModelScope.launch {
                _messages.emit("Stok ${item.name} tidak mencukupi")
            }
            return
        }

        val nextCart = if (existing == null) {
            currentState.cart + CartLineUi(item = item, qty = 1)
        } else {
            currentState.cart.map { line ->
                if (line.item.id == item.id) line.copy(qty = line.qty + 1) else line
            }
        }
        _uiState.update { it.copy(cart = nextCart) }
    }

    fun increase(itemId: Int) {
        val item = _uiState.value.items.firstOrNull { it.id == itemId } ?: return
        addToCart(item)
    }

    fun decrease(itemId: Int) {
        val next = _uiState.value.cart.mapNotNull { line ->
            if (line.item.id != itemId) {
                line
            } else {
                val qty = line.qty - 1
                if (qty <= 0) null else line.copy(qty = qty)
            }
        }
        _uiState.update { it.copy(cart = next) }
    }

    fun remove(itemId: Int) {
        _uiState.update { state ->
            state.copy(cart = state.cart.filterNot { it.item.id == itemId })
        }
    }

    fun checkoutCash() {
        viewModelScope.launch {
            if (_uiState.value.cart.isEmpty()) {
                _messages.emit("Keranjang masih kosong")
                return@launch
            }
            _uiState.update { it.copy(paymentLoading = true) }
            when (val result = checkoutRepository.checkoutCash(_uiState.value.cart)) {
                is AppResult.Success -> {
                    _messages.emit("Pembayaran tunai sukses (${result.data})")
                    _uiState.update { it.copy(cart = emptyList(), paymentLoading = false) }
                    refreshItems()
                    _navEvents.emit(PosNavEvent.GoDashboard)
                }

                is AppResult.Error -> {
                    _uiState.update { it.copy(paymentLoading = false) }
                    _messages.emit(result.message)
                }
            }
        }
    }

    fun checkoutMidtrans() {
        viewModelScope.launch {
            if (_uiState.value.cart.isEmpty()) {
                _messages.emit("Keranjang masih kosong")
                return@launch
            }
            _uiState.update { it.copy(paymentLoading = true) }
            when (val result = checkoutRepository.checkoutMidtrans(_uiState.value.cart)) {
                is AppResult.Success -> {
                    _uiState.update { it.copy(paymentLoading = false) }
                    _navEvents.emit(
                        PosNavEvent.OpenMidtrans(
                            orderId = result.data.orderId,
                            redirectUrl = result.data.redirectUrl
                        )
                    )
                }

                is AppResult.Error -> {
                    _uiState.update { it.copy(paymentLoading = false) }
                    _messages.emit(result.message)
                }
            }
        }
    }

    fun confirmMidtransPayment(orderId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(paymentLoading = true) }
            when (val result = checkoutRepository.confirmMidtrans(orderId)) {
                is AppResult.Success -> {
                    _messages.emit("Pembayaran Midtrans sukses ($orderId)")
                    _uiState.update { it.copy(cart = emptyList(), paymentLoading = false) }
                    refreshItems()
                    _navEvents.emit(PosNavEvent.GoDashboard)
                }

                is AppResult.Error -> {
                    _uiState.update { it.copy(paymentLoading = false) }
                    _messages.emit(result.message)
                }
            }
        }
    }

    fun markMidtransCancelled() {
        viewModelScope.launch {
            _messages.emit("Pembayaran Midtrans dibatalkan atau belum selesai")
        }
    }
}
