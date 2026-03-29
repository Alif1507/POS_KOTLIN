package com.kasirkeren.app.ui.navigation

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Scaffold
import androidx.navigation.NavType
import com.kasirkeren.app.ui.cart.CartScreen
import com.kasirkeren.app.ui.components.BottomNavBar
import com.kasirkeren.app.ui.components.KasirTopBar
import com.kasirkeren.app.ui.dashboard.DashboardScreen
import com.kasirkeren.app.ui.dashboard.DashboardViewModel
import com.kasirkeren.app.ui.history.HistoryScreen
import com.kasirkeren.app.ui.history.HistoryViewModel
import com.kasirkeren.app.ui.items.ItemsScreen
import com.kasirkeren.app.ui.items.ItemsViewModel
import com.kasirkeren.app.ui.payment.MidtransWebViewScreen
import com.kasirkeren.app.ui.pos.PosNavEvent
import com.kasirkeren.app.ui.pos.PosScreen
import com.kasirkeren.app.ui.pos.PosViewModel
import androidx.navigation.navArgument

@Composable
fun KasirApp() {
    val dashboardViewModel: DashboardViewModel = viewModel()
    val posViewModel: PosViewModel = viewModel()
    val itemsViewModel: ItemsViewModel = viewModel()
    val historyViewModel: HistoryViewModel = viewModel()

    val navController = rememberNavController()
    val currentEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentEntry?.destination?.route
    val current = AppRoute.fromRoute(currentRoute)

    LaunchedEffect(Unit) {
        posViewModel.navEvents.collect { event ->
            when (event) {
                is PosNavEvent.GoDashboard -> {
                    navController.navigate(AppRoute.Dashboard.route) {
                        popUpTo(AppRoute.Cart.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }

                is PosNavEvent.OpenMidtrans -> {
                    navController.navigate(AppRoute.paymentRoute(event.orderId, event.redirectUrl))
                }
            }
        }
    }

    Scaffold(
        topBar = {
            KasirTopBar(
                title = current.title,
                subtitle = current.subtitle,
                onNotificationClick = {}
            )
        },
        bottomBar = {
            if (current.inBottomNav) {
                BottomNavBar(currentRoute = currentRoute) { route ->
                    navController.navigate(route.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppRoute.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppRoute.Dashboard.route) {
                DashboardScreen(dashboardViewModel)
            }

            composable(AppRoute.Pos.route) {
                PosScreen(viewModel = posViewModel) {
                    navController.navigate(AppRoute.Cart.route)
                }
            }

            composable(AppRoute.Cart.route) {
                CartScreen(viewModel = posViewModel) {
                    navController.navigate(AppRoute.Pos.route)
                }
            }

            composable(
                route = AppRoute.paymentRoutePattern,
                arguments = listOf(
                    navArgument("orderId") { type = NavType.StringType },
                    navArgument("redirectUrl") { type = NavType.StringType }
                )
            ) { backStack ->
                val orderId = backStack.arguments?.getString("orderId").orEmpty()
                val redirectUrl = Uri.decode(backStack.arguments?.getString("redirectUrl").orEmpty())
                MidtransWebViewScreen(
                    viewModel = posViewModel,
                    orderId = orderId,
                    redirectUrl = redirectUrl,
                    onClose = { navController.popBackStack() }
                )
            }

            composable(AppRoute.Items.route) {
                ItemsScreen(viewModel = itemsViewModel)
            }

            composable(AppRoute.History.route) {
                HistoryScreen(viewModel = historyViewModel)
            }
        }
    }
}
