package com.example.util

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

/**
 * Manages tactile and subtle haptic feedback across the Wavelength application,
 * specifically providing tailored vibration responses when interacting with resonance dilemmas.
 */
class HapticFeedbackManager(private val context: Context) {

    private val vibrator: Vibrator? by lazy {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }
        } catch (_: Exception) {
            null
        }
    }

    /**
     * Subtle, crisp tactile response when a user selects a side on a resonance dilemma.
     * Uses Android's predefined click/tick vibration effect on modern devices,
     * with a subtle calibrated one-shot waveform as fallback.
     */
    fun performSelectionHaptic() {
        try {
            val vib = vibrator ?: return
            if (!vib.hasVibrator()) return

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Predefined subtle click effect
                val effect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
                vib.vibrate(effect)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Subtle one-shot: 20ms duration with gentle amplitude (64/255)
                val effect = VibrationEffect.createOneShot(20, 64)
                vib.vibrate(effect)
            } else {
                @Suppress("DEPRECATION")
                vib.vibrate(18)
            }
        } catch (_: Exception) {
            // Gracefully ignore if vibrator is unavailable or restricted
        }
    }

    /**
     * Subtle double-pulse resonance haptic when harmonic sync or dilemma results are calculated.
     */
    fun performResonancePulse() {
        try {
            val vib = vibrator ?: return
            if (!vib.hasVibrator()) return

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val timings = longArrayOf(0, 15, 40, 25)
                val amplitudes = intArrayOf(0, 50, 0, 90)
                val effect = VibrationEffect.createWaveform(timings, amplitudes, -1)
                vib.vibrate(effect)
            } else {
                @Suppress("DEPRECATION")
                vib.vibrate(longArrayOf(0, 20, 40, 25), -1)
            }
        } catch (_: Exception) {
            // Graceful fallback
        }
    }

    /**
     * Subtle micro-tick for slider adjustments or light UI taps.
     */
    fun performLightTick() {
        try {
            val vib = vibrator ?: return
            if (!vib.hasVibrator()) return

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val effect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK)
                vib.vibrate(effect)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val effect = VibrationEffect.createOneShot(10, 40)
                vib.vibrate(effect)
            } else {
                @Suppress("DEPRECATION")
                vib.vibrate(10)
            }
        } catch (_: Exception) {
            // Graceful fallback
        }
    }
}

/**
 * Composable helper to remember a [HapticFeedbackManager] instance tied to the local context.
 */
@Composable
fun rememberHapticFeedbackManager(): HapticFeedbackManager {
    val context = LocalContext.current.applicationContext
    return remember(context) { HapticFeedbackManager(context) }
}
