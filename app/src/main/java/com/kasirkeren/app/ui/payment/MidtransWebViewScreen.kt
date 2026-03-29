package com.kasirkeren.app.ui.payment

import android.annotation.SuppressLint
import android.net.Uri
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kasirkeren.app.ui.pos.PosViewModel

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun MidtransWebViewScreen(
    viewModel: PosViewModel,
    orderId: String,
    redirectUrl: String,
    onClose: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var pageLoading by remember { mutableStateOf(true) }
    var handled by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.messages.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    fun inspectUrl(url: String) {
        if (handled) return
        val uri = runCatching { Uri.parse(url) }.getOrNull() ?: return
        val status = uri.getQueryParameter("transaction_status")?.lowercase()
        val lowered = url.lowercase()

        val success = status in setOf("settlement", "capture", "success") ||
            lowered.contains("payment/success")
        val failed = status in setOf("deny", "cancel", "expire", "failure") ||
            lowered.contains("payment/failed") ||
            lowered.contains("status=failed")

        when {
            success -> {
                handled = true
                viewModel.confirmMidtransPayment(orderId)
            }

            failed -> {
                handled = true
                viewModel.markMidtransCancelled()
                onClose()
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (pageLoading || uiState.paymentLoading) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    CircularProgressIndicator()
                }
            }

            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        webChromeClient = WebChromeClient()
                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                                val nextUrl = request?.url?.toString().orEmpty()
                                inspectUrl(nextUrl)
                                return false
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                pageLoading = false
                                inspectUrl(url.orEmpty())
                            }
                        }
                        loadUrl(redirectUrl)
                    }
                }
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "ORDER: $orderId",
                    style = MaterialTheme.typography.labelSmall
                )
                Button(
                    onClick = { viewModel.confirmMidtransPayment(orderId) },
                    enabled = !uiState.paymentLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Saya Sudah Bayar")
                }
                Button(
                    onClick = {
                        viewModel.markMidtransCancelled()
                        onClose()
                    },
                    enabled = !uiState.paymentLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Batalkan")
                }
            }
        }
    }
}
