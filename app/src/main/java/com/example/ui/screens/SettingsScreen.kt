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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.model.ActivityLogEntity
import com.example.data.model.SystemMetricEntity
import com.example.data.model.UserProfileEntity
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SettingsScreen(
    profile: UserProfileEntity?,
    activityLogs: List<ActivityLogEntity>,
    systemMetrics: List<SystemMetricEntity>,
    onSaveSettings: (
        playerName: String,
        bgm: Float,
        sfx: Float,
        haptics: Boolean,
        fps: Int,
        quality: String,
        batterySaver: Boolean,
        language: String,
        role: String
    ) -> Unit,
    onResetProgress: () -> Unit,
    modifier: Modifier = Modifier
) {
    var playerName by remember(profile) { mutableStateOf(profile?.playerName ?: "Chieftain Bharat") }
    var bgmVolume by remember(profile) { mutableFloatStateOf(profile?.bgmVolume ?: 0.8f) }
    var sfxVolume by remember(profile) { mutableFloatStateOf(profile?.sfxVolume ?: 0.9f) }
    var hapticsEnabled by remember(profile) { mutableStateOf(profile?.hapticsEnabled ?: true) }
    var targetFps by remember(profile) { mutableIntStateOf(profile?.targetFps ?: 60) }
    var graphicsQuality by remember(profile) { mutableStateOf(profile?.graphicsQuality ?: "High ✨") }
    var batterySaver by remember(profile) { mutableStateOf(profile?.batterySaver ?: false) }
    var language by remember(profile) { mutableStateOf(profile?.language ?: "en") }
    var activeRole by remember(profile) { mutableStateOf(profile?.userRole ?: "Chieftain (Admin)") }

    var showEditNameDialog by remember { mutableStateOf(false) }
    var tempName by remember { mutableStateOf(playerName) }
    var showResetConfirmDialog by remember { mutableStateOf(false) }
    var selectedLogFilter by remember { mutableStateOf("ALL") }

    // Edit Name Dialog
    if (showEditNameDialog) {
        AlertDialog(
            onDismissRequest = { showEditNameDialog = false },
            title = { Text("Edit Chieftain Name", fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = tempName,
                    onValueChange = { tempName = it },
                    label = { Text("Chieftain Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        playerName = tempName
                        showEditNameDialog = false
                        onSaveSettings(playerName, bgmVolume, sfxVolume, hapticsEnabled, targetFps, graphicsQuality, batterySaver, language, activeRole)
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditNameDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Reset Confirm Dialog
    if (showResetConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showResetConfirmDialog = false },
            title = { Text("Reset Run Progress?", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error) },
            text = { Text("This will reset your coins to 5,000, energy to 25, and stars to 5. Building and hero levels remain safe.") },
            confirmButton = {
                Button(
                    onClick = {
                        onResetProgress()
                        showResetConfirmDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Reset Journey")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetConfirmDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    val filteredLogs = if (selectedLogFilter == "ALL") {
        activityLogs
    } else {
        activityLogs.filter { it.severity.equals(selectedLogFilter, ignoreCase = true) }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Header
        item {
            Column {
                Text(
                    text = "VILLAGE RUSH: BHARAT",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = SaffronPrimary,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = "Settings & Real-Time Monitoring",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // 2. Player Pass Plaque
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(SaffronPrimaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = OnSaffronPrimaryContainer,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = playerName,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            IconButton(
                                onClick = {
                                    tempName = playerName
                                    showEditNameDialog = true
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(14.dp))
                            }
                        }

                        Text(
                            text = "ID: ${profile?.playerTag ?: "#VR-9428-IN"} • Village 1: Punjab",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Role Badge
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = when {
                            activeRole.contains("Admin", true) -> SaffronPrimaryContainer
                            activeRole.contains("Moderator", true) -> EmeraldSecondaryContainer
                            else -> RoyalBlueTertiaryContainer
                        }
                    ) {
                        Text(
                            text = activeRole.substringBefore(" "),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }
            }
        }

        // 3. System Performance & Real-time Anomaly Monitor (Dashboard)
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shadowElevation = 3.dp,
                border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldSecondaryContainer)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Analytics, contentDescription = null, tint = EmeraldSecondary)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "SYSTEM PERFORMANCE & HEALTH",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        // Anomaly Alert Status Chip
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = EmeraldSecondaryContainer
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldSecondary, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("All Systems Nominal", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = OnEmeraldSecondaryContainer)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // 5 Metric Tiles
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        systemMetrics.take(3).forEach { metric ->
                            Surface(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.surfaceContainerHigh
                            ) {
                                Column(
                                    modifier = Modifier.padding(6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(metric.metricName, fontSize = 8.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(metric.currentValue, fontSize = 11.sp, fontWeight = FontWeight.Black, color = EmeraldSecondary)
                                    Text(metric.status, fontSize = 8.sp, fontWeight = FontWeight.Bold, color = EmeraldSecondary)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        systemMetrics.drop(3).forEach { metric ->
                            Surface(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.surfaceContainerHigh
                            ) {
                                Column(
                                    modifier = Modifier.padding(6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(metric.metricName, fontSize = 8.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(metric.currentValue, fontSize = 11.sp, fontWeight = FontWeight.Black, color = SaffronPrimary)
                                    Text(metric.status, fontSize = 8.sp, fontWeight = FontWeight.Bold, color = EmeraldSecondary)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Activity Logs Title & Filter
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Audit Activity Logs", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            listOf("ALL", "INFO", "SUCCESS", "ALERT").forEach { filter ->
                                Surface(
                                    modifier = Modifier.clickable { selectedLogFilter = filter },
                                    shape = RoundedCornerShape(6.dp),
                                    color = if (selectedLogFilter == filter) SaffronPrimary else MaterialTheme.colorScheme.surfaceContainerHigh
                                ) {
                                    Text(
                                        text = filter,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (selectedLogFilter == filter) OnSaffronPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Recent Logs Stream
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(10.dp))
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        filteredLogs.take(5).forEach { log ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(7.dp)
                                        .clip(CircleShape)
                                        .background(
                                            when (log.severity) {
                                                "ALERT" -> MaterialTheme.colorScheme.error
                                                "WARNING" -> SaffronPrimaryContainer
                                                "SUCCESS" -> EmeraldSecondary
                                                else -> RoyalBlueTertiary
                                            }
                                        )
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "${log.action} • ${log.details}",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1
                                    )
                                }
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = MaterialTheme.colorScheme.surfaceContainerHighest
                                ) {
                                    Text(
                                        text = log.userRole,
                                        fontSize = 7.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 3.dp, vertical = 1.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 4. Role-Based Access Control (RBAC) Switcher
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.AdminPanelSettings, contentDescription = null, tint = SaffronPrimary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "ROLE-BASED ACCESS CONTROL (RBAC)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Switch security role to test access boundaries and permissions:",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("Chieftain (Admin)", "Village Elder (Moderator)", "Runner (Player)").forEach { role ->
                            val isSelected = activeRole == role
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        activeRole = role
                                        onSaveSettings(playerName, bgmVolume, sfxVolume, hapticsEnabled, targetFps, graphicsQuality, batterySaver, language, role)
                                    }
                                    .shadow(if (isSelected) 2.dp else 0.dp, RoundedCornerShape(10.dp)),
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) SaffronPrimary else MaterialTheme.colorScheme.surfaceContainerHigh
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = role.substringBefore(" "),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) OnSaffronPrimary else MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = if (role.contains("Admin")) "Full Control" else if (role.contains("Mod")) "Supervise" else "Standard",
                                        fontSize = 9.sp,
                                        color = if (isSelected) SaffronPrimaryFixed else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 5. Audio & Sensations
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "AUDIO & SENSATIONS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // BGM Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.VolumeUp, contentDescription = null, tint = SaffronPrimary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Music (BGM)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Text("${(bgmVolume * 100).toInt()}%", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Slider(
                        value = bgmVolume,
                        onValueChange = {
                            bgmVolume = it
                            onSaveSettings(playerName, bgmVolume, sfxVolume, hapticsEnabled, targetFps, graphicsQuality, batterySaver, language, activeRole)
                        },
                        colors = SliderDefaults.colors(thumbColor = SaffronPrimary, activeTrackColor = SaffronPrimary)
                    )

                    // SFX Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.GraphicEq, contentDescription = null, tint = SaffronPrimary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Sound Effects (SFX)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Text("${(sfxVolume * 100).toInt()}%", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Slider(
                        value = sfxVolume,
                        onValueChange = {
                            sfxVolume = it
                            onSaveSettings(playerName, bgmVolume, sfxVolume, hapticsEnabled, targetFps, graphicsQuality, batterySaver, language, activeRole)
                        },
                        colors = SliderDefaults.colors(thumbColor = SaffronPrimary, activeTrackColor = SaffronPrimary)
                    )

                    // Haptics Switch
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Vibration, contentDescription = null, tint = SaffronPrimary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text("Haptic Feedback", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text("Vibrate on obstacle collisions & coins", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        Switch(
                            checked = hapticsEnabled,
                            onCheckedChange = {
                                hapticsEnabled = it
                                onSaveSettings(playerName, bgmVolume, sfxVolume, hapticsEnabled, targetFps, graphicsQuality, batterySaver, language, activeRole)
                            },
                            colors = SwitchDefaults.colors(checkedThumbColor = SaffronPrimary)
                        )
                    }
                }
            }
        }

        // 6. Graphics & Visuals
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "GRAPHICS & DISPLAY",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Target FPS Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Target Frame Rate", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf(30, 60).forEach { fps ->
                                val isSelected = targetFps == fps
                                Surface(
                                    modifier = Modifier.clickable {
                                        targetFps = fps
                                        onSaveSettings(playerName, bgmVolume, sfxVolume, hapticsEnabled, targetFps, graphicsQuality, batterySaver, language, activeRole)
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) SaffronPrimary else MaterialTheme.colorScheme.surfaceContainerHigh
                                ) {
                                    Text(
                                        text = "$fps FPS",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) OnSaffronPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Battery Saver
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Battery Saver Mode", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text("Reduces particles & background anims", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(
                            checked = batterySaver,
                            onCheckedChange = {
                                batterySaver = it
                                onSaveSettings(playerName, bgmVolume, sfxVolume, hapticsEnabled, targetFps, graphicsQuality, batterySaver, language, activeRole)
                            }
                        )
                    }
                }
            }
        }

        // 7. Save, Cloud Sync & Reset
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "DATA PERSISTENCE & STORAGE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.CloudDone, contentDescription = null, tint = EmeraldSecondary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Local Room DB & Encrypted Session Active",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                onSaveSettings(playerName, bgmVolume, sfxVolume, hapticsEnabled, targetFps, graphicsQuality, batterySaver, language, activeRole)
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary)
                        ) {
                            Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("SAVE NOW", fontSize = 11.sp, fontWeight = FontWeight.Black)
                        }

                        OutlinedButton(
                            onClick = { showResetConfirmDialog = true },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(imageVector = Icons.Default.RestartAlt, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("RESET PROGRESS", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
