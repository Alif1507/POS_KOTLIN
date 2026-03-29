package com.kasirkeren.app.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kasirkeren.app.ui.components.SkeletonLoader
import com.kasirkeren.app.ui.toRupiah
import com.kasirkeren.app.ui.theme.IndigoDeep
import com.kasirkeren.app.ui.theme.IndigoMedium
import com.kasirkeren.app.ui.theme.TextSecondary

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        item {
            if (uiState.loading) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.padding(18.dp)
                    ) {
                        SkeletonLoader()
                        SkeletonLoader(modifier = Modifier.height(28.dp).fillMaxWidth())
                    }
                }
            } else if (uiState.summary != null) {
                val summary = uiState.summary!!
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(Brush.linearGradient(listOf(IndigoDeep, IndigoMedium)))
                        .padding(20.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Pendapatan Hari Ini",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                        )
                        Text(
                            text = summary.todayRevenue.toRupiah(),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "Terlaris: ${summary.topItem}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                        )
                    }
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = uiState.errorMessage ?: "Gagal memuat data dashboard",
                            color = MaterialTheme.colorScheme.error
                        )
                        androidx.compose.material3.TextButton(onClick = viewModel::refresh) {
                            Text("Coba Lagi")
                        }
                    }
                }
            }
        }

        if (!uiState.loading && uiState.summary != null) {
            val summary = uiState.summary!!
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MetricCard("Transaksi", summary.totalTransactions.toString(), Modifier.weight(1f))
                    MetricCard("Rata-rata", summary.avgTransaction.toRupiah(), Modifier.weight(1f))
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("7 Hari Terakhir", style = MaterialTheme.typography.titleMedium)
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val maxAmount = summary.weeklyRevenue.maxOf { it.amount }.coerceAtLeast(1)
                            summary.weeklyRevenue.forEach { point ->
                                val heightRatio = point.amount.toFloat() / maxAmount.toFloat()
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Box(
                                        modifier = Modifier
                                            .size(width = 22.dp, height = (88.dp * heightRatio))
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(IndigoDeep)
                                    )
                                    Text(
                                        text = point.label,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextSecondary,
                                        modifier = Modifier.padding(top = 6.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(14.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            Text(text = value, style = MaterialTheme.typography.titleMedium)
        }
    }
}
