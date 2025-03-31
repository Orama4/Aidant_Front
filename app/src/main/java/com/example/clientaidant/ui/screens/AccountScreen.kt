import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.Notes // Alternative for Report Bug
import androidx.compose.material.icons.filled.* // Import base filled icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.clientaidant.R
import com.example.clientaidant.ui.theme.AppColors
import com.example.clientaidant.ui.theme.PlusJakartaSans


data class UserProfile(
    val name: String,
    val email: String,
    val phone: String
)

data class FaqItem(
    val id: String,
    val question: String,
    val answer: String
)

// --- Account Main Screen (Revamped UI) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountMainScreen(navController: NavController) {
    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 10.dp) // Adjust padding
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp,) .background(Color.White) ,
                horizontalArrangement = Arrangement.Start // Align to the left
            ) {
                Image(
                    painter = painterResource(id = R.drawable.d_back),
                    contentDescription = null,
                )
                Text(
                    text = "Account Settings",
                    modifier = Modifier.padding(8.dp),
                    color = AppColors.darkBlue,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = PlusJakartaSans,
                )
            }
            SectionHeader("General") // Added Section Header
            SettingsCard { // Group related items in a Card
                SettingsItem(
                    icon = Icons.Filled.PersonOutline, // Changed Icon
                    title = "Profile Information",
                    onClick = { navController.navigate(Screen.Profile_info.route)
                    {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }}
                )
                SettingsDivider()
                SettingsItem(
                    icon = Icons.Filled.LockOpen, // Changed Icon
                    title = "Change Password",
                    onClick = { navController.navigate(Screen.Change_password.route)
                    {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }}
                )
                SettingsDivider()
                SettingsItem(
                    icon = Icons.Filled.NotificationsNone, // Changed Icon
                    title = "Push Notifications",
                    onClick = { navController.navigate(Screen.Push_notifications.route)
                    {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }}
                )
            }

            Spacer(modifier = Modifier.height(15.dp))
            SectionHeader("Support") // Added Section Header
            SettingsCard {
                SettingsItem(
                    icon = Icons.AutoMirrored.Filled.HelpOutline,
                    title = "FAQ / Help Center",
                    onClick = { navController.navigate(Screen.Faq.route)
                    {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }}
                )
                SettingsDivider()
                SettingsItem(
                    icon = Icons.AutoMirrored.Filled.HelpOutline, // Changed Icon
                    title = "Contact Support",
                    onClick = { navController.navigate(Screen.Contact_support.route)
                    {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }}
                )
                SettingsDivider()
                SettingsItem(
                    icon = Icons.AutoMirrored.Filled.Notes, // Changed Icon (BugReport is not standard filled)
                    title = "Report a Bug",
                    onClick = { navController.navigate(Screen.Report_bug.route)
                    {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }}
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader("Account Actions") // Added Section Header
            SettingsCard {
                SettingsItem(
                    icon = Icons.AutoMirrored.Filled.Logout,
                    title = "Logout / Delete Account",
                    onClick = { navController.navigate(Screen.Logout_delete.route)
                    {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }},
                )
            }
            Spacer(modifier = Modifier.height(16.dp)) // Space at the bottom
        }
    }
}
