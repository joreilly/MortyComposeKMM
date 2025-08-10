package dev.johnoreilly.mortycomposekmm.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Custom colors for Rick and Morty theme
val RickBlue = Color(0xFF97CE4C)
val MortyYellow = Color(0xFFF0E14A)
val PortalGreen = Color(0xFF00B5CC)
val SpaceBlack = Color(0xFF202428)
val AlienGreen = Color(0xFF97CE4C)
val StatusAlive = Color(0xFF55CC44)
val StatusDead = Color(0xFFD63D2E)
val StatusUnknown = Color(0xFF9E9E9E)

// Additional Rick and Morty themed colors
val PortalSwirl = Color(0xFF39FF14)      // Bright portal green
val GarageWall = Color(0xFFE0E0E0)       // Rick's garage wall color
val MeeseeksBlue = Color(0xFF00B3E3)     // Mr. Meeseeks blue
val SchwiftyRed = Color(0xFFFF5252)      // Vibrant red
val PlumbusPink = Color(0xFFFFAEC9)      // Plumbus pink
val DimensionPurple = Color(0xFF9C27B0)  // Interdimensional purple

// Custom Typography for Rick and Morty theme
val MortyTypography = Typography(
    // Large titles for main screens
    headlineLarge = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        letterSpacing = (-0.5).sp
    ),
    // Character names
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        letterSpacing = 0.sp
    ),
    // Section titles
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        letterSpacing = 0.sp
    ),
    // Card titles
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        letterSpacing = 0.15.sp
    ),
    // Regular body text
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),
    // Secondary information
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    // Small details like episode air dates
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    ),
    // Labels and buttons
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp
    )
)

// Custom Shapes for Rick and Morty theme
val MortyShapes = Shapes(
    // Small components like chips, small buttons
    small = RoundedCornerShape(
        topStart = 4.dp,
        topEnd = 12.dp,
        bottomStart = 12.dp,
        bottomEnd = 4.dp
    ),
    // Medium components like cards, dialogs
    medium = RoundedCornerShape(
        topStart = 8.dp,
        topEnd = 16.dp,
        bottomStart = 16.dp,
        bottomEnd = 8.dp
    ),
    // Large components like bottom sheets, expanded cards
    large = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 24.dp,
        bottomStart = 24.dp,
        bottomEnd = 16.dp
    )
)

private val LightColorScheme = lightColorScheme(
    primary = PortalGreen,
    onPrimary = Color.White,
    primaryContainer = AlienGreen,
    onPrimaryContainer = Color.White,
    secondary = MortyYellow,
    onSecondary = SpaceBlack,
    secondaryContainer = MortyYellow.copy(alpha = 0.7f),
    onSecondaryContainer = SpaceBlack,
    tertiary = MeeseeksBlue,
    onTertiary = Color.White,
    tertiaryContainer = MeeseeksBlue.copy(alpha = 0.7f),
    onTertiaryContainer = Color.White,
    error = SchwiftyRed,
    errorContainer = SchwiftyRed.copy(alpha = 0.7f),
    background = GarageWall,
    onBackground = SpaceBlack,
    surface = Color.White,
    onSurface = SpaceBlack,
    surfaceVariant = Color(0xFFF0F0F0),
    onSurfaceVariant = SpaceBlack.copy(alpha = 0.7f),
    outline = PortalGreen.copy(alpha = 0.5f)
)

private val DarkColorScheme = darkColorScheme(
    primary = PortalSwirl,
    onPrimary = SpaceBlack,
    primaryContainer = PortalGreen,
    onPrimaryContainer = Color.White,
    secondary = MortyYellow,
    onSecondary = SpaceBlack,
    secondaryContainer = MortyYellow.copy(alpha = 0.5f),
    onSecondaryContainer = Color.White,
    tertiary = MeeseeksBlue,
    onTertiary = Color.White,
    tertiaryContainer = MeeseeksBlue.copy(alpha = 0.5f),
    onTertiaryContainer = Color.White,
    error = SchwiftyRed,
    errorContainer = SchwiftyRed.copy(alpha = 0.5f),
    background = SpaceBlack,
    onBackground = Color.White,
    surface = Color(0xFF202020),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF303030),
    onSurfaceVariant = Color.White.copy(alpha = 0.7f),
    outline = PortalSwirl.copy(alpha = 0.5f)
)

@Composable
fun MortyComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MortyTypography,
        shapes = MortyShapes,
        content = content
    )
}

// Extension function to get status color based on character status
fun ColorScheme.getStatusColor(status: String): Color {
    return when (status.lowercase()) {
        "alive" -> StatusAlive
        "dead" -> StatusDead
        else -> StatusUnknown
    }
}