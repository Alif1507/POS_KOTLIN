package com.kasirkeren.app.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kasirkeren.app.ui.navigation.AppRoute
import com.kasirkeren.app.ui.theme.IndigoDeep
import com.kasirkeren.app.ui.theme.IndigoLight
import com.kasirkeren.app.ui.theme.TextSecondary

@Composable
fun BottomNavBar(
    currentRoute: String?,
    onNavigate: (AppRoute) -> Unit
) {
    NavigationBar(
        containerColor = Color.White.copy(alpha = 0.92f)
    ) {
        AppRoute.bottomNavItems.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item) },
                icon = { Icon(imageVector = item.icon!!, contentDescription = item.title) },
                label = { Text(text = item.title) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = IndigoDeep,
                    selectedTextColor = IndigoDeep,
                    indicatorColor = IndigoLight,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary
                )
            )
        }
    }
}
