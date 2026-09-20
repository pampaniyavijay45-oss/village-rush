package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Upgrade
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.HeroEntity
import com.example.data.model.UserProfileEntity
import com.example.ui.theme.EmeraldSecondary
import com.example.ui.theme.EmeraldSecondaryContainer
import com.example.ui.theme.OnEmeraldSecondary
import com.example.ui.theme.OnEmeraldSecondaryContainer
import com.example.ui.theme.OnSaffronPrimary
import com.example.ui.theme.OnSaffronPrimaryContainer
import com.example.ui.theme.RoyalBlueTertiary
import com.example.ui.theme.RoyalBlueTertiaryContainer
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.SaffronPrimaryContainer
import com.example.ui.theme.SaffronPrimaryFixed

@Composable
fun HeroesScreen(
    profile: UserProfileEntity?,
    heroes: List<HeroEntity>,
    onEquipHero: (String) -> Unit,
    onUpgradeHero: (HeroEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedHeroId by remember { mutableStateOf(profile?.activeHeroId ?: "veer") }
    val activeHero = heroes.find { it.id == selectedHeroId } ?: heroes.firstOrNull()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 1. Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "VILLAGE HEROES",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = SaffronPrimary,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Chieftain Roster",
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
                        text = "${heroes.count { it.isUnlocked }}/${heroes.size} Unlocked",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // 2. Spotlight Active Hero Card
        if (activeHero != null) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shadowElevation = 3.dp
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // 3D Avatar Portrait
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .shadow(6.dp, RoundedCornerShape(18.dp))
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(SaffronPrimaryContainer)
                                    .border(2.dp, SaffronPrimaryFixed, RoundedCornerShape(18.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBZaF2u7lQ9uAwxKyfTtv-ji8NEmVrTREQDe1uedVzY_6Fi48NZ16A1YseNXN3ObIWbVlk4NdWtuVduMN2SsuWc2P2xDJdz41EX7WjhCLh7mZ1Nen5xG3-HraEwO9dPiaUi1qK1aIkD5NTVddiW6Vv2nlUfac2hE2ozdUGTy1Fx2WDgrON6kvSlzkPJ99UiTu31piwPRiktcZX9ARFR89paeG9aQow2Ys7b75D93rGoRigCXGPdKJafPA",
                                    contentDescription = activeHero.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = activeHero.name,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    if (profile?.activeHeroId == activeHero.id) {
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = EmeraldSecondaryContainer
                                        ) {
                                            Text(
                                                text = "ACTIVE",
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Black,
                                                color = OnEmeraldSecondaryContainer,
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }

                                Text(
                                    text = "${activeHero.title} • Lvl ${activeHero.level}",
                                    fontSize = 12.sp,
                                    color = SaffronPrimary,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                // Skill Ribbon
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = SaffronPrimaryFixed.copy(alpha = 0.4f)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Bolt,
                                            contentDescription = null,
                                            tint = SaffronPrimary,
                                            modifier = Modifier.size(13.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "${activeHero.skillName}: ${activeHero.skillDescription}",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 2
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Stat Progress Gauges
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            StatGaugeRow(
                                label = "Speed Velocity",
                                value = activeHero.speedVal,
                                ratio = activeHero.speedRatio,
                                icon = Icons.Default.Speed,
                                barColor = SaffronPrimary
                            )
                            StatGaugeRow(
                                label = "Coin Magnet Radius",
                                value = activeHero.magnetVal,
                                ratio = activeHero.magnetRatio,
                                icon = Icons.Default.MonetizationOn,
                                barColor = EmeraldSecondary
                            )
                            StatGaugeRow(
                                label = "Energy Capacity",
                                value = activeHero.energyVal,
                                ratio = activeHero.energyRatio,
                                icon = Icons.Default.FlashOn,
                                barColor = RoyalBlueTertiary
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Equip Button
                        if (activeHero.isUnlocked) {
                            val isEquipped = profile?.activeHeroId == activeHero.id
                            Button(
                                onClick = { onEquipHero(activeHero.id) },
                                enabled = !isEquipped,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                                    .testTag("equip_hero_button"),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isEquipped) EmeraldSecondary else SaffronPrimary,
                                    contentColor = OnSaffronPrimary
                                )
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (isEquipped) Icons.Default.Check else Icons.Default.Security,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (isEquipped) "EQUIPPED AS CHIEFTAIN" else "EQUIP AS ACTIVE CHIEFTAIN",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }
                        } else {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.outline)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = activeHero.unlockCondition,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 3. Horizontal Village Roster Carousel
        item {
            Text(
                text = "VILLAGE HEROES ROSTER",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 0.5.sp
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(heroes) { hero ->
                    val isSelected = selectedHeroId == hero.id
                    val isEquipped = profile?.activeHeroId == hero.id

                    Surface(
                        modifier = Modifier
                            .width(130.dp)
                            .clickable { selectedHeroId = hero.id }
                            .shadow(2.dp, RoundedCornerShape(14.dp))
                            .testTag("hero_card_${hero.id}"),
                        shape = RoundedCornerShape(14.dp),
                        color = if (isSelected) SaffronPrimaryFixed.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surfaceContainerLowest,
                        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, SaffronPrimary) else null
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(contentAlignment = Alignment.TopEnd) {
                                Box(
                                    modifier = Modifier
                                        .size(54.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(if (hero.isUnlocked) SaffronPrimaryContainer else Color.Gray)
                                ) {
                                    AsyncImage(
                                        model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBZaF2u7lQ9uAwxKyfTtv-ji8NEmVrTREQDe1uedVzY_6Fi48NZ16A1YseNXN3ObIWbVlk4NdWtuVduMN2SsuWc2P2xDJdz41EX7WjhCLh7mZ1Nen5xG3-HraEwO9dPiaUi1qK1aIkD5NTVddiW6Vv2nlUfac2hE2ozdUGTy1Fx2WDgrON6kvSlzkPJ99UiTu31piwPRiktcZX9ARFR89paeG9aQow2Ys7b75D93rGoRigCXGPdKJafPA",
                                        contentDescription = hero.name,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                if (isEquipped) {
                                    Box(
                                        modifier = Modifier
                                            .size(16.dp)
                                            .clip(CircleShape)
                                            .background(EmeraldSecondary),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(11.dp))
                                    }
                                } else if (!hero.isUnlocked) {
                                    Box(
                                        modifier = Modifier
                                            .size(16.dp)
                                            .clip(CircleShape)
                                            .background(Color.Black.copy(alpha = 0.7f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = Color.White, modifier = Modifier.size(10.dp))
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = hero.name,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = if (hero.isUnlocked) "Lvl ${hero.level}" else "Locked",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (hero.isUnlocked) SaffronPrimary else MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
        }

        // 4. Level Mastery Upgrade Panel
        if (activeHero != null && activeHero.isUnlocked) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shadowElevation = 2.dp
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "HERO MASTERY UPGRADE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Lvl ${activeHero.level} ➔ ${activeHero.level + 1}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = EmeraldSecondary
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Shards progress
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Hero Shards Ready",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "${activeHero.shardsReady}/${activeHero.shardsRequired}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (activeHero.shardsReady >= activeHero.shardsRequired) EmeraldSecondary else MaterialTheme.colorScheme.error
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        LinearProgressIndicator(
                            progress = { (activeHero.shardsReady.toFloat() / activeHero.shardsRequired).coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = EmeraldSecondary,
                            trackColor = MaterialTheme.colorScheme.surfaceContainerHigh
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Upgrade CTA
                        Button(
                            onClick = { onUpgradeHero(activeHero) },
                            enabled = activeHero.shardsReady >= activeHero.shardsRequired && (profile?.coins ?: 0L) >= activeHero.upgradeCostCoins,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("upgrade_hero_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = EmeraldSecondary,
                                contentColor = OnEmeraldSecondary
                            )
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Upgrade, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "UPGRADE LEVEL (${activeHero.upgradeCostCoins} COINS)",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatGaugeRow(
    label: String,
    value: String,
    ratio: Float,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    barColor: Color
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = icon, contentDescription = null, tint = barColor, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(value, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        }
        Spacer(modifier = Modifier.height(3.dp))
        LinearProgressIndicator(
            progress = { ratio },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = barColor,
            trackColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    }
}
