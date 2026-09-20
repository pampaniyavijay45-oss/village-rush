package com.example.ui.components

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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HolidayVillage
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.ui.theme.EmeraldSecondary
import com.example.ui.theme.OnEmeraldSecondary
import com.example.ui.theme.OnSaffronPrimary
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.SaffronPrimaryContainer
import com.example.ui.theme.SaffronPrimaryFixed

@Composable
fun TutorialOverlay(
    onDismiss: () -> Unit,
    onStartBuilding: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xCC1E1C11))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(16.dp, RoundedCornerShape(22.dp)),
                shape = RoundedCornerShape(22.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Header Ribbon
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(SaffronPrimary, SaffronPrimaryContainer)
                                )
                            )
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = SaffronPrimaryFixed,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "ROYAL PANCHAYAT DECREE • STEP 1/6",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = OnSaffronPrimary,
                                letterSpacing = 0.5.sp
                            )
                        }

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(28.dp)
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

                    // Content
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Veer Avatar dialogue portrait
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .shadow(8.dp, CircleShape)
                                .clip(CircleShape)
                                .background(SaffronPrimaryContainer)
                                .border(2.dp, SaffronPrimaryFixed, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBZaF2u7lQ9uAwxKyfTtv-ji8NEmVrTREQDe1uedVzY_6Fi48NZ16A1YseNXN3ObIWbVlk4NdWtuVduMN2SsuWc2P2xDJdz41EX7WjhCLh7mZ1Nen5xG3-HraEwO9dPiaUi1qK1aIkD5NTVddiW6Vv2nlUfac2hE2ozdUGTy1Fx2WDgrON6kvSlzkPJ99UiTu31piwPRiktcZX9ARFR89paeG9aQow2Ys7b75D93rGoRigCXGPdKJafPA",
                                contentDescription = "Veer",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Chieftain Veer's Decree",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "“Greetings Chieftain! To build a flourishing settlement in the Punjab Wheatlands, begin by upgrading our Desi Mud House. Upgraded buildings generate passive offline grain income and earn Village Stars to unlock Thar Desert!”",
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Action button
                        Button(
                            onClick = onStartBuilding,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .shadow(4.dp, RoundedCornerShape(12.dp))
                                .testTag("tutorial_start_building_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = EmeraldSecondary,
                                contentColor = OnEmeraldSecondary
                            )
                        ) {
                            Text(
                                text = "LET'S BUILD! 🌾",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        TextButton(onClick = onDismiss) {
                            Text(
                                text = "Dismiss Decree",
                                fontSize = 11.sp,
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
