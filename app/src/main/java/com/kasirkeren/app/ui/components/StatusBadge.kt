package com.kasirkeren.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kasirkeren.app.ui.theme.AmberWarning
import com.kasirkeren.app.ui.theme.AmberWarningBg
import com.kasirkeren.app.ui.theme.GreenSuccess
import com.kasirkeren.app.ui.theme.GreenSuccessBg
import com.kasirkeren.app.ui.theme.RedError
import com.kasirkeren.app.ui.theme.RedErrorBg

@Composable
fun StatusBadge(status: String, modifier: Modifier = Modifier) {
    val normalized = status.lowercase()
    val (label, bg, fg) = when (normalized) {
        "success" -> Triple("Sukses", GreenSuccessBg, GreenSuccess)
        "pending" -> Triple("Pending", AmberWarningBg, AmberWarning)
        else -> Triple("Gagal", RedErrorBg, RedError)
    }

    Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        color = fg,
        modifier = modifier
            .background(bg, RoundedCornerShape(999.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}

fun stockBadge(stock: Int): Triple<String, Color, Color> {
    return when {
        stock <= 0 -> Triple("Habis", RedErrorBg, RedError)
        stock <= 5 -> Triple("Stok Rendah", AmberWarningBg, AmberWarning)
        else -> Triple(stock.toString(), GreenSuccessBg, GreenSuccess)
    }
}
