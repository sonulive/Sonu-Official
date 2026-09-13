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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FriendSync
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
fun CirclesScreen(
    friends: List<FriendSync>,
    onInviteFriends: () -> Unit,
    modifier: Modifier = Modifier
) {
    var quickSparkInput by remember { mutableStateOf("") }
    var sparkSharedToast by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WavelengthObsidian),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Hero Sync Radar Card
        item {
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
                                    ElectricViolet.copy(alpha = 0.25f),
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Radar,
                                contentDescription = null,
                                tint = NeonCyanPulse,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "CIRCLE RESONANCE MATRIX",
                                style = MaterialTheme.typography.labelSmall,
                                color = NeonCyanPulse,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(WavelengthSurfaceHover)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "4 Active Kin",
                                style = MaterialTheme.typography.labelSmall,
                                color = WavelengthTextSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Your Inner Circle Sync: 88%",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Highest harmony with Maya Chen (96%). Greatest philosophical divergence with Devon Park on AI and artistic mortality.",
                        style = MaterialTheme.typography.bodySmall,
                        color = WavelengthTextSecondary,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Live Waveform visualizer showing combined circle frequency
                    WaveformVisualizer(
                        primaryColor = NeonCyanPulse,
                        secondaryColor = ElectricViolet,
                        speedFactor = 1.3f
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = onInviteFriends,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("invite_circle_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.GroupAdd,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Add Friends to Your Wave",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Quick Daily Thought Drop (Sparks)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, WavelengthBorderSubtle, RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(containerColor = WavelengthSurfaceCard)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Drop a Spark Note to Friends",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "One-turn asynchronous thought with no performative doomscrolling.",
                        style = MaterialTheme.typography.bodySmall,
                        color = WavelengthTextMuted
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = quickSparkInput,
                            onValueChange = { quickSparkInput = it },
                            placeholder = { Text("Share an uncensored realization...") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("spark_input_field"),
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ElectricViolet,
                                unfocusedBorderColor = WavelengthBorderSubtle,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            singleLine = true
                        )

                        Button(
                            onClick = {
                                if (quickSparkInput.isNotBlank()) {
                                    sparkSharedToast = true
                                    quickSparkInput = ""
                                }
                            },
                            modifier = Modifier.testTag("send_spark_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = "Send", modifier = Modifier.size(16.dp))
                        }
                    }

                    if (sparkSharedToast) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "✓ Spark broadcasted to your Sync Circle!",
                            style = MaterialTheme.typography.labelSmall,
                            color = NeonCyanPulse,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Section header
        item {
            Text(
                text = "Harmonic Sync Members",
                style = MaterialTheme.typography.titleMedium,
                color = WavelengthTextPrimary,
                fontWeight = FontWeight.Bold
            )
        }

        // Friends list
        items(friends) { friend ->
            FriendSyncCard(friend = friend)
        }
    }
}

@Composable
private fun FriendSyncCard(friend: FriendSync) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, WavelengthBorderSubtle, RoundedCornerShape(20.dp)),
        color = WavelengthSurfaceCard
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(WavelengthSurfaceHover),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = friend.avatarEmoji, fontSize = 22.sp)
                    }

                    Column {
                        Text(
                            text = friend.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = WavelengthTextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${friend.handle} • ${friend.archetype}",
                            style = MaterialTheme.typography.labelSmall,
                            color = WavelengthTextSecondary
                        )
                    }
                }

                // Harmonic Sync Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (friend.harmonicSync >= 90) NeonCyanPulse.copy(alpha = 0.18f)
                            else ElectricViolet.copy(alpha = 0.18f)
                        )
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "${friend.harmonicSync}% SYNC",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (friend.harmonicSync >= 90) NeonCyanPulse else ElectricViolet,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Recent Spark Note from friend
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(WavelengthSurfaceHover.copy(alpha = 0.6f))
                    .padding(12.dp)
            ) {
                Column {
                    Text(
                        text = "LATEST PERSPECTIVE SPARK",
                        style = MaterialTheme.typography.labelSmall,
                        color = WavelengthTextMuted,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = friend.sparkNote,
                        style = MaterialTheme.typography.bodySmall,
                        color = WavelengthTextPrimary,
                        lineHeight = 17.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${friend.mutualDilemmasCount} Mutual Debates Answered",
                    style = MaterialTheme.typography.labelSmall,
                    color = WavelengthTextSecondary
                )
                Text(
                    text = friend.status,
                    style = MaterialTheme.typography.labelSmall,
                    color = SolarAmber
                )
            }
        }
    }
}
