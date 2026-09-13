package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.data.model.UserProfile
import com.example.ui.components.WaveformVisualizer
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.NeonCyanPulse
import com.example.ui.theme.SolarAmber
import com.example.ui.theme.WavelengthBorderHighlight
import com.example.ui.theme.WavelengthBorderSubtle
import com.example.ui.theme.WavelengthObsidian
import com.example.ui.theme.WavelengthSurfaceCard
import com.example.ui.theme.WavelengthSurfaceHover
import com.example.ui.theme.WavelengthTextMuted
import com.example.ui.theme.WavelengthTextPrimary
import com.example.ui.theme.WavelengthTextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(
    userProfile: UserProfile?,
    onProUpgradeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val profile = userProfile ?: UserProfile()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WavelengthObsidian),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Identity & Archetype Hero
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .border(1.dp, WavelengthBorderHighlight, RoundedCornerShape(28.dp)),
                colors = CardDefaults.cardColors(containerColor = WavelengthSurfaceCard)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xFF1E1538),
                                    WavelengthSurfaceCard
                                )
                            )
                        )
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Top header: Settings
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (profile.isProMember) ElectricViolet.copy(alpha = 0.2f)
                                    else WavelengthSurfaceHover
                                )
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (profile.isProMember) "WAVELENGTH BLACK MEMBER" else "STANDARD FREQUENCY",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (profile.isProMember) NeonCyanPulse else WavelengthTextMuted,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }

                        IconButton(onClick = onProUpgradeClick) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = WavelengthTextSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Avatar with glowing ambient ring
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(ElectricViolet, NeonCyanPulse)
                                )
                            )
                            .padding(3.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(74.dp)
                                .clip(CircleShape)
                                .background(WavelengthObsidian),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "✦", fontSize = 34.sp, color = NeonCyanPulse)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = profile.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = profile.handle,
                        style = MaterialTheme.typography.labelMedium,
                        color = ElectricViolet
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Archetype Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(WavelengthSurfaceHover.copy(alpha = 0.8f))
                            .border(1.dp, WavelengthBorderSubtle, RoundedCornerShape(16.dp))
                            .padding(14.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Psychology,
                                    contentDescription = null,
                                    tint = NeonCyanPulse,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = profile.archetype.uppercase(),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = NeonCyanPulse,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = profile.archetypedescription,
                                style = MaterialTheme.typography.bodySmall,
                                color = WavelengthTextSecondary,
                                textAlign = TextAlign.Center,
                                lineHeight = 18.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Personal Waveform Visualizer
                    WaveformVisualizer(
                        primaryColor = ElectricViolet,
                        secondaryColor = NeonCyanPulse,
                        speedFactor = 0.9f
                    )
                }
            }
        }

        // Stats Matrix (Streak, Dilemmas Answered, Kin Circles)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Mindful Streak",
                    value = "${profile.streakDays} Days",
                    icon = Icons.Default.LocalFireDepartment,
                    iconColor = SolarAmber
                )
                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Debates Logged",
                    value = "${profile.totalDilemmasAnswered}",
                    icon = Icons.Default.BookmarkBorder,
                    iconColor = ElectricViolet
                )
                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Sync Circles",
                    value = "${profile.syncCirclesCount}",
                    icon = Icons.Default.EmojiEvents,
                    iconColor = NeonCyanPulse
                )
            }
        }

        // Cognitive Traits Tags
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, WavelengthBorderSubtle, RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(containerColor = WavelengthSurfaceCard)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Cognitive Personality Matrix",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Synthesized from your 42 daily philosophical stance choices.",
                        style = MaterialTheme.typography.bodySmall,
                        color = WavelengthTextMuted
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    val traits = profile.topTraits.split(",").map { it.trim() }
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        traits.forEach { trait ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(WavelengthSurfaceHover)
                                    .border(1.dp, WavelengthBorderSubtle, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = trait,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = ElectricViolet,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Monetization & Subscription Banner: Wavelength Black
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .border(1.dp, ElectricViolet, RoundedCornerShape(22.dp))
                    .clickable(onClick = onProUpgradeClick)
                    .testTag("wavelength_black_cta"),
                colors = CardDefaults.cardColors(containerColor = WavelengthSurfaceCard)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color(0xFF28184C),
                                    WavelengthSurfaceCard
                                )
                            )
                        )
                        .padding(18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(ElectricViolet.copy(alpha = 0.25f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.WorkspacePremium,
                                contentDescription = null,
                                tint = NeonCyanPulse,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Column {
                            Text(
                                text = if (profile.isProMember) "Wavelength Black Active" else "Upgrade to Wavelength Black",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (profile.isProMember) "Unlimited AI generation & deep psychometrics" else "Custom Aura Palettes, Group Radars & AI Pro",
                                style = MaterialTheme.typography.bodySmall,
                                color = WavelengthTextSecondary
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = NeonCyanPulse,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileStatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .border(1.dp, WavelengthBorderSubtle, RoundedCornerShape(18.dp)),
        color = WavelengthSurfaceCard
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = WavelengthTextPrimary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = WavelengthTextMuted,
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
