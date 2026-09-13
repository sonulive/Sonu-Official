package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.WaveformVisualizer
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.NeonCyanPulse
import com.example.ui.theme.WavelengthBorderHighlight
import com.example.ui.theme.WavelengthBorderSubtle
import com.example.ui.theme.WavelengthObsidian
import com.example.ui.theme.WavelengthSurfaceCard
import com.example.ui.theme.WavelengthSurfaceHover
import com.example.ui.theme.WavelengthTextMuted
import com.example.ui.theme.WavelengthTextPrimary
import com.example.ui.theme.WavelengthTextSecondary
import com.example.util.HapticFeedbackManager
import com.example.util.rememberHapticFeedbackManager

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit
) {
    var step by remember { mutableIntStateOf(1) }
    val totalSteps = 6

    val selectedInterests = remember {
        mutableStateListOf("Tech Ethics", "Digital Culture", "Deep Philosophy")
    }
    var firstChoiceVote by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WavelengthObsidian)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Step Indicator Dots
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..totalSteps) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(if (i == step) 20.dp else 8.dp, 6.dp)
                        .clip(CircleShape)
                        .background(
                            if (i == step) NeonCyanPulse
                            else if (i < step) ElectricViolet
                            else WavelengthSurfaceHover
                        )
                )
            }
        }

        // Main dynamic step content
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = step,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "onboarding_step"
            ) { currentStep ->
                when (currentStep) {
                    1 -> StepBrandIntro()
                    2 -> StepValueProp()
                    3 -> StepInterests(
                        selected = selectedInterests,
                        onToggle = { interest ->
                            if (selectedInterests.contains(interest)) {
                                selectedInterests.remove(interest)
                            } else {
                                selectedInterests.add(interest)
                            }
                        }
                    )
                    4 -> StepFirstDilemma(
                        vote = firstChoiceVote,
                        onVote = { firstChoiceVote = it }
                    )
                    5 -> StepInstantAuraResult()
                    6 -> StepConnectFriends()
                    else -> StepBrandIntro()
                }
            }
        }

        // Bottom Navigation Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (step > 1 && step < 6) {
                OutlinedButton(
                    onClick = { step-- },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Back", color = WavelengthTextSecondary)
                }
            }

            Button(
                onClick = {
                    if (step < totalSteps) {
                        step++
                    } else {
                        onFinishOnboarding()
                    }
                },
                modifier = Modifier
                    .weight(if (step == 1 || step == 6) 2f else 1.5f)
                    .height(52.dp)
                    .testTag("onboarding_next_button"),
                colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = if (step == totalSteps) "Enter Wavelength" else "Continue",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun StepBrandIntro() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        listOf(ElectricViolet, NeonCyanPulse)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(44.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Wavelength",
            style = MaterialTheme.typography.displayLarge,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = (-1).sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Perspective Resonance for 2026",
            style = MaterialTheme.typography.titleMedium,
            color = NeonCyanPulse,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(20.dp))

        WaveformVisualizer(
            primaryColor = ElectricViolet,
            secondaryColor = NeonCyanPulse,
            speedFactor = 1.0f
        )
    }
}

@Composable
private fun StepValueProp() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(ElectricViolet.copy(alpha = 0.2f))
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Text(
                text = "THE CORE IDEA",
                style = MaterialTheme.typography.labelSmall,
                color = ElectricViolet,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Find the minds on your exact frequency.",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Every day, explore 3 nuanced dilemmas. Discover your mental archetype and compare real harmonic resonance with friends—without performative clout or toxic feeds.",
            style = MaterialTheme.typography.bodyLarge,
            color = WavelengthTextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StepInterests(
    selected: List<String>,
    onToggle: (String) -> Unit
) {
    val options = listOf(
        "Tech Ethics", "Digital Culture", "Deep Philosophy",
        "Relationships", "Future & AI", "Slow Living",
        "Aesthetic Theory", "Creator Lore", "Existential Stances"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Select Your Domains",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We tune your daily drops based on where you seek cognitive depth.",
            style = MaterialTheme.typography.bodyMedium,
            color = WavelengthTextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { item ->
                val isSelected = selected.contains(item)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) ElectricViolet else WavelengthSurfaceCard)
                        .border(
                            1.dp,
                            if (isSelected) ElectricViolet else WavelengthBorderSubtle,
                            RoundedCornerShape(20.dp)
                        )
                        .clickable { onToggle(item) }
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (isSelected) Color.White else WavelengthTextSecondary,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Composable
private fun StepFirstDilemma(
    vote: Int?,
    onVote: (Int) -> Unit,
    hapticFeedbackManager: HapticFeedbackManager = rememberHapticFeedbackManager()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(NeonCyanPulse.copy(alpha = 0.2f))
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(
                text = "YOUR FIRST TASTE",
                style = MaterialTheme.typography.labelSmall,
                color = NeonCyanPulse,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Flawless Memory vs Grace of Forgetting",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Does intimate connection require remembering everything, or the gentle mercy of letting things fade?",
            style = MaterialTheme.typography.bodySmall,
            color = WavelengthTextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Option 1
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(if (vote == 1) ElectricViolet.copy(alpha = 0.25f) else WavelengthSurfaceCard)
                .border(
                    1.dp,
                    if (vote == 1) ElectricViolet else WavelengthBorderSubtle,
                    RoundedCornerShape(16.dp)
                )
                .clickable {
                    hapticFeedbackManager.performSelectionHaptic()
                    onVote(1)
                }
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (vote == 1) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = ElectricViolet)
                }
                Text(
                    text = "A: Intimacy requires flawless total recall",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    fontWeight = if (vote == 1) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Option 2
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(if (vote == 2) NeonCyanPulse.copy(alpha = 0.25f) else WavelengthSurfaceCard)
                .border(
                    1.dp,
                    if (vote == 2) NeonCyanPulse else WavelengthBorderSubtle,
                    RoundedCornerShape(16.dp)
                )
                .clickable {
                    hapticFeedbackManager.performSelectionHaptic()
                    onVote(2)
                }
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (vote == 2) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = NeonCyanPulse)
                }
                Text(
                    text = "B: Forgiveness requires the grace of forgetting",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    fontWeight = if (vote == 2) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun StepInstantAuraResult() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(26.dp))
            .border(1.dp, WavelengthBorderHighlight, RoundedCornerShape(26.dp)),
        colors = CardDefaults.cardColors(containerColor = WavelengthSurfaceCard)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF26184C),
                            WavelengthSurfaceCard
                        )
                    )
                )
                .padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Psychology,
                contentDescription = null,
                tint = NeonCyanPulse,
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "YOUR AURA ARCHETYPE",
                style = MaterialTheme.typography.labelSmall,
                color = NeonCyanPulse,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Metacognitive Architect",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            WaveformVisualizer(
                primaryColor = ElectricViolet,
                secondaryColor = NeonCyanPulse,
                speedFactor = 1.0f
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "You balance technological acceleration with deep existential grounding, valuing nuanced truth over polarized groupthink.",
                style = MaterialTheme.typography.bodySmall,
                color = WavelengthTextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun StepConnectFriends() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(ElectricViolet.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Group,
                contentDescription = null,
                tint = NeonCyanPulse,
                modifier = Modifier.size(36.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Your Wavelength is Live",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Share your personal Aura Card with friends to unlock the real-time Harmonic Sync Radar and start exchanging daily perspective sparks.",
            style = MaterialTheme.typography.bodyMedium,
            color = WavelengthTextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )
    }
}
