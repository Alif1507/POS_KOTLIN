package com.kasirkeren.app.ui.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kasirkeren.app.data.model.TransactionUi
import com.kasirkeren.app.ui.components.EmptyState
import com.kasirkeren.app.ui.components.PopupModal
import com.kasirkeren.app.ui.components.StatusBadge
import com.kasirkeren.app.ui.toClockText
import com.kasirkeren.app.ui.toDateGroupLabel
import com.kasirkeren.app.ui.toRupiah
import com.kasirkeren.app.ui.theme.TextSecondary

@Composable
fun HistoryScreen(viewModel: HistoryViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selected by remember { mutableStateOf<TransactionUi?>(null) }

    if (uiState.loading) {
        Text(
            text = "Memuat transaksi...",
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        )
        return
    }

    if (uiState.errorMessage != null) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(uiState.errorMessage!!, color = MaterialTheme.colorScheme.error)
            Button(onClick = viewModel::refresh) { Text("Coba Lagi") }
        }
        return
    }

    if (uiState.transactions.isEmpty()) {
        EmptyState(
            title = "Belum ada transaksi",
            description = "Transaksi yang sudah dibuat akan muncul di sini.",
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        )
        return
    }

    val grouped = remember(uiState.transactions) {
        uiState.transactions.groupBy { it.createdAt.toDateGroupLabel() }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        grouped.forEach { (label, transactions) ->
            item {
                Text(text = label, style = MaterialTheme.typography.titleMedium)
            }
            items(transactions, key = { it.orderId }) { tx ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selected = tx }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(3.dp), modifier = Modifier.weight(1f)) {
                            Text(tx.orderId, style = MaterialTheme.typography.titleMedium)
                            Text(tx.paymentType, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                            Text(tx.createdAt.toClockText(), style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                        }
                        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(5.dp)) {
                            StatusBadge(tx.status)
                            Text(tx.total.toRupiah(), style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
            }
        }
    }

    HistoryDetailModal(transaction = selected, onDismiss = { selected = null })
}

@Composable
private fun HistoryDetailModal(
    transaction: TransactionUi?,
    onDismiss: () -> Unit
) {
    PopupModal(visible = transaction != null, onDismiss = onDismiss, sheetMode = false) {
        if (transaction == null) return@PopupModal

        Text(text = transaction.orderId, style = MaterialTheme.typography.titleMedium)
        StatusBadge(transaction.status)
        Text(text = "Pembayaran: ${transaction.paymentType}")

        transaction.items.forEach { item ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("${item.name} x${item.qty}")
                Text(item.subtotal.toRupiah())
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Total", style = MaterialTheme.typography.titleMedium)
            Text(transaction.total.toRupiah(), style = MaterialTheme.typography.titleMedium)
        }

        Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
            Text("Tutup")
        }
    }
}
