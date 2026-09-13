package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.NeonCyanPulse
import kotlin.math.PI
import kotlin.math.sin

/**
 * Animated dynamic waveform visualizer depicting harmonic mental resonance.
 */
@Composable
fun WaveformVisualizer(
    modifier: Modifier = Modifier,
    primaryColor: Color = ElectricViolet,
    secondaryColor: Color = NeonCyanPulse,
    speedFactor: Float = 1.0f
) {
    val infiniteTransition = rememberInfiniteTransition(label = "waveform_anim")

    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = (4000 / speedFactor).toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
    ) {
        val width = size.width
        val height = size.height
        val centerY = height / 2f

        // Wave 1: Primary Harmonic
        val path1 = Path()
        path1.moveTo(0f, centerY)
        val step = 4f
        var x = 0f
        while (x <= width) {
            val normalizedX = x / width
            val envelope = sin(normalizedX * PI).toFloat() // Dampens edges
            val y = centerY + sin(normalizedX * 4f * PI + phase).toFloat() * (height * 0.35f) * envelope
            path1.lineTo(x, y)
            x += step
        }

        drawPath(
            path = path1,
            brush = Brush.horizontalGradient(
                colors = listOf(primaryColor.copy(alpha = 0.4f), secondaryColor, primaryColor)
            ),
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )

        // Wave 2: Secondary Harmonic (Interfering Resonance)
        val path2 = Path()
        path2.moveTo(0f, centerY)
        x = 0f
        while (x <= width) {
            val normalizedX = x / width
            val envelope = sin(normalizedX * PI).toFloat()
            val y = centerY + sin(normalizedX * 6f * PI - phase * 1.3f).toFloat() * (height * 0.22f) * envelope
            path2.lineTo(x, y)
            x += step
        }

        drawPath(
            path = path2,
            brush = Brush.horizontalGradient(
                colors = listOf(secondaryColor.copy(alpha = 0.2f), primaryColor.copy(alpha = 0.8f), secondaryColor.copy(alpha = 0.2f))
            ),
            style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
        )

        // Center resonance nodes
        val nodeCount = 5
        for (i in 1..nodeCount) {
            val nodeX = (width / (nodeCount + 1)) * i
            val normX = nodeX / width
            val envelope = sin(normX * PI).toFloat()
            val nodeY = centerY + sin(normX * 4f * PI + phase).toFloat() * (height * 0.35f) * envelope

            drawCircle(
                color = Color.White,
                radius = 3.dp.toPx(),
                center = Offset(nodeX, nodeY)
            )
            drawCircle(
                color = secondaryColor.copy(alpha = 0.4f),
                radius = 7.dp.toPx(),
                center = Offset(nodeX, nodeY)
            )
        }
    }
}
