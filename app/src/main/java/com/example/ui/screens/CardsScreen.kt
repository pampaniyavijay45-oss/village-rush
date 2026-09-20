package com.example.ui.screens

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.CardEntity
import com.example.ui.theme.EmeraldSecondary
import com.example.ui.theme.EmeraldSecondaryContainer
import com.example.ui.theme.OnEmeraldSecondary
import com.example.ui.theme.OnEmeraldSecondaryContainer
import com.example.ui.theme.OnRoyalBlueTertiaryContainer
import com.example.ui.theme.OnSaffronPrimary
import com.example.ui.theme.OnSaffronPrimaryContainer
import com.example.ui.theme.RoyalBlueTertiary
import com.example.ui.theme.RoyalBlueTertiaryContainer
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.SaffronPrimaryContainer
import com.example.ui.theme.SaffronPrimaryFixed

@Composable
fun CardsScreen(
    cards: List<CardEntity>,
    onTradeDuplicates: () -> Unit,
    onOpenDailyPack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedCardForDetail by remember { mutableStateOf<CardEntity?>(null) }
    val categories = listOf("All", "Villages", "Farming", "Artisans", "Festivals")

    val filteredCards = if (selectedCategory == "All") {
        cards
    } else {
        cards.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    if (selectedCardForDetail != null) {
        val card = selectedCardForDetail!!
        AlertDialog(
            onDismissRequest = { selectedCardForDetail = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Style, contentDescription = null, tint = SaffronPrimary)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(card.title, fontWeight = FontWeight.Black, fontSize = 18.sp)
                }
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                    ) {
                        AsyncImage(
                            model = card.imageUrl,
                            contentDescription = card.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Rarity: ${card.rarity} • Level ${card.level}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = SaffronPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = card.bonusDescription,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Copies Owned: ${card.ownedCopies}/${card.requiredCopies}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                Button(onClick = { selectedCardForDetail = null }) {
                    Text("Close")
                }
            }
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 90.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // 1. Set 1: Punjab Harvest Milestone Banner
        item(span = { GridItemSpan(2) }) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "HERITAGE TRADING ALBUM",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = SaffronPrimary,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Set 1: Punjab Harvest",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        // Milestone Reward Badge
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = SaffronPrimaryContainer
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MonetizationOn,
                                    contentDescription = null,
                                    tint = OnSaffronPrimaryContainer,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "+500 Reward",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnSaffronPrimaryContainer
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("4/5 Heritage Cards Collected", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("80% Unlocked", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = EmeraldSecondary)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    LinearProgressIndicator(
                        progress = { 0.80f },
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

        // 2. Free Daily Pack & Duplicate Trading Row
        item(span = { GridItemSpan(2) }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Free Daily Pack
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onOpenDailyPack() }
                        .testTag("free_daily_pack_button"),
                    shape = RoundedCornerShape(14.dp),
                    color = EmeraldSecondaryContainer
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CardGiftcard,
                            contentDescription = null,
                            tint = EmeraldSecondary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Free Daily Pack", fontSize = 11.sp, fontWeight = FontWeight.Black, color = OnEmeraldSecondaryContainer)
                            Text("Tap to Open! 🎁", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = EmeraldSecondary)
                        }
                    }
                }

                // Duplicate Trade
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTradeDuplicates() }
                        .testTag("trade_duplicates_button"),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shadowElevation = 1.dp
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sync,
                            contentDescription = null,
                            tint = SaffronPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Trade Duplicates", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            Text("14 in Stash", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }

        // 3. Category Filter Pills
        item(span = { GridItemSpan(2) }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                categories.forEach { cat ->
                    val isSelected = selectedCategory == cat
                    Surface(
                        modifier = Modifier.clickable { selectedCategory = cat },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) SaffronPrimary else MaterialTheme.colorScheme.surfaceContainerHigh
                    ) {
                        Text(
                            text = cat,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) OnSaffronPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }
        }

        // 4. Cards Grid
        items(filteredCards) { card ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedCardForDetail = card }
                    .shadow(2.dp, RoundedCornerShape(16.dp))
                    .testTag("card_item_${card.id}"),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    // Card Image with Rarity Tag
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                    ) {
                        AsyncImage(
                            model = card.imageUrl,
                            contentDescription = card.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Rarity Badge
                        Surface(
                            modifier = Modifier
                                .padding(4.dp)
                                .align(Alignment.TopStart),
                            shape = RoundedCornerShape(4.dp),
                            color = when (card.rarity) {
                                "Legendary" -> SaffronPrimaryContainer
                                "Epic" -> RoyalBlueTertiaryContainer
                                "Rare" -> EmeraldSecondaryContainer
                                else -> MaterialTheme.colorScheme.surfaceVariant
                            }
                        ) {
                            Text(
                                text = card.rarity.uppercase(),
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Black,
                                color = when (card.rarity) {
                                    "Legendary" -> OnSaffronPrimaryContainer
                                    "Epic" -> OnRoyalBlueTertiaryContainer
                                    "Rare" -> OnEmeraldSecondaryContainer
                                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                                },
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }

                        if (card.isMaxLevel) {
                            Surface(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .align(Alignment.TopEnd),
                                shape = RoundedCornerShape(4.dp),
                                color = EmeraldSecondary
                            ) {
                                Text(
                                    text = "MAX",
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        } else if (card.isLocked) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.5f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = Color.White)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = card.title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = card.bonusDescription,
                        fontSize = 10.sp,
                        color = SaffronPrimary,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Progress bar
                    if (!card.isLocked && !card.isMaxLevel) {
                        LinearProgressIndicator(
                            progress = { (card.ownedCopies.toFloat() / card.requiredCopies).coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp)),
                            color = SaffronPrimary,
                            trackColor = MaterialTheme.colorScheme.surfaceContainerHigh
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Copies", fontSize = 8.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${card.ownedCopies}/${card.requiredCopies}", fontSize = 8.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
