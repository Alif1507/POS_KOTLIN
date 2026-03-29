package com.kasirkeren.app.ui.navigation

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.PointOfSale
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppRoute(
    val route: String,
    val title: String,
    val inBottomNav: Boolean = false,
    val icon: ImageVector? = null,
    val subtitle: String? = null
) {
    data object Dashboard : AppRoute(
        "dashboard",
        "Dashboard",
        inBottomNav = true,
        icon = Icons.Outlined.Dashboard,
        subtitle = "RINGKASAN HARI INI"
    )

    data object Pos : AppRoute(
        "pos",
        "Kasir",
        inBottomNav = true,
        icon = Icons.Outlined.PointOfSale,
        subtitle = "PENJUALAN LANGSUNG"
    )

    data object Items : AppRoute(
        "items",
        "Inventaris",
        inBottomNav = true,
        icon = Icons.Outlined.Inventory2,
        subtitle = "MANAJEMEN STOK"
    )

    data object History : AppRoute(
        "history",
        "History",
        inBottomNav = true,
        icon = Icons.Outlined.History,
        subtitle = "CATATAN OPERASIONAL"
    )

    data object Cart : AppRoute("cart", "Keranjang")
    data object Payment : AppRoute("payment", "Pembayaran")

    companion object {
        val paymentRoutePattern = "payment/{orderId}?redirectUrl={redirectUrl}"

        val all: List<AppRoute> = listOf(Dashboard, Pos, Items, History, Cart, Payment)
        val bottomNavItems: List<AppRoute> = all.filter { it.inBottomNav }

        fun paymentRoute(orderId: String, redirectUrl: String): String {
            return "payment/${Uri.encode(orderId)}?redirectUrl=${Uri.encode(redirectUrl)}"
        }

        fun fromRoute(route: String?): AppRoute {
            if (route == null) return Dashboard
            if (route.startsWith(Payment.route)) return Payment
            return all.firstOrNull { it.route == route } ?: Dashboard
        }
    }
}
