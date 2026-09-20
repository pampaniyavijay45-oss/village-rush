package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.HolidayVillage
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.SportsMartialArts
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.Toll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MissionEntity
import com.example.ui.theme.EmeraldSecondary
import com.example.ui.theme.EmeraldSecondaryContainer
import com.example.ui.theme.OnEmeraldSecondary
import com.example.ui.theme.OnEmeraldSecondaryContainer
import com.example.ui.theme.OnRoyalBlueTertiaryContainer
import com.example.ui.theme.OnSaffronPrimary
import com.example.ui.theme.OnSaffronPrimaryContainer
import com.example.ui.theme.RoyalBlueTertiaryContainer
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.SaffronPrimaryContainer

@Composable
fun MissionsScreen(
    missions: List<MissionEntity>,
    onClaimMission: (MissionEntity) -> Unit,
    onNavigateToPlay: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf("Daily Tasks") }
    val tabs = listOf("Daily Tasks", "Village Badges", "Weekly Rush")

    val completedCount = missions.count { it.isClaimed || it.currentProgress >= it.targetProgress }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 1. Panchayat Board Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "PANCHAYAT BOARD",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = SaffronPrimary,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Daily Duties & Chores",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerHigh
                ) {
                    Text(
                        text = "Reset in 14h 22m",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // 2. Milestone Chest Progress Card
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(SaffronPrimaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CardGiftcard,
                            contentDescription = null,
                            tint = OnSaffronPrimaryContainer,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Milestone Chest: $completedCount/${missions.size} Done",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "+2,000 🪙 +10 💎",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = EmeraldSecondary
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        LinearProgressIndicator(
                            progress = { (completedCount.toFloat() / missions.size.coerceAtLeast(1)).coerceIn(0f, 1f) },
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
        }

        // 3. Tab Selectors
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tabs.forEach { tab ->
                    val isSelected = selectedTab == tab
                    Surface(
                        modifier = Modifier.clickable { selectedTab = tab },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) SaffronPrimary else MaterialTheme.colorScheme.surfaceContainerHigh
                    ) {
                        Text(
                            text = tab,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) OnSaffronPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // 4. Missions List
        items(missions) { mission ->
            val canClaim = !mission.isClaimed && mission.currentProgress >= mission.targetProgress

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(1.dp, RoundedCornerShape(16.dp))
                    .testTag("mission_item_${mission.id}"),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Mission Icon
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(
                                if (mission.isClaimed) MaterialTheme.colorScheme.surfaceVariant else SaffronPrimaryContainer
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (mission.icon) {
                                "holiday_village" -> Icons.Default.HolidayVillage
                                "toll" -> Icons.Default.Toll
                                "sprint" -> Icons.Default.DirectionsRun
                                else -> Icons.Default.SportsMartialArts
                            },
                            contentDescription = null,
                            tint = if (mission.isClaimed) MaterialTheme.colorScheme.onSurfaceVariant else OnSaffronPrimaryContainer,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    // Mission Details
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = mission.title,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            // Reward badge
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (mission.rewardType == "coins") SaffronPrimaryContainer else RoyalBlueTertiaryContainer
                            ) {
                                Text(
                                    text = "+${mission.rewardAmount} ${if (mission.rewardType == "coins") "🪙" else "💎"}",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (mission.rewardType == "coins") OnSaffronPrimaryContainer else OnRoyalBlueTertiaryContainer,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }

                        Text(
                            text = mission.description,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Progress Bar
                        LinearProgressIndicator(
                            progress = { (mission.currentProgress.toFloat() / mission.targetProgress).coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp)),
                            color = if (canClaim) EmeraldSecondary else SaffronPrimary,
                            trackColor = MaterialTheme.colorScheme.surfaceContainerHigh
                        )

                        Text(
                            text = "${mission.currentProgress}/${mission.targetProgress}",
                            fontSize = 9.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }

                    // Action Button (Claim / Done / GO)
                    if (mission.isClaimed) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceContainerHigh
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = EmeraldSecondary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Claimed", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    } else if (canClaim) {
                        Button(
                            onClick = { onClaimMission(mission) },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = EmeraldSecondary,
                                contentColor = OnEmeraldSecondary
                            ),
                            modifier = Modifier.testTag("claim_mission_${mission.id}")
                        ) {
                            Text("CLAIM", fontSize = 11.sp, fontWeight = FontWeight.Black)
                        }
                    } else {
                        OutlinedButton(
                            onClick = onNavigateToPlay,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("GO", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 5. Gram Trophy Spotlight Card
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(RoyalBlueTertiaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = null,
                            tint = OnRoyalBlueTertiaryContainer,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Gram Trophy: First Harvest",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Reach 25 Stars in Punjab Settlement to unlock Thar Desert.",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = RoyalBlueTertiaryContainer
                    ) {
                        Text(
                            text = "+25 💎",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = OnRoyalBlueTertiaryContainer,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
