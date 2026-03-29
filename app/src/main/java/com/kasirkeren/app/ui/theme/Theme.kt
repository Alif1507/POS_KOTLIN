package com.kasirkeren.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val KasirColorScheme = lightColorScheme(
    primary = IndigoDeep,
    onPrimary = SurfaceWhite,
    primaryContainer = IndigoLight,
    onPrimaryContainer = IndigoDeep,
    secondary = IndigoMedium,
    onSecondary = SurfaceWhite,
    background = SlateBg,
    onBackground = TextPrimary,
    surface = SurfaceWhite,
    onSurface = TextPrimary,
    error = RedError,
    onError = SurfaceWhite
)

@Composable
fun KasirKerenTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = KasirColorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
