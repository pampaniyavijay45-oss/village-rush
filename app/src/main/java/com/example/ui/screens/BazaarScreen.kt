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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Redeem
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.ui.theme.CardamomSurfaceContainer
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
fun BazaarScreen(
    playerCoins: Long,
    playerGems: Int,
    onClaimDailyShagun: () -> Unit,
    onBuyBundle: (title: String, costGems: Int, coinsGiven: Long, gemsExtra: Int) -> Unit,
    onRefillEnergy: (amount: Int, costGems: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Shahi Vyapar Mela Banner
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = SaffronPrimary,
                shadowElevation = 3.dp
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "SHAHI VYAPAR MELA",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = SaffronPrimaryFixed,
                                letterSpacing = 1.sp
                            )
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color.Black.copy(alpha = 0.3f)
                            ) {
                                Text(
                                    text = "Refreshes in 12h 45m",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Royal Punjabi Harvest Fair",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = OnSaffronPrimary
                        )
                        Text(
                            text = "Exclusive Chieftain supplies, energy elixirs, and gold vaults.",
                            fontSize = 11.sp,
                            color = SaffronPrimaryFixed
                        )
                    }
                }
            }
        }

        // 2. Daily Shagun Free Claim Box
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = EmeraldSecondaryContainer,
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
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(EmeraldSecondary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CardGiftcard,
                                contentDescription = null,
                                tint = OnEmeraldSecondary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Village Elder's Shagun",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                color = OnEmeraldSecondaryContainer
                            )
                            Text(
                                text = "Daily blessing: Free +500 Coins",
                                fontSize = 11.sp,
                                color = EmeraldSecondary
                            )
                        }
                    }

                    Button(
                        onClick = onClaimDailyShagun,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EmeraldSecondary,
                            contentColor = OnEmeraldSecondary
                        ),
                        modifier = Modifier.testTag("claim_shagun_button")
                    ) {
                        Text("CLAIM FREE", fontSize = 11.sp, fontWeight = FontWeight.Black)
                    }
                }
            }
        }

        // 3. Mela Special Deal (Punjab Harvest Starter Bundle)
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shadowElevation = 3.dp,
                border = androidx.compose.foundation.BorderStroke(1.5.dp, SaffronPrimaryContainer)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFBA1A1A)
                        ) {
                            Text(
                                text = "🔥 80% OFF SPECIAL",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Text(
                            text = "Limited 1 per Chieftain",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Punjab Harvest Starter Bundle",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Shera the Sprinter Hero + 10,000 Coins + 30 Extra Gems",
                        fontSize = 11.sp,
                        color = SaffronPrimary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            onBuyBundle("Punjab Harvest Starter Bundle", 20, 10000L, 30)
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("buy_mela_bundle_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SaffronPrimary,
                            contentColor = OnSaffronPrimary
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Diamond, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("ACQUIRE BUNDLE (20 GEMS)", fontSize = 12.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }
        }

        // 4. Energy Refill Stalls
        item {
            Text(
                text = "ENERGY REFILL STALLS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 0.5.sp
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Quick Chai
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .shadow(2.dp, RoundedCornerShape(14.dp)),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerLowest
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(imageVector = Icons.Default.LocalCafe, contentDescription = null, tint = SaffronPrimary, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Quick Kadak Chai", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Text("+5 Energy ⚡", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = EmeraldSecondary)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { onRefillEnergy(5, 5) },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldSecondary)
                        ) {
                            Text("5 💎", fontSize = 11.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }

                // Chieftain Feast
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .shadow(2.dp, RoundedCornerShape(14.dp)),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerLowest
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(imageVector = Icons.Default.Bolt, contentDescription = null, tint = Color(0xFFBA1A1A), modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Chieftain's Feast", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Text("Full Refill (25) ⚡", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = EmeraldSecondary)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { onRefillEnergy(25, 15) },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary)
                        ) {
                            Text("15 💎", fontSize = 11.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }
        }

        // 5. Village Coin Bundles
        item {
            Text(
                text = "VILLAGE COIN BUNDLES",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 0.5.sp
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Handful of coins
                CoinBundleCard(
                    title = "Handful of Coins",
                    coinsAmount = "5,000 Coins",
                    costGems = 10,
                    icon = Icons.Default.MonetizationOn,
                    onBuy = { onBuyBundle("Handful of Coins", 10, 5000L, 0) }
                )

                // Clay pot of gold
                CoinBundleCard(
                    title = "Clay Pot of Gold",
                    coinsAmount = "30,000 Coins (+5k bonus)",
                    costGems = 25,
                    icon = Icons.Default.Savings,
                    badge = "POPULAR",
                    onBuy = { onBuyBundle("Clay Pot of Gold", 25, 30000L, 0) }
                )

                // Royal Treasury
                CoinBundleCard(
                    title = "Royal Punjab Treasury",
                    coinsAmount = "133,000 Coins (+33k bonus)",
                    costGems = 80,
                    icon = Icons.Default.LocalAtm,
                    badge = "BEST VALUE",
                    onBuy = { onBuyBundle("Royal Punjab Treasury", 80, 133000L, 0) }
                )
            }
        }
    }
}

@Composable
private fun CoinBundleCard(
    title: String,
    coinsAmount: String,
    costGems: Int,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    badge: String? = null,
    onBuy: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(SaffronPrimaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = OnSaffronPrimaryContainer,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        if (badge != null) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = EmeraldSecondaryContainer
                            ) {
                                Text(
                                    text = badge,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Black,
                                    color = OnEmeraldSecondaryContainer,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                    }
                    Text(text = coinsAmount, fontSize = 11.sp, fontWeight = FontWeight.Black, color = SaffronPrimary)
                }
            }

            Button(
                onClick = onBuy,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Diamond, contentDescription = null, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("$costGems", fontSize = 11.sp, fontWeight = FontWeight.Black)
                }
            }
        }
    }
}
