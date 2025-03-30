package com.example.clientaidant


import AnimatedBottomNavigationBar
import DefaultNotificationsPreview
import DefaultPreviewOfHomeScreen
import NavigationController
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController

import com.example.clientaidant.ui.theme.ClientAidantTheme
import com.example.myapplication.AccountScreenNavHost
import com.example.myapplication.MyModernAppTheme
import com.example.myapplication.UserProfile
import com.example.myapplication.getSampleFaqs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClientAidantTheme {

                val navController = rememberNavController()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5F5F5))
                        .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
                ) {
                    NavigationController(navController)
                    AnimatedBottomNavigationBar(navController)

//                    DefaultNotificationsPreview()
                }

            }




            // Sample Data (Replace with your actual data source/ViewModel)
//            val userProfile = remember {
//                mutableStateOf(UserProfile("Aymen Bouslama", "aymen.b@example.com", "+1 123-456-7890"))
//            }
//            val pushNotificationsEnabled = rememberSaveable { mutableStateOf(true) }
//            val faqs = remember { getSampleFaqs() }
//            val contactEmail = "support@yourapp.com"
//            val contactPhone = "+1-800-SUPPORT"
//
//            MyModernAppTheme { // Apply the *new* custom theme
//                AccountScreenNavHost(
//                    userProfile = userProfile.value,
//                    pushNotificationsEnabled = pushNotificationsEnabled.value,
//                    faqs = faqs,
//                    contactEmail = contactEmail,
//                    contactPhone = contactPhone,
//                    onSaveProfile = { updatedProfile ->
//                        println("Saving profile: $updatedProfile")
//                        userProfile.value = updatedProfile
//                    },
//                    onChangePassword = { current, new ->
//                        println("Changing password: Current=$current, New=$new")
//                        true // Simulate success
//                    },
//                    onNotificationToggle = { enabled ->
//                        println("Notifications toggled: $enabled")
//                        pushNotificationsEnabled.value = enabled
//                    },
//                    onBugSubmit = { report ->
//                        println("Submitting bug report: $report")
//                        true // Simulate success
//                    },
//                    onLogout = { println("Logging out...") },
//                    onDeleteAccount = { println("Deleting account...") }
//                )
//            }
        }
    }
}



