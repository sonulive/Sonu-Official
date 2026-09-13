package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.data.model.FriendSync
import com.example.data.model.UserProfile
import com.example.ui.components.DilemmaCard
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

@Composable
fun HomeScreen(
    dailyDilemma: Dilemma?,
    recentDilemmas: List<Dilemma>,
    userProfile: UserProfile?,
    friends: List<FriendSync>,
    onVote: (String, Int) -> Unit,
    onOpenSparks: (Dilemma) -> Unit,
    onShare: (Dilemma) -> Unit,
    onNavigateToCircles: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WavelengthObsidian),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Daily Resonance Frequency Banner
        item {
            DailyResonanceHero(
                userProfile = userProfile,
                onExploreSync = onNavigateToCircles
            )
        }

        // Friend Sync Radar (Horizontal glance)
        item {
            FriendSyncRadarBar(
                friends = friends,
                onViewAll = onNavigateToCircles
            )
        }

        // Section Title: Daily Drop
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ElectricBolt,
                        contentDescription = null,
                        tint = NeonCyanPulse,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Today's Core Drop",
                        style = MaterialTheme.typography.titleLarge,
                        color = WavelengthTextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "Refreshes in 11h",
                    style = MaterialTheme.typography.labelSmall,
                    color = WavelengthTextMuted
                )
            }
        }

        // Daily Featured Dilemma Card
        if (dailyDilemma != null) {
            item {
                DilemmaCard(
                    dilemma = dailyDilemma,
                    onVote = { option -> onVote(dailyDilemma.id, option) },
                    onOpenSparks = onOpenSparks,
                    onShare = onShare
                )
            }
        }

        // Additional Fresh Dilemmas
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Trending Community Debates",
                style = MaterialTheme.typography.titleLarge,
                color = WavelengthTextPrimary,
                fontWeight = FontWeight.Bold
            )
        }

        items(recentDilemmas.filter { !it.isDailyFeatured }) { dilemma ->
            DilemmaCard(
                dilemma = dilemma,
                onVote = { option -> onVote(dilemma.id, option) },
                onOpenSparks = onOpenSparks,
                onShare = onShare
            )
        }
    }
}

@Composable
private fun DailyResonanceHero(
    userProfile: UserProfile?,
    onExploreSync: () -> Unit
) {
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
                            ElectricViolet.copy(alpha = 0.22f),
                            WavelengthSurfaceCard
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(NeonCyanPulse.copy(alpha = 0.15f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "AURA HARMONICS",
                        style = MaterialTheme.typography.labelSmall,
                        color = NeonCyanPulse,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${userProfile?.streakDays ?: 12} Day Insight Streak",
                        style = MaterialTheme.typography.labelSmall,
                        color = SolarAmber,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = userProfile?.archetype ?: "Metacognitive Architect",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Your mental frequency harmonizes 91% with your inner circle today.",
                style = MaterialTheme.typography.bodyMedium,
                color = WavelengthTextSecondary
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Animated Waveform in the Hero
            WaveformVisualizer(
                primaryColor = ElectricViolet,
                secondaryColor = NeonCyanPulse,
                speedFactor = 1.0f
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(onClick = onExploreSync)
                    .background(WavelengthSurfaceHover.copy(alpha = 0.7f))
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "See Friend Compatibility Matrix",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = NeonCyanPulse,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun FriendSyncRadarBar(
    friends: List<FriendSync>,
    onViewAll: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PeopleAlt,
                    contentDescription = null,
                    tint = ElectricViolet,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Sync Circles",
                    style = MaterialTheme.typography.titleMedium,
                    color = WavelengthTextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = "View Radar",
                style = MaterialTheme.typography.labelMedium,
                color = NeonCyanPulse,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable(onClick = onViewAll)
                    .testTag("view_radar_button")
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(friends) { friend ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(WavelengthSurfaceCard)
                        .border(1.dp, WavelengthBorderSubtle, RoundedCornerShape(16.dp))
                        .clickable(onClick = onViewAll)
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(WavelengthSurfaceHover),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = friend.avatarEmoji, fontSize = 18.sp)
                        }

                        Column {
                            Text(
                                text = friend.name,
                                style = MaterialTheme.typography.labelMedium,
                                color = WavelengthTextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${friend.harmonicSync}% Harmonic",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (friend.harmonicSync >= 90) NeonCyanPulse else ElectricViolet,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}
