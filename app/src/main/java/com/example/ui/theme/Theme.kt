package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val WavelengthDarkColorScheme = darkColorScheme(
    primary = ElectricViolet,
    onPrimary = Color.White,
    primaryContainer = WavelengthSurfaceCard,
    onPrimaryContainer = CyberLavender,
    secondary = NeonCyanPulse,
    onSecondary = WavelengthObsidian,
    secondaryContainer = WavelengthSurfaceHover,
    onSecondaryContainer = NeonCyanPulse,
    tertiary = CoralAura,
    onTertiary = Color.White,
    background = WavelengthObsidian,
    onBackground = WavelengthTextPrimary,
    surface = WavelengthSurfaceDark,
    onSurface = WavelengthTextPrimary,
    surfaceVariant = WavelengthSurfaceCard,
    onSurfaceVariant = WavelengthTextSecondary,
    outline = WavelengthBorderSubtle,
    outlineVariant = WavelengthBorderHighlight
)

private val WavelengthLightColorScheme = lightColorScheme(
    primary = ElectricViolet,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEDE9FE),
    onPrimaryContainer = ElectricViolet,
    secondary = Color(0xFF00BFA5),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE0F2F1),
    onSecondaryContainer = Color(0xFF004D40),
    tertiary = CoralAura,
    onTertiary = Color.White,
    background = WavelengthLightBackground,
    onBackground = WavelengthLightTextPrimary,
    surface = WavelengthLightSurface,
    onSurface = WavelengthLightTextPrimary,
    surfaceVariant = Color(0xFFF1F3F9),
    onSurfaceVariant = WavelengthLightTextSecondary,
    outline = WavelengthLightBorder,
    outlineVariant = Color(0xFFCBD5E1)
)

@Composable
fun WavelengthTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep bespoke brand identity by default
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> WavelengthDarkColorScheme
        else -> WavelengthDarkColorScheme // Gen-Z prefers dark mode aesthetic, but light can be toggled
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
