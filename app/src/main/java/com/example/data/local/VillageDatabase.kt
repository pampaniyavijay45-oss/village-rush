package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.ActivityLogEntity
import com.example.data.model.BuildingPlotEntity
import com.example.data.model.CardEntity
import com.example.data.model.HeroEntity
import com.example.data.model.MissionEntity
import com.example.data.model.SystemMetricEntity
import com.example.data.model.UserProfileEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserProfileEntity::class,
        BuildingPlotEntity::class,
        HeroEntity::class,
        CardEntity::class,
        MissionEntity::class,
        ActivityLogEntity::class,
        SystemMetricEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class VillageDatabase : RoomDatabase() {
    abstract fun villageDao(): VillageDao

    companion object {
        @Volatile
        private var INSTANCE: VillageDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): VillageDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VillageDatabase::class.java,
                    "village_rush_database"
                )
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialData(database.villageDao())
                    }
                }
            }

            suspend fun populateInitialData(dao: VillageDao) {
                // Initial Profile
                dao.insertOrUpdateProfile(
                    UserProfileEntity(
                        id = 1,
                        playerName = "Chieftain Bharat",
                        playerTag = "#VR-9428-IN",
                        coins = 12450L,
                        gems = 85,
                        energy = 22,
                        maxEnergy = 25,
                        stars = 18,
                        maxStars = 25,
                        villageIndex = 1,
                        villageName = "Green Village",
                        villageRegion = "Punjab Wheatlands",
                        activeHeroId = "veer",
                        bgmVolume = 0.8f,
                        sfxVolume = 0.9f,
                        hapticsEnabled = true,
                        targetFps = 60,
                        graphicsQuality = "High ✨",
                        batterySaver = false,
                        language = "en",
                        userRole = "Chieftain (Admin)"
                    )
                )

                // 5 Village Buildings matching mockups
                val plots = listOf(
                    BuildingPlotEntity(
                        id = "desi_house",
                        name = "Desi House",
                        level = 2,
                        maxLevel = 5,
                        flavor = "Clay hut with carved wooden door & courtyard.",
                        incomePerHour = 240,
                        nextIncomeDelta = 120,
                        upgradeCostCoins = 775,
                        iconName = "cottage",
                        villageIndex = 1,
                        isUnlocked = true
                    ),
                    BuildingPlotEntity(
                        id = "farm_plot",
                        name = "Punjab Farm",
                        level = 1,
                        maxLevel = 5,
                        flavor = "Golden wheat field with tubewell & scarecrow.",
                        incomePerHour = 180,
                        nextIncomeDelta = 140,
                        upgradeCostCoins = 600,
                        iconName = "agriculture",
                        villageIndex = 1,
                        isUnlocked = true
                    ),
                    BuildingPlotEntity(
                        id = "bazaar_stall",
                        name = "Village Bazaar",
                        level = 1,
                        maxLevel = 5,
                        flavor = "Festive awning with spices & handmade brass pots.",
                        incomePerHour = 310,
                        nextIncomeDelta = 190,
                        upgradeCostCoins = 920,
                        iconName = "storefront",
                        villageIndex = 1,
                        isUnlocked = true
                    ),
                    BuildingPlotEntity(
                        id = "artisan_shop",
                        name = "Artisan Workshop",
                        level = 0,
                        maxLevel = 5,
                        flavor = "Foundation plot ready for woodcraft and anvil.",
                        incomePerHour = 0,
                        nextIncomeDelta = 220,
                        upgradeCostCoins = 1100,
                        iconName = "carpenter",
                        villageIndex = 1,
                        isUnlocked = true
                    ),
                    BuildingPlotEntity(
                        id = "panchayat_hall",
                        name = "Panchayat Bhawan",
                        level = 0,
                        maxLevel = 5,
                        flavor = "Village Council pavilion atop stepped stone plinth.",
                        incomePerHour = 0,
                        nextIncomeDelta = 400,
                        upgradeCostCoins = 2500,
                        iconName = "account_balance",
                        villageIndex = 1,
                        isUnlocked = false
                    )
                )
                dao.insertBuildingPlots(plots)

                // 5 Heroes matching Hero Roster mockup
                val heroes = listOf(
                    HeroEntity(
                        id = "veer",
                        name = "Veer",
                        title = "Punjab Field Chieftain",
                        level = 4,
                        maxLevel = 10,
                        skillName = "Harvest Surge",
                        skillType = "Passive",
                        skillDescription = "2x Coin value for 6 seconds during high-speed Lane Dash.",
                        speedVal = "380 m/s",
                        speedRatio = 0.78f,
                        magnetVal = "8.4 m",
                        magnetRatio = 0.85f,
                        energyVal = "140 AP",
                        energyRatio = 0.65f,
                        shardsReady = 2,
                        shardsRequired = 2,
                        upgradeCostCoins = 500,
                        isUnlocked = true,
                        unlockCondition = "Default Hero"
                    ),
                    HeroEntity(
                        id = "simran",
                        name = "Simran",
                        title = "Village Artisan",
                        level = 2,
                        maxLevel = 10,
                        skillName = "Craft Boost",
                        skillType = "Passive",
                        skillDescription = "15% discount on all Village Workshop building upgrades.",
                        speedVal = "340 m/s",
                        speedRatio = 0.60f,
                        magnetVal = "10.2 m",
                        magnetRatio = 0.95f,
                        energyVal = "120 AP",
                        energyRatio = 0.55f,
                        shardsReady = 1,
                        shardsRequired = 2,
                        upgradeCostCoins = 350,
                        isUnlocked = true,
                        unlockCondition = "Unlocked"
                    ),
                    HeroEntity(
                        id = "kabir",
                        name = "Kabir",
                        title = "Bazaar Merchant",
                        level = 1,
                        maxLevel = 10,
                        skillName = "Fortune Merchant",
                        skillType = "Active",
                        skillDescription = "Awards an extra +500 Coins on every Rush Victory.",
                        speedVal = "320 m/s",
                        speedRatio = 0.52f,
                        magnetVal = "7.0 m",
                        magnetRatio = 0.68f,
                        energyVal = "160 AP",
                        energyRatio = 0.80f,
                        shardsReady = 1,
                        shardsRequired = 3,
                        upgradeCostCoins = 200,
                        isUnlocked = true,
                        unlockCondition = "Unlocked"
                    ),
                    HeroEntity(
                        id = "rani",
                        name = "Rani",
                        title = "Royal Archer",
                        level = 1,
                        maxLevel = 10,
                        skillName = "Eagle Strike",
                        skillType = "Active",
                        skillDescription = "Pierces through 3 consecutive roadblock obstacles.",
                        speedVal = "410 m/s",
                        speedRatio = 0.90f,
                        magnetVal = "6.0 m",
                        magnetRatio = 0.55f,
                        energyVal = "110 AP",
                        energyRatio = 0.50f,
                        shardsReady = 0,
                        shardsRequired = 5,
                        upgradeCostCoins = 750,
                        isUnlocked = false,
                        unlockCondition = "Unlock at Village 3"
                    ),
                    HeroEntity(
                        id = "bheema",
                        name = "Bheema",
                        title = "Strongman Wrestler",
                        level = 1,
                        maxLevel = 10,
                        skillName = "Earth Tremor",
                        skillType = "Ultimate",
                        skillDescription = "Smashes nearby obstacles in a 4-meter radius.",
                        speedVal = "310 m/s",
                        speedRatio = 0.48f,
                        magnetVal = "5.5 m",
                        magnetRatio = 0.45f,
                        energyVal = "200 AP",
                        energyRatio = 1.0f,
                        shardsReady = 0,
                        shardsRequired = 5,
                        upgradeCostCoins = 1000,
                        isUnlocked = false,
                        unlockCondition = "20 Gems"
                    )
                )
                dao.insertHeroes(heroes)

                // 5 Cards matching Cards Album mockup
                val cards = listOf(
                    CardEntity(
                        id = "card_wheat",
                        setName = "Punjab Harvest",
                        title = "Wheat Stalk",
                        rarity = "Common",
                        level = 3,
                        bonusDescription = "+5% Farm bonus",
                        ownedCopies = 3,
                        requiredCopies = 5,
                        isMaxLevel = false,
                        isLocked = false,
                        category = "Farming",
                        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAUWPsOHphWMQWNn8T6qdpaphRtE9UEAFzpIwvmEzT-l_RlMYfzeWaAGIB14nW94e_LTDTSVWljsr_XJ8ienVLn6s1mOZ6yTsZQgRNMxfYvGNEqnuIg7wKG2Ce-t-mF-U_gYGyimRYEAb5r_YsKAbBv_VeSpldt5RUQVgRuDDyhKDDrBQM4hQoOk-toy7kFU87CIKpgCXzevxLdJXGwdE_GSU7wYY_iBUyTTInePOjacxMdv4CSWpq6QQ"
                    ),
                    CardEntity(
                        id = "card_lassi",
                        setName = "Punjab Harvest",
                        title = "Brass Lassi",
                        rarity = "Rare",
                        level = 2,
                        bonusDescription = "+10% Energy recharge",
                        ownedCopies = 2,
                        requiredCopies = 4,
                        isMaxLevel = false,
                        isLocked = false,
                        category = "Villages",
                        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAHb8HNXo8tvRM5fP1GvelluiPaw2rLkd4vtHaLSBz4g6bRLYSbeol1LVJUGeJDWp1Fr_OYKSPE6gKhMPiuvNsedp4v0sOqaonTjD7vWTT4KUlDFWl7ONZUTBCj_vHAaDtFgzZz5p-eyjPigt9XcMNXBKtzqylgRgq5ou8q5JhFB0vq3jexo65IVKiDViL4XYvsBENztxxdvQJlNv9Xsety5TWsvEB7BS2w5tDRE3ZA7wYAml2UVlnC9Q"
                    ),
                    CardEntity(
                        id = "card_turban",
                        setName = "Punjab Harvest",
                        title = "Royal Turban",
                        rarity = "Epic",
                        level = 1,
                        bonusDescription = "+15% Dash Coins",
                        ownedCopies = 1,
                        requiredCopies = 2,
                        isMaxLevel = false,
                        isLocked = false,
                        category = "Festivals",
                        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuArHYmXQB9PDCO9ZRgJs6iYhztlyd_ET8o5QW593RD2ZWM3p6OHoUsTCqGKYuJFyARwdaPf3CZtoED3U6-Ei9CUVpSqSpxtUK1-yOi6JIXD06U47PUPQELOj_8LZZpHYaPxuP4rxbbQuLLEkTOsXhc1iPComtRh2itphkn5KkDRmiw9TvbQWO-hHqqUH_4Cr9rJoLrIpcObYt3AjTK-Q45nyujJcWVBKTJXzv9lgAitiujo0nqpTsWJxQ"
                    ),
                    CardEntity(
                        id = "card_cart",
                        setName = "Punjab Harvest",
                        title = "Bullock Cart",
                        rarity = "Common",
                        level = 4,
                        bonusDescription = "+8% Dodge Score",
                        ownedCopies = 5,
                        requiredCopies = 5,
                        isMaxLevel = true,
                        isLocked = false,
                        category = "Artisans",
                        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAimKBFB43SkDOwjDMXFTLtwe7rIV793zDKOvNdLu19TCEEBR4Gl6_ful_Szw-SQvcrDa30-rgEu6tsw9sjnO4_gATf1S4VE4gdjPqucCyajKvMtgLqQOJiLZlx3ZNygpcLLswfG3gCrUFEr189SgJEq7bS2mlvSJr-aSwvv_zTXDQv5gjsSWF_mPKgVatdcXv6ld01Xz_bvY0ddThboZoHqAHUWL5kWcl6qpM_i2YCMSOrT5wcQ3E8mg"
                    ),
                    CardEntity(
                        id = "card_dhol",
                        setName = "Punjab Harvest",
                        title = "Baisakhi Dhol",
                        rarity = "Legendary",
                        level = 0,
                        bonusDescription = "Found in Grand Village Crates",
                        ownedCopies = 0,
                        requiredCopies = 1,
                        isMaxLevel = false,
                        isLocked = true,
                        category = "Festivals",
                        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuA8gRrJ1juO7GDY7-5l-eloF4CwAXD31c4ejpdLZu8gcp1fjQYKPWYI430gBvYMi50dBZ-eGt2Q88GWCGgMLvMRQcig09xTq9pO59NxpCmraQhoU3__O8Iy6aX5dZVPEOLKLH_l4_nr5YFhzDLLBHogvMvcd9Jj_5qQZgK8v47SAATVF6GtoRlHDpvcZyx8aQyRBQM1GR90Q5F6l-Y5BgFtcO06OGAhEE0cdOEbGUjLh0LqOE6j2_qQ-Q"
                    )
                )
                dao.insertCards(cards)

                // Daily Missions matching Panchayat Board mockup
                val missions = listOf(
                    MissionEntity(
                        id = "mission_builder",
                        title = "Village Builder",
                        description = "Upgrade any building in Green Village 2 times.",
                        icon = "holiday_village",
                        currentProgress = 2,
                        targetProgress = 2,
                        rewardType = "coins",
                        rewardAmount = 1500,
                        isClaimed = false,
                        category = "daily"
                    ),
                    MissionEntity(
                        id = "mission_dasher",
                        title = "Coin Dasher",
                        description = "Collect 50 gold coins in Coin Dash mini-game.",
                        icon = "toll",
                        currentProgress = 38,
                        targetProgress = 50,
                        rewardType = "coins",
                        rewardAmount = 500,
                        isClaimed = false,
                        category = "daily"
                    ),
                    MissionEntity(
                        id = "mission_dodge",
                        title = "Dodge Master",
                        description = "Avoid 5 village obstacles without crashing in one run.",
                        icon = "sprint",
                        currentProgress = 5,
                        targetProgress = 5,
                        rewardType = "gems",
                        rewardAmount = 5,
                        isClaimed = false,
                        category = "daily"
                    ),
                    MissionEntity(
                        id = "mission_energy",
                        title = "Energy Saver",
                        description = "Complete 3 mini-game runs today.",
                        icon = "sports_martial_arts",
                        currentProgress = 1,
                        targetProgress = 3,
                        rewardType = "energy",
                        rewardAmount = 5,
                        isClaimed = false,
                        category = "daily"
                    )
                )
                dao.insertMissions(missions)

                // Initial Activity Logs for monitoring & anomalies
                val initialLogs = listOf(
                    ActivityLogEntity(
                        action = "SESSION_INITIALIZED",
                        details = "Village Rush session started. AES-256 room storage verified.",
                        severity = "INFO",
                        userRole = "Chieftain"
                    ),
                    ActivityLogEntity(
                        action = "RBAC_ROLE_VERIFIED",
                        details = "User authenticated with Chieftain (Admin) privilege clearance.",
                        severity = "SUCCESS",
                        userRole = "Chieftain"
                    ),
                    ActivityLogEntity(
                        action = "HARVEST_INCOME_SYNCED",
                        details = "Offline village passive income calculated: +480 coins accrued.",
                        severity = "INFO",
                        userRole = "Chieftain"
                    ),
                    ActivityLogEntity(
                        action = "INTEGRITY_SCAN_COMPLETED",
                        details = "No abnormal memory mutations or coin tampered logs detected.",
                        severity = "SUCCESS",
                        userRole = "System"
                    )
                )
                initialLogs.forEach { dao.insertLog(it) }

                // Initial System Metrics
                val metrics = listOf(
                    SystemMetricEntity("fps", "Render Frame Rate", "60 FPS", "OPTIMAL"),
                    SystemMetricEntity("memory_mb", "Heap RAM Usage", "64 MB", "OPTIMAL"),
                    SystemMetricEntity("latency_ms", "Cloud Sync Latency", "32 ms", "OPTIMAL"),
                    SystemMetricEntity("db_query_ms", "Room SQLite Latency", "1.4 ms", "OPTIMAL"),
                    SystemMetricEntity("security_status", "Encryption & Security", "AES-256 Validated", "OPTIMAL")
                )
                dao.insertSystemMetrics(metrics)
            }
        }
    }
}
