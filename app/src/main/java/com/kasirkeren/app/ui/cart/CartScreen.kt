package com.kasirkeren.app.ui.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kasirkeren.app.ui.components.PopupModal
import com.kasirkeren.app.ui.pos.PosViewModel
import com.kasirkeren.app.ui.toRupiah
import com.kasirkeren.app.ui.theme.TextSecondary

@Composable
fun CartScreen(
    viewModel: PosViewModel,
    onBackToPos: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var showPaymentSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.messages.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Keranjang", style = MaterialTheme.typography.headlineMedium)

            if (uiState.cart.isEmpty()) {
                Text(
                    text = "Belum ada item di keranjang.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.cart, key = { it.item.id }) { line ->
                        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(line.item.name, style = MaterialTheme.typography.titleMedium)
                                    Text(line.subtotal.toRupiah(), style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = { viewModel.decrease(line.item.id) }) {
                                        Icon(Icons.Outlined.Remove, contentDescription = "Kurangi")
                                    }
                                    Text(line.qty.toString(), style = MaterialTheme.typography.titleMedium)
                                    IconButton(onClick = { viewModel.increase(line.item.id) }) {
                                        Icon(Icons.Outlined.Add, contentDescription = "Tambah")
                                    }
                                    IconButton(onClick = { viewModel.remove(line.item.id) }) {
                                        Icon(Icons.Outlined.DeleteOutline, contentDescription = "Hapus")
                                    }
                                }
                            }
                        }
                    }
                }

                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total", style = MaterialTheme.typography.titleMedium)
                        Text(uiState.grandTotal.toRupiah(), style = MaterialTheme.typography.titleMedium)
                    }
                }

                Button(
                    onClick = { showPaymentSheet = true },
                    enabled = !uiState.paymentLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Bayar Sekarang")
                }
            }

            Button(onClick = onBackToPos, modifier = Modifier.fillMaxWidth()) {
                Text("Kembali ke POS")
            }
        }
    }

    PopupModal(visible = showPaymentSheet, onDismiss = { showPaymentSheet = false }, sheetMode = true) {
        Text("Pilih Metode Pembayaran", style = MaterialTheme.typography.titleMedium)
        Button(
            onClick = {
                showPaymentSheet = false
                viewModel.checkoutCash()
            },
            enabled = !uiState.paymentLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tunai")
        }
        Button(
            onClick = {
                showPaymentSheet = false
                viewModel.checkoutMidtrans()
            },
            enabled = !uiState.paymentLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Midtrans")
        }
    }
}
