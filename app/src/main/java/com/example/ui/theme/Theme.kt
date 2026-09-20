package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
  primary = SaffronDarkPrimary,
  onPrimary = OnSaffronPrimaryFixed,
  primaryContainer = SaffronDarkPrimaryContainer,
  onPrimaryContainer = SaffronPrimaryFixed,
  secondary = EmeraldDarkSecondary,
  onSecondary = OnEmeraldSecondaryFixed,
  secondaryContainer = EmeraldSecondary,
  onSecondaryContainer = EmeraldSecondaryFixed,
  tertiary = RoyalBlueDarkTertiary,
  onTertiary = OnRoyalBlueTertiaryContainer,
  tertiaryContainer = RoyalBlueTertiary,
  onTertiaryContainer = RoyalBlueTertiaryContainer,
  background = DarkSurface,
  onBackground = DarkOnSurface,
  surface = DarkSurface,
  onSurface = DarkOnSurface,
  surfaceVariant = DarkSurfaceContainerHigh,
  onSurfaceVariant = OutlineVariantClay,
  surfaceContainer = DarkSurfaceContainer,
  surfaceContainerLow = DarkSurfaceContainerLowest,
  surfaceContainerHigh = DarkSurfaceContainerHigh,
  surfaceContainerLowest = DarkSurfaceContainerLowest,
  outline = OutlineClay,
  outlineVariant = OutlineVariantClay,
  error = ErrorRed,
  onError = OnErrorRed,
  errorContainer = ErrorRedContainer
)

private val LightColorScheme = lightColorScheme(
  primary = SaffronPrimary,
  onPrimary = OnSaffronPrimary,
  primaryContainer = SaffronPrimaryContainer,
  onPrimaryContainer = OnSaffronPrimaryContainer,
  secondary = EmeraldSecondary,
  onSecondary = OnEmeraldSecondary,
  secondaryContainer = EmeraldSecondaryContainer,
  onSecondaryContainer = OnEmeraldSecondaryContainer,
  tertiary = RoyalBlueTertiary,
  onTertiary = OnRoyalBlueTertiary,
  tertiaryContainer = RoyalBlueTertiaryContainer,
  onTertiaryContainer = OnRoyalBlueTertiaryContainer,
  background = CardamomSurface,
  onBackground = OnCardamomSurface,
  surface = CardamomSurface,
  onSurface = OnCardamomSurface,
  surfaceVariant = CardamomSurfaceVariant,
  onSurfaceVariant = OnCardamomSurfaceVariant,
  surfaceContainer = CardamomSurfaceContainer,
  surfaceContainerLow = CardamomSurfaceContainerLow,
  surfaceContainerHigh = CardamomSurfaceContainerHigh,
  surfaceContainerLowest = CardamomSurfaceContainerLowest,
  outline = OutlineClay,
  outlineVariant = OutlineVariantClay,
  error = ErrorRed,
  onError = OnErrorRed,
  errorContainer = ErrorRedContainer
)

@Composable
fun VillageRushTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep intentional game palette
  content: @Composable () -> Unit,
) {
  val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      val context = LocalContext.current
      if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }
    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  VillageRushTheme(darkTheme, dynamicColor, content)
}

