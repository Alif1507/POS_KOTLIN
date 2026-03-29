package com.kasirkeren.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KasirTopBar(
    title: String,
    subtitle: String?,
    onRefreshClick: (() -> Unit)? = null,
    onNotificationClick: () -> Unit
) {
    TopAppBar(
        title = {
            Column {
                Text(text = title, style = MaterialTheme.typography.headlineMedium)
                if (!subtitle.isNullOrEmpty()) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        actions = {
            if (onRefreshClick != null) {
                IconButton(onClick = onRefreshClick) {
                    Icon(imageVector = Icons.Outlined.Refresh, contentDescription = "Refresh")
                }
            }
            IconButton(onClick = onNotificationClick) {
                Icon(imageVector = Icons.Outlined.NotificationsNone, contentDescription = "Notifikasi")
            }
        }
    )
}
