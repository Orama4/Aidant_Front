package com.example.clientaidant.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.clientaidant.R
import com.example.clientaidant.data.api.LoginRequest
import com.example.clientaidant.data.viewmodels.AuthViewModel
import com.example.clientaidant.ui.theme.AppColors
import com.example.clientaidant.ui.theme.PlusJakartaSans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(context : Context, authViewModel: AuthViewModel, navController: NavController) {
    var checkState by remember { mutableStateOf(false) }
    val textStates = remember { mutableStateListOf("", "") }
    var passwordVisible by remember { mutableStateOf(false) }


    val loginSuccess by authViewModel.loginSuccess.collectAsState()
    val isLoading by authViewModel.loading.collectAsState()


    LaunchedEffect(loginSuccess) {
        if (loginSuccess != null) {
            navController.navigate(Screen.Home.route){
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.darkBlue), // Dark background
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(modifier = Modifier.padding(top=140.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Log In",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = PlusJakartaSans,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Please sign in to your existing account",
                fontFamily = PlusJakartaSans,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        }


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top=40.dp)
                .fillMaxHeight(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ){
                    // Email Field
                    Text("EMAIL", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
                    OutlinedTextField(
                        value = textStates[0],
                        onValueChange = {textStates[0] = it},
                        placeholder = { Text("example@gmail.com", color = Color.Gray) },
                        modifier = Modifier.fillMaxWidth().height(64.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color.White,
                            focusedBorderColor = Color.White,
                            containerColor = AppColors.lightBlue
                        )
                    )

                    Spacer(modifier = Modifier.height(26.dp))

                    // Password Field
                    Text("PASSWORD", fontSize = 12.sp, color = Color.Gray,fontWeight = FontWeight.SemiBold)
                    OutlinedTextField(
                        value = textStates[1],
                        onValueChange = {textStates[1] = it},
                        placeholder = { Text("********", color = Color.Gray) },
                        modifier = Modifier.fillMaxWidth().height(64.dp),
                        shape = RoundedCornerShape(8.dp),

                        visualTransformation = if ( !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible },modifier = Modifier.padding(end = 8.dp)) {

                                Icon(
                                    imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                                )
                            }
                        },

                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color.White,
                            focusedBorderColor = Color.White,
                            containerColor = AppColors.lightBlue
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Remember Me & Forgot Password
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = checkState,
                            onCheckedChange = {checkState = it},
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.Gray.copy(alpha = 0.6f),  // Change checkbox color & set opacity
                                uncheckedColor = Color.Gray.copy(alpha = 0.3f),  // Change unchecked color & set opacity
                                checkmarkColor = Color.White  // Change checkmark color
                            )
                        )
                        Text("Remember me", color = Color.Gray.copy(0.6f), fontFamily = PlusJakartaSans)
                    }
                    Text("Forgot Password", color = Color(0xFFFF8000), fontWeight = FontWeight.Bold, fontFamily = PlusJakartaSans,modifier = Modifier.clickable(onClick = { navController.navigate(Screen.ForgotPassword.route)}))
                }

                Spacer(modifier = Modifier.height(26.dp))

                // Login Button
                Button(
                    onClick = { /* Handle Login */
                        authViewModel.login(LoginRequest(textStates[0], textStates[1]))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8000)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("LOG IN", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White, fontFamily = PlusJakartaSans)
                }

                Spacer(modifier = Modifier.height(26.dp))

                // Sign Up
                Row {
                    Text("Don't have an account?", color = Color.Gray, fontFamily = PlusJakartaSans)
                    Text(" SIGN UP", color = Color(0xFFFF8000), fontWeight = FontWeight.Bold, fontFamily = PlusJakartaSans, modifier = Modifier.clickable(onClick = { navController.navigate(
                        Screen.Registration.route) }))
                }

                Spacer(modifier = Modifier.height(38.dp))
                Text("Or", color = Color.Gray, fontFamily = PlusJakartaSans)

                Spacer(modifier = Modifier.height(28.dp))

                // Social Media Login
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    SocialIcon(
                        painter = painterResource(id = R.drawable.ic_fb),

                        backgroundColor = Color(0xFF395998)
                    )
                    SocialIcon(
                        painter = painterResource(id = R.drawable.ic_twitter),

                        backgroundColor = Color(0xFF169CE8)
                    )
                    SocialIcon(
                        painter = painterResource(id = R.drawable.ic_apple),

                        backgroundColor = Color.Black
                    )
                }

            }
        }
    }
}

@Composable
fun SocialIcon(
    painter: Painter,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,  // Default size
    backgroundColor: Color = Color.Gray  // Default color
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor)
            .padding(12.dp), // Adjust padding for icon spacing
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(size * 0.6f) // Adjust icon size relative to circle
        )
    }
}
