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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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

@Composable
fun CreateScreen(
    onDilemmaCreated: (String, String, String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var context by remember { mutableStateOf("") }
    var optionA by remember { mutableStateOf("") }
    var optionB by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Culture") }
    var isPublished by remember { mutableStateOf(false) }
    var isAiEnhancing by remember { mutableStateOf(false) }

    val categories = listOf("Culture", "Tech Ethics", "Philosophy", "Relationships", "Future")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WavelengthObsidian),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header
        item {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(ElectricViolet.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = ElectricViolet,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = "Forge a Dilemma",
                        style = MaterialTheme.typography.headlineMedium,
                        color = WavelengthTextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Pose a nuanced question that reveals where minds harmonize or diverge.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = WavelengthTextSecondary
                )
            }
        }

        // Fast Template Prompt Starter
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(WavelengthSurfaceHover)
                    .clickable {
                        title = "Algorithmic Soulmates vs Real Friction"
                        context = "If an AI matching model guarantees 99% compatibility, is falling in love without friction emotionally hollow?"
                        optionA = "Friction is essential for real intimacy"
                        optionB = "Effortless compatibility is an upgrade"
                        selectedCategory = "Relationships"
                    }
                    .padding(14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.TipsAndUpdates,
                        contentDescription = "Idea",
                        tint = NeonCyanPulse,
                        modifier = Modifier.size(20.dp)
                    )
                    Column {
                        Text(
                            text = "Need inspiration? Tap to autofill sample dilemma:",
                            style = MaterialTheme.typography.labelSmall,
                            color = NeonCyanPulse,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "\"Algorithmic Soulmates vs Real Friction\"",
                            style = MaterialTheme.typography.bodySmall,
                            color = WavelengthTextPrimary
                        )
                    }
                }
            }
        }

        // Category Chips
        item {
            Column {
                Text(
                    text = "Category Domain",
                    style = MaterialTheme.typography.labelMedium,
                    color = WavelengthTextSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(categories) { cat ->
                        val isSelected = cat == selectedCategory
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) ElectricViolet else WavelengthSurfaceCard)
                                .border(1.dp, if (isSelected) ElectricViolet else WavelengthBorderSubtle, RoundedCornerShape(16.dp))
                                .clickable { selectedCategory = cat }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = cat,
                                style = MaterialTheme.typography.labelMedium,
                                color = if (isSelected) Color.White else WavelengthTextSecondary,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }

        // Title Input
        item {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Dilemma Core Title") },
                placeholder = { Text("e.g. Memory vs Forgiveness") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dilemma_title_input"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricViolet,
                    unfocusedBorderColor = WavelengthBorderSubtle,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = ElectricViolet,
                    unfocusedLabelColor = WavelengthTextMuted
                ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )
        }

        // Context Input
        item {
            OutlinedTextField(
                value = context,
                onValueChange = { context = it },
                label = { Text("Nuance & Context (The Real Tension)") },
                placeholder = { Text("Explain why both choices have philosophical merit...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dilemma_context_input"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricViolet,
                    unfocusedBorderColor = WavelengthBorderSubtle,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = ElectricViolet,
                    unfocusedLabelColor = WavelengthTextMuted
                ),
                shape = RoundedCornerShape(16.dp),
                minLines = 3,
                maxLines = 4
            )
        }

        // Option A and Option B
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = optionA,
                    onValueChange = { optionA = it },
                    label = { Text("Position A") },
                    placeholder = { Text("e.g. Intimacy requires memory") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("option_a_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricViolet,
                        unfocusedBorderColor = WavelengthBorderSubtle,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = ElectricViolet,
                        unfocusedLabelColor = WavelengthTextMuted
                    ),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = optionB,
                    onValueChange = { optionB = it },
                    label = { Text("Position B") },
                    placeholder = { Text("e.g. Forgiveness requires forgetting") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("option_b_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyanPulse,
                        unfocusedBorderColor = WavelengthBorderSubtle,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = NeonCyanPulse,
                        unfocusedLabelColor = WavelengthTextMuted
                    ),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true
                )
            }
        }

        // AI Contrast Sharpener Button
        item {
            Button(
                onClick = {
                    isAiEnhancing = true
                    // Enhance options with sharp contrast
                    if (title.isBlank()) title = "Digital Ghosts vs Clean Severance"
                    if (context.isBlank()) context = "Should personal archives and shared chat logs be auto-deleted after mutual relationships end?"
                    if (optionA.isBlank()) optionA = "Preserve raw historical record"
                    if (optionB.isBlank()) optionB = "Ephemeral purge for mental freedom"
                    isAiEnhancing = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("ai_enhance_button"),
                colors = ButtonDefaults.buttonColors(containerColor = WavelengthSurfaceHover),
                shape = RoundedCornerShape(14.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(listOf(ElectricViolet, NeonCyanPulse))
                )
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = NeonCyanPulse,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isAiEnhancing) "Sharpening Dilemma..." else "AI Dilemma Sharpener (Enhance Contrast)",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }

        // Live Preview Card
        if (title.isNotBlank()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .border(1.dp, WavelengthBorderHighlight, RoundedCornerShape(20.dp)),
                    colors = CardDefaults.cardColors(containerColor = WavelengthSurfaceCard)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "LIVE CARD PREVIEW",
                            style = MaterialTheme.typography.labelSmall,
                            color = NeonCyanPulse,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = context.ifBlank { "Tension details..." },
                            style = MaterialTheme.typography.bodySmall,
                            color = WavelengthTextSecondary
                        )
                    }
                }
            }
        }

        // Publish Button
        item {
            val canPublish = title.isNotBlank() && optionA.isNotBlank() && optionB.isNotBlank()
            Button(
                onClick = {
                    onDilemmaCreated(
                        title,
                        context.ifBlank { "Community philosophical tension inquiry." },
                        selectedCategory,
                        optionA,
                        optionB
                    )
                    isPublished = true
                },
                enabled = canPublish,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("publish_dilemma_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ElectricViolet,
                    disabledContainerColor = WavelengthSurfaceHover
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = if (isPublished) Icons.Default.Check else Icons.Default.Send,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isPublished) "Dilemma Live on Wavelength!" else "Broadcast to Wavelength",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
