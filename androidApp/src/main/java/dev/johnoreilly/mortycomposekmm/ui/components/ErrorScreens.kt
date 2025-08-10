package dev.johnoreilly.mortycomposekmm.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.johnoreilly.mortycomposekmm.ui.theme.SchwiftyRed

/**
 * A portal-themed error screen with retry functionality
 */
@Composable
fun PortalErrorScreen(
    message: String = "Something went wrong",
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Error portal animation
        ErrorPortalAnimation()
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Error message
        Text(
            text = message,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Retry button with portal-themed styling
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Retry",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Try Again",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * A smaller error indicator for use in lists
 */
@Composable
fun SmallErrorIndicator(
    message: String = "Failed to load",
    onRetry: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Error message
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.weight(1f)
        )
        
        // Small retry button
        IconButton(
            onClick = onRetry,
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Retry",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

/**
 * A portal-themed error animation
 */
@Composable
private fun ErrorPortalAnimation() {
    // Create animations
    val infiniteTransition = rememberInfiniteTransition()
    
    // Rotation animation
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing)
        )
    )
    
    // Pulsating animation
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    Box(
        modifier = Modifier
            .size(120.dp)
            .rotate(rotation),
        contentAlignment = Alignment.Center
    ) {
        // Outer error portal
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            SchwiftyRed.copy(alpha = 0.7f),
                            SchwiftyRed.copy(alpha = 0.3f)
                        )
                    )
                )
        )
        
        // Inner error portal with X shape
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            SchwiftyRed,
                            SchwiftyRed.copy(alpha = 0.5f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            // X mark
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .rotate(45f)
                    .background(Color.White)
                    .width(6.dp)
            )
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .rotate(-45f)
                    .background(Color.White)
                    .width(6.dp)
            )
        }
    }
}