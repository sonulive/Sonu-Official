package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Dilemma
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.NeonCyanPulse
import com.example.ui.theme.WavelengthBorderSubtle
import com.example.ui.theme.WavelengthSurfaceCard
import com.example.ui.theme.WavelengthSurfaceHover
import com.example.ui.theme.WavelengthTextMuted
import com.example.ui.theme.WavelengthTextPrimary
import com.example.ui.theme.WavelengthTextSecondary
import com.example.util.HapticFeedbackManager
import com.example.util.rememberHapticFeedbackManager

@Composable
fun DilemmaCard(
    dilemma: Dilemma,
    onVote: (Int) -> Unit,
    onOpenSparks: (Dilemma) -> Unit,
    onShare: (Dilemma) -> Unit,
    modifier: Modifier = Modifier,
    hapticFeedbackManager: HapticFeedbackManager = rememberHapticFeedbackManager()
) {
    val hasVoted = dilemma.userVote != null
    val totalVotes = (dilemma.voteCountA + dilemma.voteCountB).coerceAtLeast(1)
    val pctA = ((dilemma.voteCountA.toFloat() / totalVotes) * 100).toInt()
    val pctB = 100 - pctA

    val animatedPctA by animateFloatAsState(
        targetValue = if (hasVoted) pctA / 100f else 0f,
        animationSpec = spring(stiffness = 300f),
        label = "anim_pct_a"
    )
    val animatedPctB by animateFloatAsState(
        targetValue = if (hasVoted) pctB / 100f else 0f,
        animationSpec = spring(stiffness = 300f),
        label = "anim_pct_b"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .border(1.dp, WavelengthBorderSubtle, RoundedCornerShape(24.dp)),
        color = WavelengthSurfaceCard,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header Row: Category Pill & Source
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(ElectricViolet.copy(alpha = 0.15f))
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = dilemma.category.uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        color = ElectricViolet,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (dilemma.isDailyFeatured) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(NeonCyanPulse.copy(alpha = 0.15f))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "DAILY DROP",
                                style = MaterialTheme.typography.labelMedium,
                                color = NeonCyanPulse,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        Text(
                            text = dilemma.authorTag,
                            style = MaterialTheme.typography.labelMedium,
                            color = WavelengthTextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Title & Context
            Text(
                text = dilemma.title,
                style = MaterialTheme.typography.titleLarge,
                color = WavelengthTextPrimary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = dilemma.context,
                style = MaterialTheme.typography.bodyMedium,
                color = WavelengthTextSecondary,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Choice Card A
            ChoiceOptionButton(
                text = dilemma.optionA,
                percentage = pctA,
                animatedProgress = animatedPctA,
                isSelected = dilemma.userVote == 1,
                hasVoted = hasVoted,
                activeColor = ElectricViolet,
                testTag = "choice_option_a_${dilemma.id}",
                onClick = {
                    hapticFeedbackManager.performSelectionHaptic()
                    onVote(1)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Choice Card B
            ChoiceOptionButton(
                text = dilemma.optionB,
                percentage = pctB,
                animatedProgress = animatedPctB,
                isSelected = dilemma.userVote == 2,
                hasVoted = hasVoted,
                activeColor = NeonCyanPulse,
                testTag = "choice_option_b_${dilemma.id}",
                onClick = {
                    hapticFeedbackManager.performSelectionHaptic()
                    onVote(2)
                }
            )

            // Revealed AI Insight & Friend Resonance after voting
            AnimatedVisibility(
                visible = hasVoted,
                enter = fadeIn() + expandVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(WavelengthSurfaceHover.copy(alpha = 0.6f))
                        .border(1.dp, WavelengthBorderSubtle, RoundedCornerShape(16.dp))
                        .padding(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "AI Resonance Insight",
                            tint = NeonCyanPulse,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "AI COGNITIVE TENSION ANALYSIS",
                            style = MaterialTheme.typography.labelMedium,
                            color = NeonCyanPulse,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = dilemma.aiInsight,
                        style = MaterialTheme.typography.bodySmall,
                        color = WavelengthTextSecondary,
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Friend Sync: ${dilemma.harmonicSyncPercent}% Harmonic Overlap",
                            style = MaterialTheme.typography.labelMedium,
                            color = ElectricViolet,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${totalVotes.formatCount()} Minds Polled",
                            style = MaterialTheme.typography.labelSmall,
                            color = WavelengthTextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Bottom Actions: Sparks Discussion & Share Card
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onOpenSparks(dilemma) }
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Forum,
                        contentDescription = "Sparks discussion",
                        tint = WavelengthTextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Perspective Sparks",
                        style = MaterialTheme.typography.labelMedium,
                        color = WavelengthTextSecondary
                    )
                }

                IconButton(
                    onClick = { onShare(dilemma) },
                    modifier = Modifier.testTag("share_dilemma_${dilemma.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.IosShare,
                        contentDescription = "Share Aura Card",
                        tint = ElectricViolet,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ChoiceOptionButton(
    text: String,
    percentage: Int,
    animatedProgress: Float,
    isSelected: Boolean,
    hasVoted: Boolean,
    activeColor: Color,
    testTag: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(WavelengthSurfaceHover)
            .border(
                width = if (isSelected) 1.5.dp else 1.dp,
                color = if (isSelected) activeColor else WavelengthBorderSubtle,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        // Background percentage fill bar
        if (hasVoted) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress)
                    .matchParentSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                activeColor.copy(alpha = if (isSelected) 0.35f else 0.15f),
                                activeColor.copy(alpha = if (isSelected) 0.2f else 0.08f)
                            )
                        )
                    )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Selected",
                        tint = activeColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSelected) Color.White else WavelengthTextPrimary,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                )
            }

            if (hasVoted) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$percentage%",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isSelected) activeColor else WavelengthTextSecondary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

private fun Int.formatCount(): String {
    return if (this >= 1000) {
        String.format("%.1fk", this / 1000.0)
    } else {
        this.toString()
    }
}
