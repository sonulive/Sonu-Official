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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Public
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
import com.example.ui.components.DilemmaCard
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
fun DiscoverScreen(
    dilemmas: List<Dilemma>,
    selectedCategory: String,
    onSelectCategory: (String) -> Unit,
    onVote: (String, Int) -> Unit,
    onOpenSparks: (Dilemma) -> Unit,
    onShare: (Dilemma) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf("All", "Tech Ethics", "Culture", "Relationships", "Philosophy", "Future")

    val filtered = if (selectedCategory == "All") {
        dilemmas
    } else {
        dilemmas.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WavelengthObsidian),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Global Consensus Heatmap Banner
        item {
            GlobalResonanceHeatmapCard()
        }

        // Category Filter Chips
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Explore Perspectives",
                        style = MaterialTheme.typography.titleMedium,
                        color = WavelengthTextPrimary,
                        fontWeight = FontWeight.Bold
                    )

                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Filter",
                        tint = WavelengthTextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { cat ->
                        val isSelected = cat == selectedCategory
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    if (isSelected) ElectricViolet else WavelengthSurfaceCard
                                )
                                .border(
                                    1.dp,
                                    if (isSelected) ElectricViolet else WavelengthBorderSubtle,
                                    RoundedCornerShape(20.dp)
                                )
                                .clickable { onSelectCategory(cat) }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                                .testTag("category_chip_$cat")
                        ) {
                            Text(
                                text = cat,
                                style = MaterialTheme.typography.labelMedium,
                                color = if (isSelected) Color.White else WavelengthTextSecondary,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        // List of dilemmas
        items(filtered) { dilemma ->
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
private fun GlobalResonanceHeatmapCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .border(1.dp, WavelengthBorderHighlight, RoundedCornerShape(22.dp)),
        colors = CardDefaults.cardColors(containerColor = WavelengthSurfaceCard)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF1B1638),
                            WavelengthSurfaceCard
                        )
                    )
                )
                .padding(18.dp)
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
                        imageVector = Icons.Default.Public,
                        contentDescription = null,
                        tint = NeonCyanPulse,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "GLOBAL SPECTRUM HEATMAP",
                        style = MaterialTheme.typography.labelSmall,
                        color = NeonCyanPulse,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Text(
                    text = "142k Active Voters",
                    style = MaterialTheme.typography.labelSmall,
                    color = WavelengthTextMuted
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Where 2026 Gen-Z is Moving",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Strongest cultural shift: 79% favor radical offline sovereignty, but 63% demand flawless AI memory for technical workflows.",
                style = MaterialTheme.typography.bodySmall,
                color = WavelengthTextSecondary,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Micro Heatmap Bar Visualizers
            HeatmapTrack(label = "Tech Ethics", value = 71, color = ElectricViolet)
            Spacer(modifier = Modifier.height(6.dp))
            HeatmapTrack(label = "Authentic Art vs Synthetic", value = 64, color = NeonCyanPulse)
            Spacer(modifier = Modifier.height(6.dp))
            HeatmapTrack(label = "Radical Offline Time", value = 82, color = Color(0xFFFF5E7E))
        }
    }
}

@Composable
private fun HeatmapTrack(label: String, value: Int, color: Color) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = WavelengthTextSecondary
            )
            Text(
                text = "$value% Consensus",
                style = MaterialTheme.typography.labelSmall,
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape)
                .background(WavelengthSurfaceHover)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(value / 100f)
                    .height(6.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}
