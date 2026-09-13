package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.Dilemma
import com.example.data.model.UserProfile
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.NeonCyanPulse
import com.example.ui.theme.WavelengthBorderHighlight
import com.example.ui.theme.WavelengthBorderSubtle
import com.example.ui.theme.WavelengthObsidian
import com.example.ui.theme.WavelengthSurfaceCard
import com.example.ui.theme.WavelengthTextMuted
import com.example.ui.theme.WavelengthTextPrimary
import com.example.ui.theme.WavelengthTextSecondary

@Composable
fun ShareableAuraModal(
    dilemma: Dilemma,
    userProfile: UserProfile,
    onDismiss: () -> Unit
) {
    var copied by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .border(1.dp, WavelengthBorderHighlight, RoundedCornerShape(28.dp)),
            colors = CardDefaults.cardColors(containerColor = WavelengthObsidian)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "EXPORT AURA CARD",
                        style = MaterialTheme.typography.labelMedium,
                        color = NeonCyanPulse,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close modal",
                            tint = WavelengthTextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // The Aesthetic Shareable Card (Ready for 9:16 Story format)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(22.dp))
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xFF1E1438),
                                    Color(0xFF101328),
                                    WavelengthSurfaceCard
                                )
                            )
                        )
                        .border(1.dp, ElectricViolet.copy(alpha = 0.4f), RoundedCornerShape(22.dp))
                        .padding(18.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Branding Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "WAVELENGTH // 2026",
                                style = MaterialTheme.typography.labelSmall,
                                color = WavelengthTextMuted,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.5.sp
                            )
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(NeonCyanPulse.copy(alpha = 0.2f))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "FREQUENCY SYNC",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = NeonCyanPulse,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // User Persona & Archetype
                        Text(
                            text = userProfile.archetype.uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "${userProfile.name} • ${userProfile.handle}",
                            style = MaterialTheme.typography.labelMedium,
                            color = ElectricViolet
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Kinetic Waveform Visualizer
                        WaveformVisualizer(
                            primaryColor = ElectricViolet,
                            secondaryColor = NeonCyanPulse,
                            speedFactor = 1.2f
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Dilemma & User Stance
                        Text(
                            text = "\"${dilemma.title}\"",
                            style = MaterialTheme.typography.titleMedium,
                            color = WavelengthTextPrimary,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        val userStance = if (dilemma.userVote == 1) dilemma.optionA else if (dilemma.userVote == 2) dilemma.optionB else "Undecided"
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(ElectricViolet.copy(alpha = 0.2f))
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "STANCE: $userStance",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Deep Link QR badge simulator
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(WavelengthObsidian.copy(alpha = 0.5f))
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.QrCode,
                                contentDescription = "QR Code",
                                tint = NeonCyanPulse,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Scan or tap to test your sync: wavelength.me/sync",
                                style = MaterialTheme.typography.labelSmall,
                                color = WavelengthTextSecondary,
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Actions: Share & Copy Link
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            // Trigger native android share intent simulation
                            copied = true
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("export_story_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(
                            imageVector = if (copied) Icons.Default.Check else Icons.Default.Share,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (copied) "Card Shared!" else "Share to Story")
                    }

                    OutlinedButton(
                        onClick = { copied = true },
                        modifier = Modifier.testTag("copy_link_button"),
                        shape = RoundedCornerShape(14.dp),
                        border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.horizontalGradient(listOf(ElectricViolet, NeonCyanPulse)))
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy Link",
                            tint = NeonCyanPulse,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}
