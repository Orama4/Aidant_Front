package com.example.clientaidant.ui.screens


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clientaidant.R
import com.example.clientaidant.ui.theme.AppColors
import com.example.clientaidant.ui.theme.PlusJakartaSans
import androidx.compose.ui.platform.LocalContext
import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemoteAssistance() {
    var text by remember { mutableStateOf("") }
    val context = LocalContext.current


    // Top Section (Back button + Title)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 42.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = null,
                    )
                    Text(
                        text = "Remote Assistance",
                        modifier = Modifier.padding(8.dp),
                        color = AppColors.darkBlue,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Medium,
                        fontFamily = PlusJakartaSans,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column {
                    Text(
                        text = "On the move",
                        modifier = Modifier.padding(8.dp),
                        color = AppColors.green,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PlusJakartaSans,
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
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

                Spacer(modifier = Modifier.height(20.dp))

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


                Spacer(modifier = Modifier.height(20.dp))



                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            val phoneNumber = "tel:+213623456789" // recuperer le num a partir de la bdd

                            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                                == PackageManager.PERMISSION_GRANTED
                            ) {
                                val intent = Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber))
                                context.startActivity(intent)
                            } else {
                                // Permission is NOT granted, request it (handled outside Compose)
                                // You need to request CALL_PHONE permission in your activity
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.primary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.call),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "CALL USER",
                                color = AppColors.white,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = PlusJakartaSans,
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                //Navigation Control
                Column {
                    Column {
                        Text(
                            text = "Navigation Control",
                            modifier = Modifier.padding(8.dp),
                            color = AppColors.darkBlue,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = PlusJakartaSans,
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Store the currently selected button
                    var selectedButton by remember { mutableStateOf<String?>(null) }

                    Row {
                        Button(
                            onClick = { selectedButton = "left" },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedButton == "left") AppColors.primary else AppColors.white
                            ),
                            modifier = Modifier
                                .then(
                                    if (selectedButton != "left") Modifier.border(1.dp, AppColors.grey, shape = RoundedCornerShape(32.dp))
                                    else Modifier
                                )
                        ) {
                            Text(
                                text = "Turn Left",
                                color = if (selectedButton == "left") AppColors.white else AppColors.darkBlue,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = PlusJakartaSans,
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = { selectedButton = "straight" },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedButton == "straight") AppColors.primary else AppColors.white
                            ),
                            modifier = Modifier
                                .then(
                                    if (selectedButton != "straight") Modifier.border(1.dp, AppColors.grey, shape = RoundedCornerShape(32.dp))
                                    else Modifier
                                )
                        ) {
                            Text(
                                text = "Proceed Straight",
                                color = if (selectedButton == "straight") AppColors.white else AppColors.darkBlue,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = PlusJakartaSans,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row {
                        Button(
                            onClick = { selectedButton = "right" },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedButton == "right") AppColors.primary else AppColors.white
                            ),
                            modifier = Modifier
                                .then(
                                    if (selectedButton != "right") Modifier.border(1.dp, AppColors.grey, shape = RoundedCornerShape(32.dp))
                                    else Modifier
                                )
                        ) {
                            Text(
                                text = "Turn Right",
                                color = if (selectedButton == "right") AppColors.white else AppColors.darkBlue,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = PlusJakartaSans,
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = { selectedButton = "stop" },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedButton == "stop") AppColors.primary else AppColors.white
                            ),
                            modifier = Modifier
                                .then(
                                    if (selectedButton != "stop") Modifier.border(1.dp, AppColors.grey, shape = RoundedCornerShape(32.dp))
                                    else Modifier
                                )
                        ) {
                            Text(
                                text = "Stop",
                                color = if (selectedButton == "stop") AppColors.white else AppColors.darkBlue,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = PlusJakartaSans,
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Distance:",
                            color = AppColors.darkBlue,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Medium,
                            fontFamily = PlusJakartaSans,
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        OutlinedTextField(
                            value = text,
                            onValueChange = { text = it },
                            placeholder = { Text("eg: 5 meters") },
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(32.dp),
                            singleLine = true,
                            textStyle = TextStyle(color = AppColors.darkBlue,fontFamily = PlusJakartaSans,fontSize = 15.sp,),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = AppColors.primary,
                                unfocusedBorderColor = AppColors.grey,
                                cursorColor = AppColors.darkBlue
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { /* Your action here */ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp), // Adjust height here
                            colors = ButtonDefaults.buttonColors(containerColor = AppColors.primary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.message),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "SEND MESSAGE",
                                    color = AppColors.white,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = PlusJakartaSans,
                                )
                            }
                        }
                    }

                }


                //Map section

                Spacer(modifier = Modifier.height(32.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(bottom = 60.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.map),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Image(
                        painter = painterResource(id = R.drawable.track),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center),
                        contentScale = ContentScale.Fit
                    )
                }


            }
        }
}
