package com.example.clientaidant.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.clientaidant.R
import com.example.clientaidant.ui.components.PageIndicator
import com.example.clientaidant.ui.theme.AppColors
import com.example.clientaidant.ui.theme.PlusJakartaSans

@Composable
fun OnboardingScreen(navController: NavHostController) {
    val pages = listOf(
        "Welcome to IRCHAD aidant\n\nYour tool to support and assist visually impaired people indoors.",
        "Track movements in real time\n\nCommunicate with users and guide them through voice instructions and vibrations.",
        "Voice assistance and remote guidance\n\nCommunicate with users and guide them through voice instructions and vibrations.",
        "Stay informed with instant alerts\n\nReceive notifications of obstacles, emergencies or route deviations."
    )
    var hasMoved by remember { mutableStateOf(false) }

    var pageIndex by remember { mutableStateOf(0) }
    var accumulatedDrag by remember { mutableStateOf(0f) }  // Stores total drag distance
    val dragThreshold = 200f  // Increase this to reduce sensitivity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = { hasMoved = false }, // Reset flag when drag ends
                    onHorizontalDrag = { _, dragAmount ->
                        if (!hasMoved) { // Only trigger once per gesture
                            when {
                                dragAmount < -50 && pageIndex < pages.size - 1 -> { // Swipe left
                                    pageIndex++
                                    hasMoved = true
                                }
                                dragAmount > 50 && pageIndex > 0 -> { // Swipe right
                                    pageIndex--
                                    hasMoved = true
                                }
                            }
                        }
                    }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_dark),
            contentDescription = "App Logo",
            modifier = Modifier.padding(top = 160.dp).size(230.dp)
        )

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = pages[pageIndex].split("\n\n")[0],
            fontFamily = PlusJakartaSans,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = AppColors.writingBlue,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = pages[pageIndex].split("\n\n")[1],
            fontFamily = PlusJakartaSans,
            fontSize = 17.sp,
            color = AppColors.writingBlue.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )






        Box(
            modifier = Modifier.fillMaxSize().padding(bottom = 30.dp),
            contentAlignment = Alignment.BottomCenter
        ){
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    PageIndicator(totalDots = pages.size, selectedIndex = pageIndex)
                }
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = {
                        if (pageIndex < pages.size - 1) pageIndex++
                        else navController.navigate("registration")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8000)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(64.dp)
                ) {
                    Text("NEXT", fontWeight = FontWeight.Bold, fontFamily = PlusJakartaSans)
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = { /* Handle Skip */ },
                    modifier = Modifier.fillMaxWidth().height(64.dp),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text("Skip", color = Color.Gray, fontFamily = PlusJakartaSans)
                }
            }
        }
    }
}