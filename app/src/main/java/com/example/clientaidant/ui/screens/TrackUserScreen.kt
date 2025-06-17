package com.example.clientaidant.ui.screens

import Appbar
import Screen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clientaidant.data.viewmodels.SocketViewModel
import com.example.clientaidant.ui.components.FloorPlanCanvasView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackUserScreen(
    navController: NavController,
    viewModel: SocketViewModel
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {   Text(
                    text = "Quitter le tracking",
                    color = Color.Black,
                    fontSize = 16.sp
                )  },
                navigationIcon = {
                    IconButton(onClick = {
                        // Action pour retourner/quitter

                        navController.navigate(Screen.Home.route) {
                            popUpTo(0) // Vide toute la pile
                        }
                    }) {

                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Retour",
                                tint = Color.Black
                            )

                        }

                },
               // colors = TopAppBarDefaults.topAppBarColors(
                //    containerColor = MaterialTheme.colorScheme.primary
               // )
            )
        }
    ) {innerPadding ->
        // Floor plan view
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .clip(RoundedCornerShape(16.dp))
                .shadow(elevation = 8.dp)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            FloorPlanCanvasView(
                modifier = Modifier.fillMaxSize(),
                socketViewModel = viewModel
            )
        }
    }
}