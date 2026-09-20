package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.HolidayVillage
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.UserProfileEntity
import com.example.ui.navigation.Screen
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
import com.example.ui.theme.SaffronPrimaryFixed

@Composable
fun TopChieftainHeader(
    profile: UserProfileEntity?,
    onSettingsClick: () -> Unit,
    onBazaarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.95f),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: App Logo Badge + Chieftain Avatar Pill
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Village Rush App Icon Badge
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SaffronPrimaryContainer)
                        .padding(2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MonetizationOn,
                        contentDescription = "Village Rush Logo",
                        tint = OnSaffronPrimaryContainer,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Chieftain Avatar pill
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shadowElevation = 2.dp,
                    modifier = Modifier.clickable { onSettingsClick() }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 2.dp, end = 8.dp, top = 2.dp, bottom = 2.dp)
                    ) {
                        Box(contentAlignment = Alignment.BottomEnd) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(SaffronPrimaryContainer)
                                    .padding(1.dp)
                            ) {
                                AsyncImage(
                                    model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBZaF2u7lQ9uAwxKyfTtv-ji8NEmVrTREQDe1uedVzY_6Fi48NZ16A1YseNXN3ObIWbVlk4NdWtuVduMN2SsuWc2P2xDJdz41EX7WjhCLh7mZ1Nen5xG3-HraEwO9dPiaUi1qK1aIkD5NTVddiW6Vv2nlUfac2hE2ozdUGTy1Fx2WDgrON6kvSlzkPJ99UiTu31piwPRiktcZX9ARFR89paeG9aQow2Ys7b75D93rGoRigCXGPdKJafPA",
                                    contentDescription = "Avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(CircleShape)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .clip(CircleShape)
                                    .background(SaffronPrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "4",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnSaffronPrimary
                                )
                            }
                        }
                        Column(modifier = Modifier.padding(start = 4.dp)) {
                            Text(
                                text = "CHIEFTAIN",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Black,
                                color = SaffronPrimary,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = profile?.playerName ?: "Bharat",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

            // Center: Telemetry Pills (Coins, Gems, Energy)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Coins Pill
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shadowElevation = 1.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(SaffronPrimaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.MonetizationOn,
                                contentDescription = "Coins",
                                tint = OnSaffronPrimaryContainer,
                                modifier = Modifier.size(13.dp)
                            )
                        }
                        Text(
                            text = "${((profile?.coins ?: 12450L) / 1000.0).let { "%.1fk".format(it) }}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(EmeraldSecondary)
                                .clickable { onBazaarClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("+", color = OnEmeraldSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Gems Pill
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shadowElevation = 1.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(RoyalBlueTertiaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Diamond,
                                contentDescription = "Gems",
                                tint = OnRoyalBlueTertiaryContainer,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                        Text(
                            text = "${profile?.gems ?: 85}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(EmeraldSecondary)
                                .clickable { onBazaarClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("+", color = OnEmeraldSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Energy Pill
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shadowElevation = 1.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFBA1A1A)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = "Energy",
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                        Text(
                            text = "${profile?.energy ?: 22}/${profile?.maxEnergy ?: 25}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(start = 3.dp)
                        )
                    }
                }
            }

            // Right: Settings Icon Button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                        .clickable { onSettingsClick() }
                        .testTag("settings_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(SaffronPrimary)
                        .clickable { onSettingsClick() }
                        .testTag("profile_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = OnSaffronPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceContainerLowest.copy(alpha = 0.98f),
        shadowElevation = 12.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 4.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Village Tab
                NavItem(
                    label = "Village",
                    icon = Icons.Default.HolidayVillage,
                    isSelected = currentRoute == Screen.Village.route,
                    onClick = { onNavigate(Screen.Village.route) }
                )

                // Cards Tab
                NavItem(
                    label = "Cards",
                    icon = Icons.Default.Style,
                    isSelected = currentRoute == Screen.Cards.route,
                    onClick = { onNavigate(Screen.Cards.route) }
                )

                // Spacer for the center elevated PLAY button
                Spacer(modifier = Modifier.width(60.dp))

                // Heroes Tab
                NavItem(
                    label = "Heroes",
                    icon = Icons.Default.Security,
                    isSelected = currentRoute == Screen.Heroes.route,
                    onClick = { onNavigate(Screen.Heroes.route) }
                )

                // Missions Tab (with red pulse badge)
                NavItem(
                    label = "Missions",
                    icon = Icons.Default.TaskAlt,
                    hasBadge = true,
                    isSelected = currentRoute == Screen.Missions.route,
                    onClick = { onNavigate(Screen.Missions.route) }
                )

                // Bazaar Tab
                NavItem(
                    label = "Bazaar",
                    icon = Icons.Default.Storefront,
                    isSelected = currentRoute == Screen.Bazaar.route,
                    onClick = { onNavigate(Screen.Bazaar.route) }
                )
            }

            // Big Juicy Elevated PLAY Button in center
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .offset(y = (-14).dp)
                    .clickable { onNavigate(Screen.CoinDash.route) }
                    .testTag("play_rush_button")
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .shadow(8.dp, CircleShape)
                        .clip(CircleShape)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(SaffronPrimaryContainer, SaffronPrimary)
                            )
                        )
                        .border(2.dp, SaffronPrimaryFixed, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SportsEsports,
                        contentDescription = "PLAY",
                        tint = OnSaffronPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Text(
                    text = "PLAY",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = SaffronPrimary,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun NavItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    hasBadge: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(contentAlignment = Alignment.TopEnd) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = if (isSelected) SaffronPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
                if (hasBadge) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFBA1A1A))
                    )
                }
            }
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                color = if (isSelected) SaffronPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}
