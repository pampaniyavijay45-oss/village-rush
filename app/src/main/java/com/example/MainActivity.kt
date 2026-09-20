package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.components.BottomNavBar
import com.example.ui.components.TopChieftainHeader
import com.example.ui.components.TutorialOverlay
import com.example.ui.navigation.Screen
import com.example.ui.screens.BazaarScreen
import com.example.ui.screens.CardsScreen
import com.example.ui.screens.CoinDashScreen
import com.example.ui.screens.HeroesScreen
import com.example.ui.screens.MissionsScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.VictoryScreen
import com.example.ui.screens.VillageHubScreen
import com.example.ui.theme.VillageRushTheme
import com.example.ui.viewmodel.VillageViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VillageRushTheme {
                VillageRushApp()
            }
        }
    }
}

@Composable
fun VillageRushApp(
    viewModel: VillageViewModel = viewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Village.route

    val profile by viewModel.profile.collectAsStateWithLifecycle()
    val buildings by viewModel.buildings.collectAsStateWithLifecycle()
    val heroes by viewModel.heroes.collectAsStateWithLifecycle()
    val cards by viewModel.cards.collectAsStateWithLifecycle()
    val missions by viewModel.missions.collectAsStateWithLifecycle()
    val activityLogs by viewModel.activityLogs.collectAsStateWithLifecycle()
    val systemMetrics by viewModel.systemMetrics.collectAsStateWithLifecycle()
    val selectedBuilding by viewModel.selectedBuilding.collectAsStateWithLifecycle()
    val lastRunResult by viewModel.lastRunResult.collectAsStateWithLifecycle()
    val showTutorial by viewModel.showTutorial.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Listen for one-time toast events from ViewModel
    LaunchedEffect(Unit) {
        viewModel.toastEvent.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    // Determine if bars should be displayed
    val isGamePlayScreen = currentRoute == Screen.CoinDash.route
    val isVictoryScreen = currentRoute == Screen.Victory.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (!isGamePlayScreen) {
                TopChieftainHeader(
                    profile = profile,
                    onSettingsClick = {
                        if (currentRoute != Screen.Settings.route) {
                            navController.navigate(Screen.Settings.route) {
                                launchSingleTop = true
                            }
                        }
                    },
                    onBazaarClick = {
                        if (currentRoute != Screen.Bazaar.route) {
                            navController.navigate(Screen.Bazaar.route) {
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (!isGamePlayScreen && !isVictoryScreen) {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Village.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Village.route,
                modifier = Modifier.fillMaxSize()
            ) {
                // 1. Village Hub Screen
                composable(Screen.Village.route) {
                    VillageHubScreen(
                        profile = profile,
                        buildings = buildings,
                        selectedBuilding = selectedBuilding,
                        onSelectBuilding = { plot -> viewModel.selectBuilding(plot) },
                        onUpgradeBuilding = { viewModel.upgradeSelectedBuilding() },
                        onQuickDashClick = {
                            navController.navigate(Screen.CoinDash.route) {
                                launchSingleTop = true
                            }
                        },
                        onCollectBarnYield = { yield -> viewModel.collectBarnYield(yield) },
                        onOpenTutorial = { viewModel.toggleTutorial(true) }
                    )
                }

                // 2. Cards Screen
                composable(Screen.Cards.route) {
                    CardsScreen(
                        cards = cards,
                        onTradeDuplicates = {
                            viewModel.emitToast("🔄 Duplicate cards traded: Received +300 Coins & 1 Crate Shard!")
                        },
                        onOpenDailyPack = {
                            viewModel.emitToast("🎁 Daily Pack Opened: Received Rare Card Shards & 250 Coins!")
                        }
                    )
                }

                // 3. Mini-Game: Coin Dash Screen
                composable(Screen.CoinDash.route) {
                    CoinDashScreen(
                        onRunFinished = { coins, distance, streak ->
                            viewModel.completeCoinDashRun(coins, distance, streak)
                            navController.navigate(Screen.Victory.route) {
                                popUpTo(Screen.CoinDash.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onAbortRun = {
                            navController.navigate(Screen.Village.route) {
                                popUpTo(Screen.Village.route) { inclusive = true }
                            }
                        }
                    )
                }

                // 4. Victory / Harvest Payout Screen
                composable(Screen.Victory.route) {
                    VictoryScreen(
                        result = lastRunResult,
                        onClaimAndReturn = {
                            viewModel.claimVictoryPayout()
                            navController.navigate(Screen.Village.route) {
                                popUpTo(Screen.Village.route) { inclusive = true }
                            }
                        },
                        onPlayAgain = {
                            viewModel.claimVictoryPayout()
                            navController.navigate(Screen.CoinDash.route) {
                                popUpTo(Screen.Victory.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )
                }

                // 5. Heroes Screen
                composable(Screen.Heroes.route) {
                    HeroesScreen(
                        profile = profile,
                        heroes = heroes,
                        onEquipHero = { heroId -> viewModel.equipHero(heroId) },
                        onUpgradeHero = { hero -> viewModel.upgradeHero(hero) }
                    )
                }

                // 6. Missions Screen
                composable(Screen.Missions.route) {
                    MissionsScreen(
                        missions = missions,
                        onClaimMission = { mission -> viewModel.claimMission(mission) },
                        onNavigateToPlay = {
                            navController.navigate(Screen.CoinDash.route) {
                                launchSingleTop = true
                            }
                        }
                    )
                }

                // 7. Bazaar Screen
                composable(Screen.Bazaar.route) {
                    BazaarScreen(
                        playerCoins = profile?.coins ?: 0L,
                        playerGems = profile?.gems ?: 0,
                        onClaimDailyShagun = { viewModel.claimDailyShagun() },
                        onBuyBundle = { title, costGems, coinsGiven, gemsExtra ->
                            viewModel.buyBazaarBundle(title, costGems, coinsGiven, gemsExtra)
                        },
                        onRefillEnergy = { amount, costGems ->
                            viewModel.refillEnergy(amount, costGems)
                        }
                    )
                }

                // 8. Settings & Real-Time Performance Screen
                composable(Screen.Settings.route) {
                    SettingsScreen(
                        profile = profile,
                        activityLogs = activityLogs,
                        systemMetrics = systemMetrics,
                        onSaveSettings = { name, bgm, sfx, haptics, fps, quality, battery, lang, role ->
                            viewModel.updateSettings(name, bgm, sfx, haptics, fps, quality, battery, lang, role)
                        },
                        onResetProgress = { viewModel.resetRunProgress() }
                    )
                }
            }

            // Royal Panchayat Tutorial Overlay
            if (showTutorial) {
                TutorialOverlay(
                    onDismiss = { viewModel.toggleTutorial(false) },
                    onStartBuilding = {
                        viewModel.toggleTutorial(false)
                        val mudHouse = buildings.find { it.id == "mud_house_1" }
                        if (mudHouse != null) {
                            viewModel.selectBuilding(mudHouse)
                        }
                    }
                )
            }
        }
    }
}
