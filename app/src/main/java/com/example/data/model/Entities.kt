package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val playerName: String = "Chieftain Bharat",
    val playerTag: String = "#VR-9428-IN",
    val coins: Long = 12450L,
    val gems: Int = 85,
    val energy: Int = 22,
    val maxEnergy: Int = 25,
    val stars: Int = 18,
    val maxStars: Int = 25,
    val villageIndex: Int = 1,
    val villageName: String = "Green Village",
    val villageRegion: String = "Punjab Wheatlands",
    val activeHeroId: String = "veer",
    val bgmVolume: Float = 0.8f,
    val sfxVolume: Float = 0.9f,
    val hapticsEnabled: Boolean = true,
    val targetFps: Int = 60,
    val graphicsQuality: String = "High ✨",
    val batterySaver: Boolean = false,
    val language: String = "en",
    val userRole: String = "Chieftain (Admin)", // RBAC: "Chieftain (Admin)", "Village Elder (Moderator)", "Runner (Player)"
    val lastSyncTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "building_plots")
data class BuildingPlotEntity(
    @PrimaryKey val id: String,
    val name: String,
    val level: Int,
    val maxLevel: Int = 5,
    val flavor: String,
    val incomePerHour: Int,
    val nextIncomeDelta: Int,
    val upgradeCostCoins: Int,
    val iconName: String,
    val villageIndex: Int = 1,
    val isUnlocked: Boolean = true
)

@Entity(tableName = "heroes")
data class HeroEntity(
    @PrimaryKey val id: String,
    val name: String,
    val title: String,
    val level: Int,
    val maxLevel: Int = 10,
    val skillName: String,
    val skillType: String = "Passive",
    val skillDescription: String,
    val speedVal: String,
    val speedRatio: Float,
    val magnetVal: String,
    val magnetRatio: Float,
    val energyVal: String,
    val energyRatio: Float,
    val shardsReady: Int,
    val shardsRequired: Int,
    val upgradeCostCoins: Int,
    val isUnlocked: Boolean,
    val unlockCondition: String
)

@Entity(tableName = "heritage_cards")
data class CardEntity(
    @PrimaryKey val id: String,
    val setName: String = "Punjab Harvest",
    val title: String,
    val rarity: String, // Common, Rare, Epic, Legendary
    val level: Int,
    val bonusDescription: String,
    val ownedCopies: Int,
    val requiredCopies: Int,
    val isMaxLevel: Boolean = false,
    val isLocked: Boolean = false,
    val category: String, // Villages, Farming, Artisans, Festivals
    val imageUrl: String
)

@Entity(tableName = "daily_missions")
data class MissionEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val currentProgress: Int,
    val targetProgress: Int,
    val rewardType: String, // "coins", "gems"
    val rewardAmount: Int,
    val isClaimed: Boolean = false,
    val category: String = "daily" // daily, badges, weekly
)

@Entity(tableName = "activity_logs")
data class ActivityLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val action: String,
    val details: String,
    val severity: String = "INFO", // INFO, SUCCESS, WARNING, ALERT
    val userRole: String = "Chieftain"
)

@Entity(tableName = "system_metrics")
data class SystemMetricEntity(
    @PrimaryKey val id: String,
    val metricName: String,
    val currentValue: String,
    val status: String = "OPTIMAL", // OPTIMAL, STABLE, WARNING
    val lastChecked: Long = System.currentTimeMillis()
)
