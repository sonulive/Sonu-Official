package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Dilemma
import com.example.data.model.FriendSync
import com.example.data.model.SparkThought
import com.example.data.model.UserProfile
import com.example.data.repository.WavelengthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WavelengthViewModel(
    private val repository: WavelengthRepository
) : ViewModel() {

    val dilemmas: StateFlow<List<Dilemma>> = repository.dilemmas.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val dailyFeatured: StateFlow<Dilemma?> = repository.dailyFeatured.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val userProfile: StateFlow<UserProfile?> = repository.userProfile.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    // Category filter for Discover screen
    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // Active dilemma for Sparks discussion sheet
    private val _activeSparksDilemma = MutableStateFlow<Dilemma?>(null)
    val activeSparksDilemma: StateFlow<Dilemma?> = _activeSparksDilemma.asStateFlow()

    // Active dilemma for Shareable Aura Modal
    private val _sharingDilemma = MutableStateFlow<Dilemma?>(null)
    val sharingDilemma: StateFlow<Dilemma?> = _sharingDilemma.asStateFlow()

    // Pro Upgrade Modal visibility
    private val _showProModal = MutableStateFlow(false)
    val showProModal: StateFlow<Boolean> = _showProModal.asStateFlow()

    // Onboarding completed flag
    private val _hasCompletedOnboarding = MutableStateFlow(true)
    val hasCompletedOnboarding: StateFlow<Boolean> = _hasCompletedOnboarding.asStateFlow()

    init {
        viewModelScope.launch {
            repository.initializeSeedDataIfNeeded()
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun castVote(dilemmaId: String, option: Int) {
        viewModelScope.launch {
            repository.castVote(dilemmaId, option)
        }
    }

    fun openSparks(dilemma: Dilemma) {
        _activeSparksDilemma.value = dilemma
    }

    fun closeSparks() {
        _activeSparksDilemma.value = null
    }

    fun openShareModal(dilemma: Dilemma) {
        _sharingDilemma.value = dilemma
    }

    fun closeShareModal() {
        _sharingDilemma.value = null
    }

    fun showProUpgrade(show: Boolean) {
        _showProModal.value = show
    }

    fun toggleProSubscription() {
        val current = userProfile.value ?: return
        viewModelScope.launch {
            repository.toggleProMembership(current)
            _showProModal.value = false
        }
    }

    fun getSyncFriends(): List<FriendSync> {
        return repository.getSyncFriends()
    }

    fun getDilemmaSparks(dilemmaId: String): List<SparkThought> {
        return repository.getDilemmaSparks(dilemmaId)
    }

    fun createDilemma(
        title: String,
        context: String,
        category: String,
        optionA: String,
        optionB: String,
        onComplete: () -> Unit
    ) {
        viewModelScope.launch {
            val author = userProfile.value?.name ?: "Kaelen Voss"
            repository.createDilemma(
                title = title,
                context = context,
                category = category,
                optionA = optionA,
                optionB = optionB,
                authorName = author
            )
            onComplete()
        }
    }

    fun setOnboardingCompleted(completed: Boolean) {
        _hasCompletedOnboarding.value = completed
    }
}
