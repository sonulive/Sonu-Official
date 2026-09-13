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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Dilemma
import com.example.data.model.SparkThought
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.NeonCyanPulse
import com.example.ui.theme.WavelengthBorderSubtle
import com.example.ui.theme.WavelengthObsidian
import com.example.ui.theme.WavelengthSurfaceCard
import com.example.ui.theme.WavelengthSurfaceHover
import com.example.ui.theme.WavelengthTextMuted
import com.example.ui.theme.WavelengthTextPrimary
import com.example.ui.theme.WavelengthTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SparksBottomSheet(
    dilemma: Dilemma,
    initialSparks: List<SparkThought>,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var newThought by remember { mutableStateOf("") }
    val sparksList = remember { mutableStateListOf<SparkThought>().apply { addAll(initialSparks) } }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = WavelengthObsidian
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // Header
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
                        imageVector = Icons.Default.Forum,
                        contentDescription = null,
                        tint = NeonCyanPulse,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Perspective Sparks",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = WavelengthTextSecondary
                    )
                }
            }

            Text(
                text = "\"${dilemma.title}\"",
                style = MaterialTheme.typography.bodyMedium,
                color = ElectricViolet,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Sparks list
            LazyColumn(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .height(300.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(sparksList) { spark ->
                    SparkItemView(spark = spark)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Post new spark input
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = newThought,
                    onValueChange = { newThought = it },
                    placeholder = { Text("Drop your 1-line take...") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("sparks_input_box"),
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
                        if (newThought.isNotBlank()) {
                            sparksList.add(
                                0,
                                SparkThought(
                                    id = "new_${System.currentTimeMillis()}",
                                    dilemmaId = dilemma.id,
                                    authorName = "You",
                                    authorHandle = "@you.wave",
                                    stance = if (dilemma.userVote == 1) dilemma.optionA else dilemma.optionB,
                                    thought = newThought,
                                    timestamp = "Just now",
                                    likesCount = 1
                                )
                            )
                            newThought = ""
                        }
                    },
                    modifier = Modifier.testTag("send_spark_thought_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricViolet),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SparkItemView(spark: SparkThought) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, WavelengthBorderSubtle, RoundedCornerShape(16.dp)),
        color = WavelengthSurfaceCard
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = spark.authorName,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = spark.authorHandle,
                        style = MaterialTheme.typography.labelSmall,
                        color = WavelengthTextMuted
                    )
                }

                Text(
                    text = spark.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = WavelengthTextMuted
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // User Stance Pill
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(WavelengthSurfaceHover)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "STANCE: ${spark.stance}",
                    style = MaterialTheme.typography.labelSmall,
                    color = NeonCyanPulse,
                    fontSize = 10.sp
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = spark.thought,
                style = MaterialTheme.typography.bodySmall,
                color = WavelengthTextSecondary,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = ElectricViolet,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = " ${spark.likesCount}",
                    style = MaterialTheme.typography.labelSmall,
                    color = WavelengthTextSecondary
                )
            }
        }
    }
}
