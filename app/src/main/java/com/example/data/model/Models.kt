package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a core daily or community perspective dilemma in Wavelength.
 */
@Entity(tableName = "dilemmas")
data class Dilemma(
    @PrimaryKey val id: String,
    val title: String,
    val context: String,
    val category: String, // "Culture", "Tech Ethics", "Philosophy", "Relationships", "Future"
    val optionA: String,
    val optionB: String,
    val voteCountA: Int,
    val voteCountB: Int,
    val userVote: Int? = null, // 1 for A, 2 for B, null if not voted
    val aiInsight: String, // AI synthesis of psychological tension
    val authorName: String = "Wavelength Curators",
    val authorTag: String = "Official Drop",
    val isDailyFeatured: Boolean = false,
    val createdTimestamp: Long = System.currentTimeMillis(),
    val harmonicSyncPercent: Int = 88 // Friend sync overlap
)

/**
 * User Aura Identity & Cognitive Profile.
 */
@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: String = "current_user",
    val name: String = "Aiden Rowe",
    val handle: String = "@aiden.wave",
    val archetype: String = "Metacognitive Architect",
    val archetypedescription: String = "You seek underlying systems in cultural shifts and value deep nuance over dogmatic extremes.",
    val auraColorHexPrimary: String = "#7B61FF",
    val auraColorHexSecondary: String = "#00F5D4",
    val streakDays: Int = 14,
    val totalDilemmasAnswered: Int = 42,
    val syncCirclesCount: Int = 5,
    val isProMember: Boolean = false,
    val topTraits: String = "Systemic Thinker, Empathic Realist, Digital Minimalist",
    val selectedInterests: String = "Tech Ethics, Deep Philosophy, Digital Culture, Sound & Art"
)

/**
 * Sync Circle friend for harmonic comparison.
 */
data class FriendSync(
    val id: String,
    val name: String,
    val handle: String,
    val avatarEmoji: String,
    val harmonicSync: Int, // e.g. 94%
    val archetype: String,
    val mutualDilemmasCount: Int,
    val status: String,
    val sparkNote: String
)

/**
 * Contextual Spark (thought exchange attached to a dilemma).
 */
data class SparkThought(
    val id: String,
    val dilemmaId: String,
    val authorName: String,
    val authorHandle: String,
    val stance: String,
    val thought: String,
    val timestamp: String,
    val likesCount: Int = 12
)
