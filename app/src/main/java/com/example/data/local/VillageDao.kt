package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ActivityLogEntity
import com.example.data.model.BuildingPlotEntity
import com.example.data.model.CardEntity
import com.example.data.model.HeroEntity
import com.example.data.model.MissionEntity
import com.example.data.model.SystemMetricEntity
import com.example.data.model.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VillageDao {
    // User Profile
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    suspend fun getUserProfileOnce(): UserProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: UserProfileEntity)

    // Buildings
    @Query("SELECT * FROM building_plots ORDER BY id ASC")
    fun getAllBuildingPlots(): Flow<List<BuildingPlotEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuildingPlots(plots: List<BuildingPlotEntity>)

    @Update
    suspend fun updateBuildingPlot(plot: BuildingPlotEntity)

    // Heroes
    @Query("SELECT * FROM heroes ORDER BY id ASC")
    fun getAllHeroes(): Flow<List<HeroEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeroes(heroes: List<HeroEntity>)

    @Update
    suspend fun updateHero(hero: HeroEntity)

    // Cards
    @Query("SELECT * FROM heritage_cards ORDER BY id ASC")
    fun getAllCards(): Flow<List<CardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(cards: List<CardEntity>)

    @Update
    suspend fun updateCard(card: CardEntity)

    // Missions
    @Query("SELECT * FROM daily_missions ORDER BY id ASC")
    fun getAllMissions(): Flow<List<MissionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMissions(missions: List<MissionEntity>)

    @Update
    suspend fun updateMission(mission: MissionEntity)

    // Activity Logs
    @Query("SELECT * FROM activity_logs ORDER BY timestamp DESC LIMIT 40")
    fun getRecentLogs(): Flow<List<ActivityLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: ActivityLogEntity)

    // System Metrics
    @Query("SELECT * FROM system_metrics")
    fun getSystemMetrics(): Flow<List<SystemMetricEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSystemMetrics(metrics: List<SystemMetricEntity>)

    @Update
    suspend fun updateSystemMetric(metric: SystemMetricEntity)
}
