package com.kasirkeren.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kasirkeren.app.ui.navigation.KasirApp
import com.kasirkeren.app.ui.theme.KasirKerenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KasirKerenTheme {
                KasirApp()
            }
        }
    }
}
