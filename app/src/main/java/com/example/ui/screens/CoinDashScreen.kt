package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
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
import kotlinx.coroutines.delay
import kotlin.random.Random

data class RunnerEntityItem(
    val id: Long,
    val lane: Int, // -1: Left, 0: Center, 1: Right
    var yProgress: Float, // 0.0 (top / horizon) to 1.0 (bottom / player)
    val type: EntityType
)

enum class EntityType {
    COIN,
    GEM,
    BULLOCK_CART,
    BOULDER
}

@Composable
fun CoinDashScreen(
    onRunFinished: (coins: Int, distance: Int, streak: Int) -> Unit,
    onAbortRun: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Game Telemetry State
    var timeLeftSeconds by remember { mutableIntStateOf(30) }
    var score by remember { mutableIntStateOf(0) }
    var coinsCollected by remember { mutableIntStateOf(0) }
    var gemsCollected by remember { mutableIntStateOf(0) }
    var distanceMeters by remember { mutableIntStateOf(0) }
    var comboStreak by remember { mutableIntStateOf(0) }
    var isDashing by remember { mutableStateOf(false) }
    var dashCooldown by remember { mutableFloatStateOf(1f) }

    // Player Lane: -1 = Left, 0 = Center, 1 = Right
    var playerLane by remember { mutableIntStateOf(0) }
    var isHitCooldown by remember { mutableStateOf(false) }

    // Spawned Items on Road
    val roadEntities = remember { mutableStateListOf<RunnerEntityItem>() }

    // Road Motion Animation
    val infiniteTransition = rememberInfiniteTransition(label = "road_stripes")
    val stripeOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (isDashing) 200 else 450, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "stripes"
    )

    // Game Loop Timer (30 seconds countdown)
    LaunchedEffect(Unit) {
        while (timeLeftSeconds > 0) {
            delay(1000)
            timeLeftSeconds--
            distanceMeters += if (isDashing) 14 else 8
            score += if (isDashing) 240 else 110
        }
        // When time expires, finalize run and navigate to Victory!
        onRunFinished(coinsCollected, distanceMeters, comboStreak)
    }

    // Entity Movement & Collision Loop (Runs ~40 FPS)
    LaunchedEffect(Unit) {
        var nextEntityId = 1L
        var ticks = 0
        while (timeLeftSeconds > 0) {
            delay(25)
            ticks++

            // Spawn new entity every ~24 ticks
            if (ticks % (if (isDashing) 16 else 24) == 0) {
                val randVal = Random.nextFloat()
                val lane = Random.nextInt(-1, 2)
                val type = when {
                    randVal < 0.45f -> EntityType.COIN
                    randVal < 0.65f -> EntityType.COIN
                    randVal < 0.75f -> EntityType.GEM
                    randVal < 0.90f -> EntityType.BULLOCK_CART
                    else -> EntityType.BOULDER
                }
                roadEntities.add(RunnerEntityItem(nextEntityId++, lane, 0.05f, type))
            }

            // Move entities down
            val speed = if (isDashing) 0.024f else 0.014f
            val iterator = roadEntities.listIterator()
            while (iterator.hasNext()) {
                val entity = iterator.next()
                entity.yProgress += speed

                // Collision detection near player (yProgress between 0.78 and 0.92)
                if (entity.yProgress in 0.76f..0.92f && entity.lane == playerLane) {
                    when (entity.type) {
                        EntityType.COIN -> {
                            coinsCollected += if (isDashing) 2 else 1
                            comboStreak += 1
                            score += 50
                            iterator.remove()
                        }
                        EntityType.GEM -> {
                            gemsCollected += 1
                            comboStreak += 2
                            score += 200
                            iterator.remove()
                        }
                        EntityType.BULLOCK_CART, EntityType.BOULDER -> {
                            if (!isDashing) {
                                // Hit obstacle: break streak
                                comboStreak = 0
                                isHitCooldown = true
                            }
                            // Dash smashes obstacle!
                            iterator.remove()
                        }
                    }
                } else if (entity.yProgress > 1.05f) {
                    iterator.remove()
                }
            }
        }
    }

    // Dash Duration Controller
    LaunchedEffect(isDashing) {
        if (isDashing) {
            delay(4000)
            isDashing = false
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1E1C11))
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount < -30 && playerLane > -1) {
                        playerLane--
                    } else if (dragAmount > 30 && playerLane < 1) {
                        playerLane++
                    }
                }
            }
    ) {
        // Perspective 3D Road Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Sky / Horizon gradient
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFFF9F0A), Color(0xFF885200), Color(0xFF28251B)),
                    startY = 0f,
                    endY = canvasHeight * 0.35f
                ),
                topLeft = Offset(0f, 0f),
                size = Size(canvasWidth, canvasHeight * 0.35f)
            )

            // Green Punjab wheat fields on sides
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF006D43), Color(0xFF003822)),
                    startY = canvasHeight * 0.35f,
                    endY = canvasHeight
                ),
                topLeft = Offset(0f, canvasHeight * 0.35f),
                size = Size(canvasWidth, canvasHeight * 0.65f)
            )

            // Road trapezoid (horizon to bottom)
            val horizonY = canvasHeight * 0.35f
            val horizonWidth = canvasWidth * 0.30f
            val horizonLeft = (canvasWidth - horizonWidth) / 2f
            val horizonRight = horizonLeft + horizonWidth

            val roadBottomWidth = canvasWidth * 0.88f
            val roadBottomLeft = (canvasWidth - roadBottomWidth) / 2f
            val roadBottomRight = roadBottomLeft + roadBottomWidth

            val roadPath = Path().apply {
                moveTo(horizonLeft, horizonY)
                lineTo(horizonRight, horizonY)
                lineTo(roadBottomRight, canvasHeight)
                lineTo(roadBottomLeft, canvasHeight)
                close()
            }

            drawPath(
                path = roadPath,
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF4A3828), Color(0xFF2B1F16)),
                    startY = horizonY,
                    endY = canvasHeight
                )
            )

            // Dotted Lane Dividers
            val numStripes = 8
            for (i in 0 until numStripes) {
                val progress = ((i.toFloat() + stripeOffset) / numStripes)
                val y = horizonY + (canvasHeight - horizonY) * progress
                val currentRoadWidth = horizonWidth + (roadBottomWidth - horizonWidth) * progress
                val leftEdge = (canvasWidth - currentRoadWidth) / 2f
                val stripeHeight = 12f * (1f + progress * 2f)

                // Divider 1 (between left and center)
                val d1X = leftEdge + currentRoadWidth * 0.33f
                drawLine(
                    color = Color(0xFFFFDDBB).copy(alpha = 0.6f),
                    start = Offset(d1X, y),
                    end = Offset(d1X, y + stripeHeight),
                    strokeWidth = 3f * (1f + progress)
                )

                // Divider 2 (between center and right)
                val d2X = leftEdge + currentRoadWidth * 0.66f
                drawLine(
                    color = Color(0xFFFFDDBB).copy(alpha = 0.6f),
                    start = Offset(d2X, y),
                    end = Offset(d2X, y + stripeHeight),
                    strokeWidth = 3f * (1f + progress)
                )
            }
        }

        // Render Road Entities (Coins, Gems, Bullock Carts, Boulders)
        roadEntities.forEach { entity ->
            val horizonYRatio = 0.35f
            val yScreen = horizonYRatio + (1f - horizonYRatio) * entity.yProgress
            val scale = 0.5f + entity.yProgress * 0.8f

            val xOffsetPercent = when (entity.lane) {
                -1 -> 0.22f - (0.08f * entity.yProgress)
                1 -> 0.78f + (0.08f * entity.yProgress)
                else -> 0.50f
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(
                            x = (320.dp * xOffsetPercent - (16.dp * scale)),
                            y = (600.dp * yScreen)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (entity.type) {
                        EntityType.COIN -> {
                            Box(
                                modifier = Modifier
                                    .size(32.dp * scale)
                                    .clip(CircleShape)
                                    .background(SaffronPrimaryContainer)
                                    .border(1.5.dp, SaffronPrimaryFixed, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MonetizationOn,
                                    contentDescription = "Coin",
                                    tint = OnSaffronPrimaryContainer,
                                    modifier = Modifier.size(22.dp * scale)
                                )
                            }
                        }
                        EntityType.GEM -> {
                            Box(
                                modifier = Modifier
                                    .size(30.dp * scale)
                                    .clip(CircleShape)
                                    .background(RoyalBlueTertiaryContainer)
                                    .border(1.5.dp, Color.Cyan, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Diamond,
                                    contentDescription = "Gem",
                                    tint = OnRoyalBlueTertiaryContainer,
                                    modifier = Modifier.size(20.dp * scale)
                                )
                            }
                        }
                        EntityType.BULLOCK_CART -> {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFBA1A1A),
                                shadowElevation = 4.dp
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = "Danger",
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp * scale)
                                    )
                                    Text(
                                        "CART",
                                        fontSize = (9 * scale).sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                        EntityType.BOULDER -> {
                            Box(
                                modifier = Modifier
                                    .size(36.dp * scale)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF673D00))
                                    .border(2.dp, Color(0xFF885200), RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "ROCK",
                                    fontSize = (8 * scale).sp,
                                    fontWeight = FontWeight.Black,
                                    color = SaffronPrimaryFixed
                                )
                            }
                        }
                    }
                }
            }
        }

        // Chieftain Runner Avatar at Bottom
        val playerXPercent = when (playerLane) {
            -1 -> 0.20f
            1 -> 0.80f
            else -> 0.50f
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 120.dp)
                    .offset(x = (320.dp * playerXPercent - 32.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Dash aura / Speed flame
                if (isDashing) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(EmeraldSecondaryContainer, Color.Transparent)
                                )
                            )
                    )
                }

                // Chieftain Veer Character Card
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isDashing) EmeraldSecondaryContainer else SaffronPrimaryContainer,
                    shadowElevation = 8.dp,
                    border = androidx.compose.foundation.BorderStroke(
                        2.dp,
                        if (isDashing) EmeraldSecondary else SaffronPrimaryFixed
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBZaF2u7lQ9uAwxKyfTtv-ji8NEmVrTREQDe1uedVzY_6Fi48NZ16A1YseNXN3ObIWbVlk4NdWtuVduMN2SsuWc2P2xDJdz41EX7WjhCLh7mZ1Nen5xG3-HraEwO9dPiaUi1qK1aIkD5NTVddiW6Vv2nlUfac2hE2ozdUGTy1Fx2WDgrON6kvSlzkPJ99UiTu31piwPRiktcZX9ARFR89paeG9aQow2Ys7b75D93rGoRigCXGPdKJafPA",
                            contentDescription = "Runner Veer",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(14.dp))
                        )
                    }
                }

                // Runner shadow & dust
                Text(
                    text = if (isDashing) "⚡ SPEED DASH!" else "VEER",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black,
                    color = if (isDashing) EmeraldSecondaryContainer else Color.White
                )
            }
        }

        // Top Telemetry HUD
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.Black.copy(alpha = 0.75f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Circular 30s Countdown
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(if (timeLeftSeconds < 10) Color(0xFFBA1A1A) else SaffronPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${timeLeftSeconds}s",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "$score",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                        Text(
                            text = "$distanceMeters m",
                            fontSize = 10.sp,
                            color = SaffronPrimaryFixed
                        )
                    }
                }

                // Streak Multiplier Badge
                if (comboStreak > 2) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = SaffronPrimaryContainer
                    ) {
                        Text(
                            text = "🔥 ${(1.0 + comboStreak * 0.1).let { "%.1fx".format(it) }} STREAK: $comboStreak",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = OnSaffronPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                // Coins & Gems Tally
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.MonetizationOn,
                            contentDescription = null,
                            tint = SaffronPrimaryContainer,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "$coinsCollected",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Diamond,
                            contentDescription = null,
                            tint = Color.Cyan,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = "$gemsCollected",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    IconButton(
                        onClick = onAbortRun,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Pause,
                            contentDescription = "Pause",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // Bottom Controls: Steer Left, DASH, Steer Right
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Hint text
            Text(
                text = "Tap arrows or swipe screen to dodge roadblocks!",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Steer Left Button
                Button(
                    onClick = {
                        if (playerLane > -1) playerLane--
                    },
                    modifier = Modifier
                        .size(width = 85.dp, height = 54.dp)
                        .testTag("steer_left_button"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Steer Left",
                        modifier = Modifier.size(24.dp)
                    )
                }

                // DASH BOOST Button
                Button(
                    onClick = {
                        if (!isDashing) {
                            isDashing = true
                        }
                    },
                    enabled = !isDashing,
                    modifier = Modifier
                        .size(width = 130.dp, height = 54.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp))
                        .testTag("dash_boost_button"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmeraldSecondary,
                        contentColor = OnEmeraldSecondary
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isDashing) "DASHING!" else "⚡ DASH!",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }

                // Steer Right Button
                Button(
                    onClick = {
                        if (playerLane < 1) playerLane++
                    },
                    modifier = Modifier
                        .size(width = 85.dp, height = 54.dp)
                        .testTag("steer_right_button"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForwardIos,
                        contentDescription = "Steer Right",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
