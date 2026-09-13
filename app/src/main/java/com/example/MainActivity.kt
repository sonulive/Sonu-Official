package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.WavelengthDatabase
import com.example.data.model.Dilemma
import com.example.data.repository.WavelengthRepository
import com.example.ui.components.NavItem
import com.example.ui.components.ProUpgradeModal
import com.example.ui.components.ShareableAuraModal
import com.example.ui.components.SparksBottomSheet
import com.example.ui.components.WavelengthBottomNav
import com.example.ui.components.WavelengthTopBar
import com.example.ui.screens.CirclesScreen
import com.example.ui.screens.CreateScreen
import com.example.ui.screens.DiscoverScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.theme.WavelengthObsidian
import com.example.ui.theme.WavelengthTheme
import com.example.ui.viewmodel.WavelengthViewModel
import com.example.ui.viewmodel.WavelengthViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = WavelengthDatabase.getInstance(applicationContext)
        val repository = WavelengthRepository(database.wavelengthDao())
        val factory = WavelengthViewModelFactory(repository)

        setContent {
            WavelengthTheme {
                val viewModel: WavelengthViewModel = viewModel(factory = factory)
                WavelengthApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun WavelengthApp(viewModel: WavelengthViewModel) {
    val hasCompletedOnboarding by viewModel.hasCompletedOnboarding.collectAsState()
    val dilemmas by viewModel.dilemmas.collectAsState()
    val dailyDilemma by viewModel.dailyFeatured.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val activeSparksDilemma by viewModel.activeSparksDilemma.collectAsState()
    val sharingDilemma by viewModel.sharingDilemma.collectAsState()
    val showProModal by viewModel.showProModal.collectAsState()
    val friends = remember { viewModel.getSyncFriends() }

    var currentRoute by remember { mutableStateOf(NavItem.Sync.route) }

    if (!hasCompletedOnboarding) {
        OnboardingScreen(
            onFinishOnboarding = {
                viewModel.setOnboardingCompleted(true)
            }
        )
        return
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = WavelengthObsidian,
        topBar = {
            WavelengthTopBar(
                streakDays = userProfile?.streakDays ?: 12,
                isPro = userProfile?.isProMember ?: false,
                onProClick = { viewModel.showProUpgrade(true) }
            )
        },
        bottomBar = {
            WavelengthBottomNav(
                currentRoute = currentRoute,
                onNavigate = { route -> currentRoute = route }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentRoute,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "screen_route_anim"
            ) { targetRoute ->
                when (targetRoute) {
                    NavItem.Sync.route -> {
                        HomeScreen(
                            dailyDilemma = dailyDilemma,
                            recentDilemmas = dilemmas,
                            userProfile = userProfile,
                            friends = friends,
                            onVote = { id, option -> viewModel.castVote(id, option) },
                            onOpenSparks = { dilemma -> viewModel.openSparks(dilemma) },
                            onShare = { dilemma -> viewModel.openShareModal(dilemma) },
                            onNavigateToCircles = { currentRoute = NavItem.Circles.route }
                        )
                    }
                    NavItem.Discover.route -> {
                        DiscoverScreen(
                            dilemmas = dilemmas,
                            selectedCategory = selectedCategory,
                            onSelectCategory = { cat -> viewModel.selectCategory(cat) },
                            onVote = { id, option -> viewModel.castVote(id, option) },
                            onOpenSparks = { dilemma -> viewModel.openSparks(dilemma) },
                            onShare = { dilemma -> viewModel.openShareModal(dilemma) }
                        )
                    }
                    NavItem.Create.route -> {
                        CreateScreen(
                            onDilemmaCreated = { title, context, category, optionA, optionB ->
                                viewModel.createDilemma(title, context, category, optionA, optionB) {
                                    currentRoute = NavItem.Sync.route
                                }
                            }
                        )
                    }
                    NavItem.Circles.route -> {
                        CirclesScreen(
                            friends = friends,
                            onInviteFriends = {
                                if (dailyDilemma != null) {
                                    viewModel.openShareModal(dailyDilemma!!)
                                }
                            }
                        )
                    }
                    NavItem.Profile.route -> {
                        ProfileScreen(
                            userProfile = userProfile,
                            onProUpgradeClick = { viewModel.showProUpgrade(true) }
                        )
                    }
                    else -> {
                        HomeScreen(
                            dailyDilemma = dailyDilemma,
                            recentDilemmas = dilemmas,
                            userProfile = userProfile,
                            friends = friends,
                            onVote = { id, option -> viewModel.castVote(id, option) },
                            onOpenSparks = { dilemma -> viewModel.openSparks(dilemma) },
                            onShare = { dilemma -> viewModel.openShareModal(dilemma) },
                            onNavigateToCircles = { currentRoute = NavItem.Circles.route }
                        )
                    }
                }
            }

            // Shareable Aura Card Modal
            sharingDilemma?.let { dilemma ->
                userProfile?.let { profile ->
                    ShareableAuraModal(
                        dilemma = dilemma,
                        userProfile = profile,
                        onDismiss = { viewModel.closeShareModal() }
                    )
                }
            }

            // Sparks Perspective Discussion Sheet
            activeSparksDilemma?.let { dilemma ->
                SparksBottomSheet(
                    dilemma = dilemma,
                    initialSparks = viewModel.getDilemmaSparks(dilemma.id),
                    onDismiss = { viewModel.closeSparks() }
                )
            }

            // Pro / Wavelength Black Upgrade Modal
            if (showProModal) {
                ProUpgradeModal(
                    isCurrentlyPro = userProfile?.isProMember ?: false,
                    onTogglePro = { viewModel.toggleProSubscription() },
                    onDismiss = { viewModel.showProUpgrade(false) }
                )
            }
        }
    }
}
