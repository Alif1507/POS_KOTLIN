package com.kasirkeren.app.ui.pos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kasirkeren.app.ui.components.ItemCard
import com.kasirkeren.app.ui.toRupiah
import com.kasirkeren.app.ui.theme.IndigoDeep

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PosScreen(
    viewModel: PosViewModel,
    onOpenCart: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.messages.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = viewModel::updateSearch,
                label = { Text("Cari item") },
                modifier = Modifier.fillMaxWidth()
            )

            androidx.compose.foundation.layout.FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                uiState.categories.forEach { category ->
                    val selected = category == uiState.selectedCategory
                    AssistChip(
                        onClick = { viewModel.selectCategory(category) },
                        label = { Text(category) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (selected) IndigoDeep.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface
                        )
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 96.dp),
                modifier = Modifier.weight(1f)
            ) {
                if (uiState.loading) {
                    item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                        Text("Memuat item...", style = MaterialTheme.typography.bodyMedium)
                    }
                } else if (uiState.filteredItems().isEmpty()) {
                    item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Tidak ada item yang cocok.", style = MaterialTheme.typography.bodyMedium)
                            TextButton(onClick = viewModel::refreshItems) {
                                Text("Muat Ulang")
                            }
                        }
                    }
                } else {
                    items(uiState.filteredItems(), key = { it.id }) { item ->
                        ItemCard(item = item, onAddToCart = { viewModel.addToCart(item) })
                    }
                }
            }
        }

        if (uiState.cart.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = onOpenCart,
                colors = CardDefaults.cardColors(containerColor = IndigoDeep)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "${uiState.totalQty} item di keranjang",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Lihat Keranjang - ${uiState.grandTotal.toRupiah()}",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
