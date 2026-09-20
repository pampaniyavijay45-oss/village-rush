package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Roofing
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Upgrade
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.data.model.BuildingPlotEntity
import com.example.ui.theme.CardamomSurfaceContainer
import com.example.ui.theme.EmeraldSecondary
import com.example.ui.theme.EmeraldSecondaryContainer
import com.example.ui.theme.OnEmeraldSecondary
import com.example.ui.theme.OnEmeraldSecondaryContainer
import com.example.ui.theme.OnSaffronPrimary
import com.example.ui.theme.OutlineVariantClay
import com.example.ui.theme.RoyalBlueTertiary
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.SaffronPrimaryContainer
import com.example.ui.theme.SaffronPrimaryFixed
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BuildingUpgradeDialog(
    plot: BuildingPlotEntity,
    playerCoins: Long,
    onDismiss: () -> Unit,
    onConfirmUpgrade: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var isUpgrading by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xBB1E1C11))
                .padding(horizontal = 16.dp, vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(16.dp, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Top Arch Header Ribbon
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(SaffronPrimary, SaffronPrimaryContainer, SaffronPrimary)
                                )
                            )
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Roofing,
                                contentDescription = null,
                                tint = SaffronPrimaryFixed,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "UPGRADE ${plot.name.uppercase()}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                color = OnSaffronPrimary,
                                letterSpacing = 0.5.sp
                            )
                        }

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(30.dp)
                                .background(MaterialTheme.colorScheme.surfaceContainerHigh, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    // Content Body
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Current vs Next Comparison Grid
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Left: Current Level Card
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .shadow(2.dp, RoundedCornerShape(12.dp)),
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surfaceContainerHigh
                            ) {
                                Column(
                                    modifier = Modifier.padding(6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(4.dp))
                                            .padding(vertical = 2.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "CURRENT • LVL ${plot.level}",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(72.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(MaterialTheme.colorScheme.surfaceContainer)
                                    ) {
                                        AsyncImage(
                                            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuC0icI_P3RAKEVhzzlos27g2D7_NDLUv_z2T4sFjXYfD6lobEIX5YmeF8i6H9h3Gp_1_I3tqUjF2nBoaZ0n3EhfgWeMB54Db2Z3R2cB4sQYKi89TtiOjeNFWBiIeCx0NZx0jG7B7v15YlYR0McNBvWUp3_ztPlb_xdffsoQbABJ80Fu9W4Pc3FIfrjTL0QJKoQMcQh9d0_fFs5Yg7XMWC2CISsNh0Xzfj8Wyq_tfHs1R5PhcrmrE6j7RQ",
                                            contentDescription = "Current Hut",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                        Box(
                                            modifier = Modifier
                                                .align(Alignment.BottomEnd)
                                                .padding(3.dp)
                                                .background(Color.White.copy(alpha = 0.85f), RoundedCornerShape(4.dp))
                                                .padding(horizontal = 4.dp, vertical = 1.dp)
                                        ) {
                                            Text(
                                                text = "Tier 1",
                                                fontSize = 8.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Black
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Income", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Text("${plot.incomePerHour}/h", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Stars", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Text("+${plot.level}", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }

                            // Center Animated Arrow
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 6.dp)
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(EmeraldSecondaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = null,
                                    tint = OnEmeraldSecondaryContainer,
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                            // Right: Next Level Card
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .shadow(4.dp, RoundedCornerShape(12.dp))
                                    .border(1.dp, EmeraldSecondaryContainer, RoundedCornerShape(12.dp)),
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surfaceContainerLowest
                            ) {
                                Column(
                                    modifier = Modifier.padding(6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(EmeraldSecondaryContainer, RoundedCornerShape(4.dp))
                                            .padding(vertical = 2.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "NEXT • LVL ${plot.level + 1}",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Black,
                                            color = OnEmeraldSecondaryContainer
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(72.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(MaterialTheme.colorScheme.surfaceContainer)
                                    ) {
                                        AsyncImage(
                                            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuC3MAuw5luXC3KLeklDhBtBl8Oh1tbQO1JDnveLFXXxu9pct-oVsRY78fZM2ahcYHO5X2guYqRiOA1tUkrUQi8IDP9SD6heUMO0EFI8w_cQh8C0ALD9x5Qff5PdTRI9T1824apnHXXNWTotnH6UoTsWM_3igWdGXoBqbpl2wG3IQVHEQXMG02dV5LkqlGrgWPymgRbHGUlaj34OuppvZSCWXib68q05IaToNGc9oga_s8wRgkcfSHwd_w",
                                            contentDescription = "Next Level",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                        Box(
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                                .padding(3.dp)
                                                .background(EmeraldSecondary, RoundedCornerShape(4.dp))
                                                .padding(horizontal = 4.dp, vertical = 1.dp)
                                        ) {
                                            Text(
                                                text = "+${(plot.nextIncomeDelta * 100 / (plot.incomePerHour.coerceAtLeast(1)))}%",
                                                fontSize = 8.sp,
                                                fontWeight = FontWeight.Black,
                                                color = OnEmeraldSecondary
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Income", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Text(
                                            "${plot.incomePerHour + plot.nextIncomeDelta}/h",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Black,
                                            color = EmeraldSecondary
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Stars", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Text(
                                            "+${plot.level + 1} (+1)",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Black,
                                            color = EmeraldSecondary
                                        )
                                    }
                                }
                            }
                        }

                        // Upgrade Cost Box
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceContainer
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "UPGRADE COST",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .background(MaterialTheme.colorScheme.surfaceContainerLowest, RoundedCornerShape(12.dp))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text("Stash: ", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Text(
                                            "${playerCoins}",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Icon(
                                            imageVector = Icons.Default.MonetizationOn,
                                            contentDescription = null,
                                            tint = SaffronPrimaryContainer,
                                            modifier = Modifier.size(12.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    // Required Cost
                                    Surface(
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.surfaceContainerLowest
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(6.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text("Required", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = Icons.Default.MonetizationOn,
                                                    contentDescription = null,
                                                    tint = SaffronPrimaryContainer,
                                                    modifier = Modifier.size(13.dp)
                                                )
                                                Text(
                                                    "${plot.upgradeCostCoins}",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Black
                                                )
                                            }
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = Icons.Default.CheckCircle,
                                                    contentDescription = null,
                                                    tint = if (playerCoins >= plot.upgradeCostCoins) EmeraldSecondary else MaterialTheme.colorScheme.error,
                                                    modifier = Modifier.size(10.dp)
                                                )
                                                Text(
                                                    if (playerCoins >= plot.upgradeCostCoins) "Ready" else "Short",
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = if (playerCoins >= plot.upgradeCostCoins) EmeraldSecondary else MaterialTheme.colorScheme.error
                                                )
                                            }
                                        }
                                    }

                                    // Build Time
                                    Surface(
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.surfaceContainerLowest
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(6.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text("Build Time", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = Icons.Default.FlashOn,
                                                    contentDescription = null,
                                                    tint = RoyalBlueTertiary,
                                                    modifier = Modifier.size(13.dp)
                                                )
                                                Text(
                                                    "Instant",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Black,
                                                    color = RoyalBlueTertiary
                                                )
                                            }
                                            Text("No wait", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                    }

                                    // Max Cap
                                    Surface(
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.surfaceContainerLowest
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(6.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text("Max Cap", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                            Text(
                                                "Lvl ${plot.level + 1}/${plot.maxLevel}",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Black
                                            )
                                            Row(
                                                modifier = Modifier.padding(top = 2.dp),
                                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                                            ) {
                                                for (i in 1..plot.maxLevel) {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(width = 6.dp, height = 4.dp)
                                                            .clip(RoundedCornerShape(2.dp))
                                                            .background(
                                                                if (i <= plot.level + 1) EmeraldSecondary else MaterialTheme.colorScheme.surfaceVariant
                                                            )
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Village Perk Announcement
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SaffronPrimaryFixed.copy(alpha = 0.4f)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Celebration,
                                    contentDescription = null,
                                    tint = SaffronPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Unlocks Chai Stall Spot at Village Square!",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        // Primary Chunky Upgrade Action Button
                        Button(
                            onClick = {
                                if (!isUpgrading) {
                                    isUpgrading = true
                                    coroutineScope.launch {
                                        delay(350)
                                        onConfirmUpgrade()
                                    }
                                }
                            },
                            enabled = !isUpgrading && playerCoins >= plot.upgradeCostCoins,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .shadow(6.dp, RoundedCornerShape(14.dp))
                                .testTag("confirm_upgrade_button"),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = EmeraldSecondary,
                                contentColor = OnEmeraldSecondary
                            )
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isUpgrading) Icons.Default.Upgrade else Icons.Default.MonetizationOn,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isUpgrading) "CONSTRUCTING..." else "UPGRADE NOW (${plot.upgradeCostCoins} COINS)",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }

                        // Secondary Dismiss Button
                        Text(
                            text = "KEEP AS IS • MAYBE LATER",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onDismiss() }
                                .padding(vertical = 4.dp)
                        )
                    }

                    // Bottom Festive Edge Trim
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(SaffronPrimary, SaffronPrimaryContainer, EmeraldSecondary)
                                )
                            )
                    )
                }
            }
        }
    }
}
