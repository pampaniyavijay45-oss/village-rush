package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.VillageDatabase
import com.example.data.model.ActivityLogEntity
import com.example.data.model.BuildingPlotEntity
import com.example.data.model.CardEntity
import com.example.data.model.HeroEntity
import com.example.data.model.MissionEntity
import com.example.data.model.SystemMetricEntity
import com.example.data.model.UserProfileEntity
import com.example.data.repository.VillageRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class MiniGameRunResult(
    val distanceMeters: Int = 248,
    val coinsGathered: Int = 38,
    val coinsPayout: Int = 1900,
    val matchScore: Int = 9640,
    val comboStreak: Int = 18,
    val streakBonus: Int = 1350,
    val baseBonus: Int = 500,
    val gemsFound: Int = 2,
    val totalCoins: Long = 13390L
)

class VillageViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: VillageRepository
    
    val profile: StateFlow<UserProfileEntity?>
    val buildings: StateFlow<List<BuildingPlotEntity>>
    val heroes: StateFlow<List<HeroEntity>>
    val cards: StateFlow<List<CardEntity>>
    val missions: StateFlow<List<MissionEntity>>
    val activityLogs: StateFlow<List<ActivityLogEntity>>
    val systemMetrics: StateFlow<List<SystemMetricEntity>>

    // Selected building for inspection / upgrade dialog
    private val _selectedBuilding = MutableStateFlow<BuildingPlotEntity?>(null)
    val selectedBuilding: StateFlow<BuildingPlotEntity?> = _selectedBuilding.asStateFlow()

    // Last completed mini-game run results
    private val _lastRunResult = MutableStateFlow(MiniGameRunResult())
    val lastRunResult: StateFlow<MiniGameRunResult> = _lastRunResult.asStateFlow()

    // Tutorial visibility
    private val _showTutorial = MutableStateFlow(false)
    val showTutorial: StateFlow<Boolean> = _showTutorial.asStateFlow()

    // Toast message trigger for UI feedback
    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent: SharedFlow<String> = _toastEvent.asSharedFlow()

    init {
        val database = VillageDatabase.getDatabase(application, viewModelScope)
        repository = VillageRepository(database.villageDao())

        profile = repository.userProfile.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null
        )
        buildings = repository.buildings.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
        heroes = repository.heroes.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
        cards = repository.cards.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
        missions = repository.missions.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
        activityLogs = repository.activityLogs.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
        systemMetrics = repository.systemMetrics.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

        // Periodic system health check simulation
        viewModelScope.launch {
            while (true) {
                delay(12000)
                repository.refreshSystemDiagnostics()
            }
        }
    }

    fun selectBuilding(plot: BuildingPlotEntity?) {
        _selectedBuilding.value = plot
    }

    fun upgradeSelectedBuilding() {
        val plot = _selectedBuilding.value ?: return
        viewModelScope.launch {
            val success = repository.upgradeBuilding(plot)
            if (success) {
                emitToast("🎉 ${plot.name} upgraded! +1 Village Star")
                _selectedBuilding.value = null
            } else {
                emitToast("Need ${plot.upgradeCostCoins} Coins to upgrade!")
            }
        }
    }

    fun collectBarnYield(coins: Long) {
        viewModelScope.launch {
            repository.addCoins(coins)
            emitToast("🌾 Grain harvested: +$coins Coins!")
        }
    }

    fun claimMission(mission: MissionEntity) {
        viewModelScope.launch {
            repository.claimMission(mission)
            emitToast("✅ Claimed +${mission.rewardAmount} ${mission.rewardType}!")
        }
    }

    fun equipHero(heroId: String) {
        viewModelScope.launch {
            repository.equipHero(heroId)
            emitToast("👑 Chieftain equipped: ${heroId.replaceFirstChar { it.uppercase() }}")
        }
    }

    fun upgradeHero(hero: HeroEntity) {
        viewModelScope.launch {
            val success = repository.upgradeHero(hero)
            if (success) {
                emitToast("⚡ ${hero.name} upgraded to Level ${hero.level + 1}!")
            } else {
                emitToast("Requires ${hero.shardsRequired} Shards & ${hero.upgradeCostCoins} Coins!")
            }
        }
    }

    fun completeCoinDashRun(coinsCollected: Int, distanceMeters: Int, comboStreak: Int) {
        viewModelScope.launch {
            val coinsPayout = coinsCollected * 50
            val matchScore = distanceMeters * 38
            val streakBonus = comboStreak * 75
            val baseBonus = 500
            val gemsFound = 2
            val totalCoins = (coinsPayout + matchScore + streakBonus + baseBonus).toLong()

            _lastRunResult.value = MiniGameRunResult(
                distanceMeters = distanceMeters,
                coinsGathered = coinsCollected,
                coinsPayout = coinsPayout,
                matchScore = matchScore,
                comboStreak = comboStreak,
                streakBonus = streakBonus,
                baseBonus = baseBonus,
                gemsFound = gemsFound,
                totalCoins = totalCoins
            )

            // Deduct energy for run
            repository.consumeEnergy(5)
            // Log run
            repository.logAction("COIN_DASH_VICTORY", "Run finished: $distanceMeters meters, $totalCoins coins harvested.", "SUCCESS")
        }
    }

    fun claimVictoryPayout() {
        viewModelScope.launch {
            val result = _lastRunResult.value
            repository.addCoins(result.totalCoins)
            repository.addGems(result.gemsFound)
            emitToast("🏆 +${result.totalCoins} Coins & +${result.gemsFound} Gems deposited to treasury!")
        }
    }

    fun buyBazaarBundle(itemTitle: String, costGems: Int, coinsGiven: Long, gemsExtra: Int = 0) {
        viewModelScope.launch {
            val success = repository.spendGems(costGems)
            if (success) {
                if (coinsGiven > 0) repository.addCoins(coinsGiven)
                if (gemsExtra > 0) repository.addGems(gemsExtra)
                emitToast("🛍️ Acquired: $itemTitle!")
            } else {
                emitToast("Not enough Royal Gems!")
            }
        }
    }

    fun refillEnergy(amount: Int, costGems: Int = 0) {
        viewModelScope.launch {
            if (costGems > 0) {
                val ok = repository.spendGems(costGems)
                if (!ok) {
                    emitToast("Need $costGems Gems for energy refill!")
                    return@launch
                }
            }
            repository.refillEnergy(amount)
            emitToast("⚡ +$amount Energy restored!")
        }
    }

    fun claimDailyShagun() {
        viewModelScope.launch {
            repository.addCoins(500)
            emitToast("🎁 Village Elder's Shagun claimed: +500 Coins!")
        }
    }

    fun updateSettings(
        playerName: String,
        bgm: Float,
        sfx: Float,
        haptics: Boolean,
        fps: Int,
        quality: String,
        batterySaver: Boolean,
        language: String,
        role: String
    ) {
        viewModelScope.launch {
            repository.updateSettings(
                playerName, bgm, sfx, haptics, fps, quality, batterySaver, language, role
            )
            emitToast("⚙️ Settings and Role ($role) updated!")
        }
    }

    fun resetRunProgress() {
        viewModelScope.launch {
            repository.resetRunProgress()
            emitToast("Village run journey reset. Good luck on the trail!")
        }
    }

    fun toggleTutorial(show: Boolean) {
        _showTutorial.value = show
    }

    fun emitToast(message: String) {
        viewModelScope.launch {
            _toastEvent.emit(message)
        }
    }
}
