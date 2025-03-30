package com.example.clientaidant.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clientaidant.R
import com.example.clientaidant.ui.components.component1
import com.example.clientaidant.ui.theme.AppColors
import com.example.clientaidant.ui.theme.PlusJakartaSans

@Composable
fun TrackUser() {
    var isLifted by remember { mutableStateOf(false) }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val cardHeight by animateDpAsState(
        targetValue = if (isLifted) screenHeight * 0.85f else screenHeight * 0.25f,
        animationSpec = tween(durationMillis = 300), label = ""
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.map),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Top Section (Back button + Title)
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp, top = 38.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start // Align to the left
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = null,
                )
                Text(
                    text = "Track User",
                    modifier = Modifier.padding(8.dp),
                    color = AppColors.darkBlue,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = PlusJakartaSans,
                )
            }
        }

        // Center Image
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.track),
                contentDescription = null,
            )
        }

        // Bottom Section (White Card)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
                .pointerInput(Unit) {
                    detectVerticalDragGestures(
                        onDragEnd = { },
                        onVerticalDrag = { _, dragAmount ->
                            if (dragAmount > 10 && isLifted) {
                                // Dragging down when lifted -> collapse to 20%
                                isLifted = false
                            } else if (dragAmount < -10 && !isLifted) {
                                // Dragging up when collapsed -> expand to 70%
                                isLifted = true
                            }
                        }
                    )
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.top),
                    contentDescription = null,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.restau),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "John Doe",
                            color = AppColors.darkBlue,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Medium,
                            fontFamily = PlusJakartaSans,
                        )
                        Text(
                            text = "Departed at 06 Sept, 10:00pm",
                            color = AppColors.greyBlue,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal,
                            fontFamily = PlusJakartaSans,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (isLifted) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.track2),
                                contentDescription = null,
                                modifier = Modifier.size(90.dp),
                            )

                            Column {
                                Column {
                                    Text(
                                        text = "Departure",
                                        color = AppColors.greyBlue,
                                        fontSize = 13.sp,
                                        textAlign = TextAlign.Start,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = PlusJakartaSans,
                                    )

                                    Text(
                                        text = "Main hall",
                                        color = AppColors.darkBlue,
                                        fontSize = 15.sp,
                                        textAlign = TextAlign.Start,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = PlusJakartaSans,
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Column {
                                    Text(
                                        text = "Arrival",
                                        color = AppColors.greyBlue,
                                        fontSize = 13.sp,
                                        textAlign = TextAlign.Start,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = PlusJakartaSans,
                                    )
                                    Text(
                                        text = "Exit",
                                        color = AppColors.darkBlue,
                                        fontSize = 15.sp,
                                        textAlign = TextAlign.Start,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = PlusJakartaSans,
                                    )
                                }
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "20 min",
                                color = AppColors.darkBlue,
                                fontSize = 40.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontFamily = PlusJakartaSans,
                            )

                            Text(
                                text = "ESTIMATED TIME OF ARRIVAL",
                                color = AppColors.greyBlue,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Normal,
                                fontFamily = PlusJakartaSans,
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 32.dp),
                        ) {
                            component1(
                                imageRes = R.drawable.group1,
                                text = "Obstacle detected ahead",
                                color = AppColors.primary,
                            )
                            component1(
                                imageRes = R.drawable.group3,
                                text = "User has deviated from the route.",
                                color = AppColors.greyBlue,
                            )
                            component1(
                                imageRes = R.drawable.group2,
                                text = "User has been re-routed",
                                color = AppColors.greyBlue,
                            )
                            component1(
                                imageRes = R.drawable.group2,
                                text = "User arrival",
                                color = AppColors.greyBlue,
                            )
                        }
                    }
                }
            }
        }
    }
}
