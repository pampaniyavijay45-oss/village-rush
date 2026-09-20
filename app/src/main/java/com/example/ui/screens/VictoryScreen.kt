package com.example.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Fireplace
import androidx.compose.material.icons.filled.HolidayVillage
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.ui.viewmodel.MiniGameRunResult

@Composable
fun VictoryScreen(
    result: MiniGameRunResult,
    onClaimAndReturn: () -> Unit,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Spacer(modifier = Modifier.height(6.dp))

        // Celebratory Header Plaque
        Box(
            modifier = Modifier
                .size(76.dp)
                .shadow(12.dp, CircleShape)
                .clip(CircleShape)
                .background(
                    Brush.verticalGradient(
                        listOf(SaffronPrimaryContainer, SaffronPrimary)
                    )
                )
                .border(3.dp, SaffronPrimaryFixed, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = "Victory Trophy",
                tint = OnSaffronPrimary,
                modifier = Modifier.size(42.dp)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "VICTORY! DASH COMPLETED",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = SaffronPrimary,
                letterSpacing = 0.5.sp
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = EmeraldSecondaryContainer
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldSecondary, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Clean Run", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = OnEmeraldSecondaryContainer)
                    }
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${result.distanceMeters} Meters Sprint",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Star Rating Row
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            repeat(3) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = SaffronPrimaryContainer,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        // Detailed Score Breakdown Card
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            color = MaterialTheme.colorScheme.surfaceContainerLowest,
            shadowElevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "HARVEST BREAKDOWN",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                BreakdownRow(
                    label = "Coins Collected (${result.coinsGathered} x 50)",
                    value = "+${result.coinsPayout}",
                    icon = Icons.Default.MonetizationOn,
                    iconTint = SaffronPrimaryContainer
                )

                BreakdownRow(
                    label = "Match Sprint Score",
                    value = "+${result.matchScore}",
                    icon = Icons.Default.Bolt,
                    iconTint = RoyalBlueTertiary
                )

                BreakdownRow(
                    label = "Combo Streak (${result.comboStreak}x)",
                    value = "+${result.streakBonus}",
                    icon = Icons.Default.Fireplace,
                    iconTint = Color(0xFFBA1A1A)
                )

                BreakdownRow(
                    label = "Base Village Bonus",
                    value = "+${result.baseBonus}",
                    icon = Icons.Default.HolidayVillage,
                    iconTint = EmeraldSecondary
                )

                BreakdownRow(
                    label = "Gems Discovered",
                    value = "+${result.gemsFound} 💎",
                    icon = Icons.Default.Diamond,
                    iconTint = Color.Cyan
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )

                // Grand Total
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TOTAL HARVEST PAYOUT",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "+${result.totalCoins}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = EmeraldSecondary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.MonetizationOn,
                            contentDescription = null,
                            tint = SaffronPrimaryContainer,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // Fair-Play Guarantee Badge
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = null,
                    tint = EmeraldSecondary,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Verified Fair-Play Cap Guarantee • Auto-Saved to Room DB",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Action Buttons
        Button(
            onClick = onClaimAndReturn,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .shadow(6.dp, RoundedCornerShape(16.dp))
                .testTag("claim_payout_button"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SaffronPrimary,
                contentColor = OnSaffronPrimary
            )
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.MonetizationOn, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "CLAIM & RETURN TO VILLAGE",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )
            }
        }

        OutlinedButton(
            onClick = onPlayAgain,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("play_again_button"),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Replay, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "PLAY AGAIN (-5 ⚡)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun BreakdownRow(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(15.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
    }
}
