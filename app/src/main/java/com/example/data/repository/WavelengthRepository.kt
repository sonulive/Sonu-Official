package com.example.data.repository

import com.example.data.local.WavelengthDao
import com.example.data.model.Dilemma
import com.example.data.model.FriendSync
import com.example.data.model.SparkThought
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class WavelengthRepository(private val dao: WavelengthDao) {

    val dilemmas: Flow<List<Dilemma>> = dao.getAllDilemmas()
    val dailyFeatured: Flow<Dilemma?> = dao.getDailyFeaturedDilemma()
    val userProfile: Flow<UserProfile?> = dao.getUserProfile()

    suspend fun initializeSeedDataIfNeeded() {
        val initialProfile = UserProfile(
            id = "current_user",
            name = "Kaelen Voss",
            handle = "@kaelen.void",
            archetype = "Metacognitive Architect",
            archetypedescription = "You balance technological acceleration with deep existential grounding, valuing nuanced truth over polarized groupthink.",
            auraColorHexPrimary = "#7B61FF",
            auraColorHexSecondary = "#00F5D4",
            streakDays = 12,
            totalDilemmasAnswered = 38,
            syncCirclesCount = 4,
            isProMember = false,
            topTraits = "Reflective Skeptic, Synthetic Intuition, Aesthetic Purist",
            selectedInterests = "Post-Digital Culture, Synthetic Ethics, Slow Tech, Human Resonance"
        )
        dao.insertUserProfile(initialProfile)

        val seedDilemmas = listOf(
            Dilemma(
                id = "dilemma_daily_1",
                title = "AI Memory vs Human Forgetting",
                context = "In 2026, personal AI companions can flawlessly remember every conversation, argument, and fleeting remark from your past 5 years. Does perfect recall deepen emotional intimacy or make forgiveness impossible?",
                category = "Tech Ethics",
                optionA = "Intimacy requires flawless mutual memory",
                optionB = "Forgiveness requires the grace of forgetting",
                voteCountA = 3420,
                voteCountB = 5890,
                userVote = null,
                aiInsight = "63% lean towards human forgetting. Gen-Z associates flawless synthetic memory with permanent surveillance and emotional rigidity, craving psychological release valves.",
                authorName = "Wavelength Editorial",
                authorTag = "Today's Core Drop",
                isDailyFeatured = true,
                createdTimestamp = System.currentTimeMillis(),
                harmonicSyncPercent = 92
            ),
            Dilemma(
                id = "dilemma_culture_2",
                title = "Nostalgia: Fuel or Escapism?",
                context = "The relentless cultural recycling of 2000s and 2010s aesthetics has peaked. Are we creatively paralyzed by archival nostalgia, or is remixing the only honest 21st-century art form?",
                category = "Culture",
                optionA = "Remixing archives is our peak artistic language",
                optionB = "It masks a fear of pioneering new futures",
                voteCountA = 4120,
                voteCountB = 4680,
                userVote = null,
                aiInsight = "A dead heat (47% vs 53%). Creators view archival sampling as legitimate craft, while technologists feel culture has stalled in retro-loops.",
                authorName = "Aura Culture Lab",
                authorTag = "Trending Debate",
                isDailyFeatured = false,
                createdTimestamp = System.currentTimeMillis() - 3600000 * 5,
                harmonicSyncPercent = 84
            ),
            Dilemma(
                id = "dilemma_phil_3",
                title = "Friendship Expiration Protocols",
                context = "Most adult relationships drift apart silently with 'ghosting' or performative emoji reacts. Should friendships have an explicit, compassionate closing conversation?",
                category = "Relationships",
                optionA = "Graceful silent fading protects dignity",
                optionB = "Explicit closure conversations honor the history",
                voteCountA = 2950,
                voteCountB = 6210,
                userVote = null,
                aiInsight = "68% favor explicit closure. Young adults are rejecting 'soft ghosting' in favor of emotional clarity, despite the awkwardness.",
                authorName = "Sora & Milo",
                authorTag = "Community Spark",
                isDailyFeatured = false,
                createdTimestamp = System.currentTimeMillis() - 3600000 * 12,
                harmonicSyncPercent = 79
            ),
            Dilemma(
                id = "dilemma_future_4",
                title = "The Synthetic Identity Dilemma",
                context = "If an autonomous digital avatar generates original music, art, and philosophical commentary indistinguishable from human soulfulness, does biological authorship still matter?",
                category = "Future",
                optionA = "The resonance matters, not the biology",
                optionB = "Human mortality gives art its true resonance",
                voteCountA = 3100,
                voteCountB = 7450,
                userVote = null,
                aiInsight = "71% insist on human mortality as the anchor of authenticity. In 2026, proof-of-humanity is becoming the ultimate luxury good.",
                authorName = "Synthetic Mind Unit",
                authorTag = "Speculative Drop",
                isDailyFeatured = false,
                createdTimestamp = System.currentTimeMillis() - 3600000 * 24,
                harmonicSyncPercent = 89
            ),
            Dilemma(
                id = "dilemma_slow_5",
                title = "Monastic Offline Retreats vs Hyper-Presence",
                context = "Is total digital disconnection a privileged illusion, or the only remaining act of genuine personal sovereignty?",
                category = "Philosophy",
                optionA = "Privileged escapism from modern reality",
                optionB = "Essential radical sovereignty",
                voteCountA = 2200,
                voteCountB = 8100,
                userVote = null,
                aiInsight = "79% view radical disconnection as essential self-defense against algorithmic exhaustion.",
                authorName = "Offline Society",
                authorTag = "Deep Dive",
                isDailyFeatured = false,
                createdTimestamp = System.currentTimeMillis() - 3600000 * 36,
                harmonicSyncPercent = 95
            )
        )
        dao.insertDilemmas(seedDilemmas)
    }

    suspend fun castVote(dilemmaId: String, option: Int) {
        val dilemma = dao.getDilemmaById(dilemmaId) ?: return
        if (dilemma.userVote == option) return

        var newCountA = dilemma.voteCountA
        var newCountB = dilemma.voteCountB

        // Reverse previous vote if any
        if (dilemma.userVote == 1) newCountA -= 1
        if (dilemma.userVote == 2) newCountB -= 1

        if (option == 1) newCountA += 1
        if (option == 2) newCountB += 1

        val updated = dilemma.copy(
            userVote = option,
            voteCountA = newCountA,
            voteCountB = newCountB
        )
        dao.updateDilemma(updated)
    }

    suspend fun createDilemma(
        title: String,
        context: String,
        category: String,
        optionA: String,
        optionB: String,
        authorName: String
    ): Dilemma {
        val newDilemma = Dilemma(
            id = "user_dilemma_${UUID.randomUUID()}",
            title = title,
            context = context,
            category = category,
            optionA = optionA,
            optionB = optionB,
            voteCountA = 1,
            voteCountB = 0,
            userVote = 1,
            aiInsight = "Newly forged community dilemma. Early metrics indicate strong cognitive tension between immediate comfort and long-term autonomy.",
            authorName = authorName,
            authorTag = "Community Creator",
            isDailyFeatured = false,
            createdTimestamp = System.currentTimeMillis(),
            harmonicSyncPercent = 85
        )
        dao.insertDilemma(newDilemma)
        return newDilemma
    }

    suspend fun toggleProMembership(current: UserProfile) {
        val updated = current.copy(isProMember = !current.isProMember)
        dao.updateUserProfile(updated)
    }

    fun getSyncFriends(): List<FriendSync> = listOf(
        FriendSync(
            id = "friend_1",
            name = "Maya Chen",
            handle = "@mayachen",
            avatarEmoji = "⚡",
            harmonicSync = 96,
            archetype = "Harmonic Realist",
            mutualDilemmasCount = 28,
            status = "Voted on AI Memory",
            sparkNote = "\"Forgiveness is an organic act; databases don't have souls.\""
        ),
        FriendSync(
            id = "friend_2",
            name = "Julian Thorne",
            handle = "@j_thorne",
            avatarEmoji = "🪐",
            harmonicSync = 89,
            archetype = "Futuristic Stoic",
            mutualDilemmasCount = 24,
            status = "Created a prompt in Tech Ethics",
            sparkNote = "\"Archival nostalgia is just fear wearing vintage clothes.\""
        ),
        FriendSync(
            id = "friend_3",
            name = "Zoe Al-Mansoor",
            handle = "@zoe.flux",
            avatarEmoji = "🔮",
            harmonicSync = 82,
            archetype = "Digital Mystic",
            mutualDilemmasCount = 31,
            status = "Synced 3 mins ago",
            sparkNote = "\"Closure rituals honor the timeline we shared.\""
        ),
        FriendSync(
            id = "friend_4",
            name = "Devon Park",
            handle = "@devon.void",
            avatarEmoji = "🌊",
            harmonicSync = 74,
            archetype = "Synthesist",
            mutualDilemmasCount = 19,
            status = "Exploring Culture category",
            sparkNote = "\"Proof of human error is what makes poetry hurt.\""
        )
    )

    fun getDilemmaSparks(dilemmaId: String): List<SparkThought> = listOf(
        SparkThought(
            id = "spark_1",
            dilemmaId = dilemmaId,
            authorName = "Maya Chen",
            authorHandle = "@mayachen",
            stance = "Forgiveness over Memory",
            thought = "If we can never truly let a slight vanish into the haze of time, grudges become immortal. Humans evolved with imperfect memory for a reason.",
            timestamp = "12m ago",
            likesCount = 42
        ),
        SparkThought(
            id = "spark_2",
            dilemmaId = dilemmaId,
            authorName = "Julian Thorne",
            authorHandle = "@j_thorne",
            stance = "Flawless Recall",
            thought = "Gaslighting becomes structurally impossible when the timeline is cryptographically verified. Intimacy without confusion.",
            timestamp = "34m ago",
            likesCount = 27
        ),
        SparkThought(
            id = "spark_3",
            dilemmaId = dilemmaId,
            authorName = "Sora Vance",
            authorHandle = "@soravance",
            stance = "Forgiveness over Memory",
            thought = "Grace isn't an algorithm; it's choosing to release what you could otherwise hold onto.",
            timestamp = "1h ago",
            likesCount = 89
        )
    )
}
