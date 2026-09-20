package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Carpenter
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cottage
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Upgrade
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.BuildingPlotEntity
import com.example.data.model.UserProfileEntity
import com.example.ui.components.BuildingUpgradeDialog
import com.example.ui.theme.CardamomSurfaceContainer
import com.example.ui.theme.EmeraldSecondary
import com.example.ui.theme.EmeraldSecondaryContainer
import com.example.ui.theme.OnEmeraldSecondary
import com.example.ui.theme.OnEmeraldSecondaryContainer
import com.example.ui.theme.OnSaffronPrimary
import com.example.ui.theme.OnSaffronPrimaryContainer
import com.example.ui.theme.OutlineVariantClay
import com.example.ui.theme.RoyalBlueTertiary
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.SaffronPrimaryContainer
import com.example.ui.theme.SaffronPrimaryFixed

@Composable
fun VillageHubScreen(
    profile: UserProfileEntity?,
    buildings: List<BuildingPlotEntity>,
    selectedBuilding: BuildingPlotEntity?,
    onSelectBuilding: (BuildingPlotEntity?) -> Unit,
    onUpgradeBuilding: () -> Unit,
    onQuickDashClick: () -> Unit,
    onCollectBarnYield: (Long) -> Unit,
    onOpenTutorial: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showWorldMapDialog by remember { mutableStateOf(false) }
    var showInspectModal by remember { mutableStateOf(false) }

    // Upgrade Dialog
    if (showInspectModal && selectedBuilding != null) {
        BuildingUpgradeDialog(
            plot = selectedBuilding,
            playerCoins = profile?.coins ?: 0L,
            onDismiss = { showInspectModal = false },
            onConfirmUpgrade = {
                onUpgradeBuilding()
                showInspectModal = false
            }
        )
    }

    // World Map Modal
    if (showWorldMapDialog) {
        AlertDialog(
            onDismissRequest = { showWorldMapDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Public, contentDescription = null, tint = SaffronPrimary)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Bharat Realm Map", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Village 1
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = EmeraldSecondaryContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldSecondary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("Village 1: Punjab Wheatlands", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text("Active Settlement • Green Village", fontSize = 11.sp)
                            }
                        }
                    }
                    // Village 2
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.outline)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("Village 2: Thar Desert Fort", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("Requires 25 Stars in Punjab", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                    // Village 3
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.outline)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("Village 3: Kerala Backwaters", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("Unlock at 50 Stars", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showWorldMapDialog = false }) {
                    Text("Stay in Punjab")
                }
            }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // 1. Village Header & Progress Ribbon
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "VILLAGE 1: PUNJAB WHEATLANDS",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = SaffronPrimary,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = profile?.villageName ?: "Green Village",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        // Map CTA Button
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surfaceContainerHigh,
                            modifier = Modifier
                                .clickable { showWorldMapDialog = true }
                                .testTag("world_map_button")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Map,
                                    contentDescription = "Map",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Map",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Village Stars and Completion Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = SaffronPrimaryContainer,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${profile?.stars ?: 18}/25 Stars",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Text(
                            text = "${((profile?.stars ?: 18) * 100 / 25)}% Complete",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldSecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    LinearProgressIndicator(
                        progress = { ((profile?.stars ?: 18).toFloat() / 25f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = EmeraldSecondary,
                        trackColor = MaterialTheme.colorScheme.surfaceContainerHigh
                    )
                }
            }
        }

        // 2. Interactive Isometric Village Viewport
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shadowElevation = 3.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                ) {
                    // Panoramic Village Landscape Background
                    AsyncImage(
                        model = "https://lh3.googleusercontent.com/aida-public/AB6AXuC3MAuw5luXC3KLeklDhBtBl8Oh1tbQO1JDnveLFXXxu9pct-oVsRY78fZM2ahcYHO5X2guYqRiOA1tUkrUQi8IDP9SD6heUMO0EFI8w_cQh8C0ALD9x5Qff5PdTRI9T1824apnHXXNWTotnH6UoTsWM_3igWdGXoBqbpl2wG3IQVHEQXMG02dV5LkqlGrgWPymgRbHGUlaj34OuppvZSCWXib68q05IaToNGc9oga_s8wRgkcfSHwd_w",
                        contentDescription = "Village Landscape",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp))
                    )

                    // Subtle Top Tint Overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.55f))
                                )
                            )
                    )

                    // Soil Fertility Badge Top Left
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.Black.copy(alpha = 0.65f),
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.WaterDrop,
                                contentDescription = null,
                                tint = EmeraldSecondaryContainer,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Soil Fertility: 94%",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    // Floating Royal Decree / Tutorial Button Top Right
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = SaffronPrimaryContainer,
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.TopEnd)
                            .clickable { onOpenTutorial() }
                            .testTag("royal_decree_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "📜 Decree",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSaffronPrimaryContainer
                            )
                        }
                    }

                    // Interactive Building Plots Grid over the Village
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(buildings) { plot ->
                                val isSelected = selectedBuilding?.id == plot.id
                                Surface(
                                    modifier = Modifier
                                        .clickable { onSelectBuilding(plot) }
                                        .testTag("plot_${plot.id}"),
                                    shape = RoundedCornerShape(14.dp),
                                    color = if (isSelected) SaffronPrimaryContainer else Color.Black.copy(alpha = 0.75f),
                                    border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, SaffronPrimaryFixed) else null
                                ) {
                                    Column(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(
                                            imageVector = when (plot.iconName) {
                                                "cottage" -> Icons.Default.Cottage
                                                "agriculture" -> Icons.Default.Agriculture
                                                "storefront" -> Icons.Default.Storefront
                                                "carpenter" -> Icons.Default.Carpenter
                                                else -> Icons.Default.AccountBalance
                                            },
                                            contentDescription = plot.name,
                                            tint = if (isSelected) OnSaffronPrimaryContainer else Color.White,
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = plot.name,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) OnSaffronPrimaryContainer else Color.White
                                        )
                                        Text(
                                            text = if (plot.level == 0) "Build!" else "Lvl ${plot.level}/${plot.maxLevel}",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = if (isSelected) SaffronPrimary else EmeraldSecondaryContainer
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Floating Quick Dash Run Button on Right
                    Button(
                        onClick = onQuickDashClick,
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SaffronPrimary,
                            contentColor = OnSaffronPrimary
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 12.dp)
                            .shadow(6.dp, CircleShape)
                            .testTag("quick_dash_button")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("DASH! (-5 ⚡)", fontSize = 11.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }
        }

        // 3. Selected Building Inspection & Action Card
        item {
            val plot = selectedBuilding ?: buildings.firstOrNull()
            if (plot != null) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shadowElevation = 2.dp
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(SaffronPrimaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = when (plot.iconName) {
                                            "cottage" -> Icons.Default.Cottage
                                            "agriculture" -> Icons.Default.Agriculture
                                            "storefront" -> Icons.Default.Storefront
                                            "carpenter" -> Icons.Default.Carpenter
                                            else -> Icons.Default.AccountBalance
                                        },
                                        contentDescription = null,
                                        tint = OnSaffronPrimaryContainer,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = plot.name,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Tier Level ${plot.level} • ${plot.flavor}",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            // Level Badge
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = EmeraldSecondaryContainer
                            ) {
                                Text(
                                    text = "Lvl ${plot.level} ➔ ${plot.level + 1}",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnEmeraldSecondaryContainer,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Stats Comparison
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(10.dp))
                                .padding(horizontal = 10.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Passive Harvest", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(
                                    text = "${plot.incomePerHour}/hr ➔ +${plot.nextIncomeDelta}/hr",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black,
                                    color = EmeraldSecondary
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                OutlinedButton(
                                    onClick = { showInspectModal = true },
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.height(36.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Info, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Inspect", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }

                                Button(
                                    onClick = { showInspectModal = true },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = EmeraldSecondary,
                                        contentColor = OnEmeraldSecondary
                                    ),
                                    modifier = Modifier.height(36.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.MonetizationOn, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("${plot.upgradeCostCoins}", fontSize = 11.sp, fontWeight = FontWeight.Black)
                                }
                            }
                        }
                    }
                }
            }
        }

        // 4. Active Festival Booster Island
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(SaffronPrimaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Celebration,
                                contentDescription = null,
                                tint = OnSaffronPrimaryContainer,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Baisakhi Harvest Festival",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = SaffronPrimaryContainer
                                ) {
                                    Text(
                                        text = "+25% Rush",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OnSaffronPrimaryContainer,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                            }
                            Text(
                                text = "4h 12m remaining • All grain yields boosted",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Harvest Barn button
                    Button(
                        onClick = { onCollectBarnYield(480L) },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SaffronPrimary,
                            contentColor = OnSaffronPrimary
                        ),
                        modifier = Modifier.testTag("collect_harvest_button")
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("HARVEST", fontSize = 10.sp, fontWeight = FontWeight.Black)
                            Text("+480 🪙", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
