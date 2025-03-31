package com.example.clientaidant.ui.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.clientaidant.R
import com.example.clientaidant.ui.theme.AppColors
import com.example.clientaidant.ui.theme.PlusJakartaSans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationScreen() {
    val otpValues = remember { mutableStateListOf("", "", "", "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.darkBlue), // Background color
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Title
        Column(
            modifier = Modifier.padding(top = 140.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Verification",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = PlusJakartaSans
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "We have sent a code to your email",
                fontSize = 18.sp,
                color = Color.Gray,
                fontFamily = PlusJakartaSans
            )
            Text(
                text = "example@gmail.com",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = PlusJakartaSans
            )
        }

        // White Card for OTP and Button
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .fillMaxHeight(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ){

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            "CODE",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.SemiBold
                        )
                        Row(){
                            Text(
                                text = "Resend",
                                color = AppColors.darkBlue,
                                fontWeight = FontWeight.Bold,
                                fontFamily = PlusJakartaSans,
                                fontSize = 14.sp
                            )
                            Text(
                                text = " in 50 sec",
                                color = AppColors.darkBlue,
                                fontWeight = FontWeight.Normal,
                                fontFamily = PlusJakartaSans,
                                fontSize = 14.sp
                            )}

                    }

                    // OTP Input Fields
                    Row(
                        modifier = Modifier
                            .fillMaxWidth().padding(vertical = 20.dp)
                        ,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        otpValues.forEachIndexed { index, _ ->
                            OutlinedTextField(
                                value = otpValues[index],
                                onValueChange = { text ->
                                    if (text.length <= 1) {
                                        otpValues[index] = text
                                    }
                                },
                                modifier = Modifier
                                    .size(56.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    unfocusedBorderColor = Color.White,
                                    focusedBorderColor = Color.White,
                                    containerColor = AppColors.lightBlue
                                ),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number
                                ),
                                singleLine = true
                            )
                        }
                    }



                    Spacer(modifier = Modifier.height(26.dp))

                    // Verify Button
                    Button(
                        onClick = { /* Handle Verification */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8000)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "VERIFY",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = PlusJakartaSans
                        )
                    }
                }
            }
        }
    }
}