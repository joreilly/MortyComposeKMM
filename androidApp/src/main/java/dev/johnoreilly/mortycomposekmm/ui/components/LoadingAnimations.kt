package dev.johnoreilly.mortycomposekmm.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.johnoreilly.mortycomposekmm.ui.theme.PortalGreen
import dev.johnoreilly.mortycomposekmm.ui.theme.PortalSwirl

/**
 * A portal-themed loading animation for Rick and Morty app
 */
@Composable
fun PortalLoadingAnimation(
    modifier: Modifier = Modifier,
    size: Float = 200f,
    showText: Boolean = true
) {
    // Create pulsating animation
    val infiniteTransition = rememberInfiniteTransition()
    
    // Scale animation for the outer portal
    val outerScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    // Scale animation for the inner portal
    val innerScale by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    // Rotation animation
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing)
        )
    )
    
    Box(
        modifier = modifier.size(size.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer portal swirl
        Box(
            modifier = Modifier
                .size((size * 0.8f).dp)
                .scale(outerScale)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            PortalGreen,
                            PortalSwirl.copy(alpha = 0.7f),
                            PortalGreen.copy(alpha = 0.3f)
                        )
                    ),
                    shape = CircleShape
                )
        )
        
        // Inner portal swirl
        Box(
            modifier = Modifier
                .size((size * 0.6f).dp)
                .scale(innerScale)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            PortalSwirl,
                            PortalGreen,
                            PortalSwirl.copy(alpha = 0.5f)
                        )
                    ),
                    shape = CircleShape
                )
        )
        
        // Loading text
        if (showText) {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(top = (size * 0.9f).dp)
            )
        }
    }
}

/**
 * A smaller version of the portal loading animation for use in lists
 */
@Composable
fun SmallPortalLoadingAnimation() {
    PortalLoadingAnimation(
        size = 60f,
        showText = false
    )
}