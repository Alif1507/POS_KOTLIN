package com.kasirkeren.app.ui.items

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kasirkeren.app.data.model.CsvImportSummaryUi
import com.kasirkeren.app.data.model.ItemUi
import com.kasirkeren.app.ui.components.EmptyState
import com.kasirkeren.app.ui.components.PopupModal
import com.kasirkeren.app.ui.components.stockBadge
import com.kasirkeren.app.ui.theme.TextSecondary
import com.kasirkeren.app.ui.toRupiah

@Composable
fun ItemsScreen(viewModel: ItemsViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    var showForm by remember { mutableStateOf(false) }
    var editingItem by remember { mutableStateOf<ItemUi?>(null) }

    val csvLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri == null) return@rememberLauncherForActivityResult
        val bytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
        if (bytes == null) {
            return@rememberLauncherForActivityResult
        }
        val fileName = context.contentResolver.resolveFileName(uri)
        viewModel.importCsv(fileName = fileName, fileBytes = bytes)
    }

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
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = { csvLauncher.launch("text/*") }) {
                    Icon(Icons.Outlined.FileUpload, contentDescription = null)
                    Text("CSV")
                }
                Button(onClick = {
                    editingItem = null
                    showForm = true
                }) {
                    Icon(Icons.Outlined.Add, contentDescription = null)
                    Text("Tambah")
                }
            }

            if (uiState.loading && uiState.items.isEmpty()) {
                Text("Memuat item...", style = MaterialTheme.typography.bodyMedium)
            } else if (uiState.items.isEmpty()) {
                EmptyState(
                    title = "Inventaris kosong",
                    description = "Belum ada item. Tambahkan item pertama Anda.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp)
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(uiState.items, key = { it.id }) { item ->
                        ItemRow(
                            item = item,
                            onEdit = {
                                editingItem = item
                                showForm = true
                            },
                            onDelete = { viewModel.deleteItem(item.id) }
                        )
                    }
                }
            }
        }
    }

    ItemFormModal(
        visible = showForm,
        initialItem = editingItem,
        onDismiss = { showForm = false },
        onSubmit = {
            viewModel.saveItem(it)
            showForm = false
        }
    )

    CsvImportResultModal(
        summary = uiState.importSummary,
        onDismiss = viewModel::clearImportSummary
    )
}

@Composable
private fun ItemRow(
    item: ItemUi,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val (stockLabel, stockBg, stockFg) = stockBadge(item.stock)

    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(item.name, style = MaterialTheme.typography.titleMedium)
                Text(item.sellPrice.toRupiah(), color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = stockLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = stockFg,
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .background(stockBg, RoundedCornerShape(999.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Outlined.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Outlined.DeleteOutline, contentDescription = "Hapus")
                }
            }
        }
    }
}

@Composable
private fun ItemFormModal(
    visible: Boolean,
    initialItem: ItemUi?,
    onDismiss: () -> Unit,
    onSubmit: (ItemFormUi) -> Unit
) {
    var name by remember(visible, initialItem) { mutableStateOf(initialItem?.name.orEmpty()) }
    var category by remember(visible, initialItem) { mutableStateOf(initialItem?.category.orEmpty()) }
    var sellPrice by remember(visible, initialItem) { mutableStateOf(initialItem?.sellPrice?.toString().orEmpty()) }
    var buyPrice by remember(visible, initialItem) { mutableStateOf(initialItem?.buyPrice?.toString().orEmpty()) }
    var stock by remember(visible, initialItem) { mutableStateOf(initialItem?.stock?.toString().orEmpty()) }

    val sell = sellPrice.toIntOrNull() ?: 0
    val buy = buyPrice.toIntOrNull() ?: 0
    val validPrice = sell >= buy

    PopupModal(visible = visible, onDismiss = onDismiss, sheetMode = true) {
        Text(
            text = if (initialItem == null) "Tambah Item" else "Edit Item",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nama") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Kategori") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            value = sellPrice,
            onValueChange = { sellPrice = it.filter(Char::isDigit) },
            label = { Text("Harga Jual") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = buyPrice,
            onValueChange = { buyPrice = it.filter(Char::isDigit) },
            label = { Text("Harga Beli") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = stock,
            onValueChange = { stock = it.filter(Char::isDigit) },
            label = { Text("Stok") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        if (!validPrice) {
            Text(
                text = "Harga jual tidak boleh kurang dari harga beli",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            Button(onClick = onDismiss, modifier = Modifier.weight(1f)) {
                Text("Batal")
            }
            Button(
                onClick = {
                    onSubmit(
                        ItemFormUi(
                            id = initialItem?.id,
                            name = name.trim(),
                            category = category.trim(),
                            sellPrice = sellPrice,
                            buyPrice = buyPrice,
                            stock = stock
                        )
                    )
                },
                enabled = name.isNotBlank() && category.isNotBlank() && validPrice,
                modifier = Modifier.weight(1f)
            ) {
                Text("Simpan")
            }
        }
    }
}

@Composable
private fun CsvImportResultModal(
    summary: CsvImportSummaryUi?,
    onDismiss: () -> Unit
) {
    PopupModal(visible = summary != null, onDismiss = onDismiss, sheetMode = true) {
        if (summary == null) return@PopupModal
        Text("Hasil Import CSV", style = MaterialTheme.typography.titleMedium)
        Text("Total baris: ${summary.totalRows}")
        Text("Berhasil diproses: ${summary.processed}")
        Text("Dibuat baru: ${summary.created}")
        Text("Diperbarui: ${summary.updated}")
        Text("Gagal: ${summary.failed}")
        summary.firstErrors.take(5).forEach {
            Text(text = "- $it", color = MaterialTheme.colorScheme.error)
        }
        Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
            Text("Tutup")
        }
    }
}

private fun ContentResolver.resolveFileName(uri: Uri): String {
    query(uri, null, null, null, null)?.use { cursor ->
        val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (index >= 0 && cursor.moveToFirst()) {
            return cursor.getString(index)
        }
    }
    return "items.csv"
}
