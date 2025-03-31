import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.clientaidant.ui.screens.TrackUser

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Tracking : Screen("tracking")
    object Notifications : Screen("notifications")
    object Registration : Screen("registration")
    object Login : Screen("login")
    object Main_account : Screen("main_account")
    object Profile_info : Screen("profile_info")
    object Change_password : Screen("change_password")
    object Push_notifications : Screen("push_notifications")
    object Faq : Screen("faq")
    object Contact_support : Screen("contact_support")
    object Report_bug : Screen("report_bug")
    object Logout_delete : Screen("logout_delete")


}

@Composable
fun NavigationController(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {   HomeScreen(
            userName = "John",
            notificationCount = 2,
            assistedUsers = listOf(
                AssistedUser(1, "John Doe", "Main Hall", "29 JAN, 12:30", UserStatus.ON_THE_MOVE),
                AssistedUser(2, "Jean Dupont", "Main Hall", "29 JAN, 12:30", UserStatus.WAITING),
                AssistedUser(3, "Alice Martin", "Cafeteria", "29 JAN, 11:15", UserStatus.WAITING),
                AssistedUser(4, "Bob Garcia", "Entrance B", "29 JAN, 12:35", UserStatus.IN_ASSISTANCE),
            ),
            onUserSearch = {  },
            onTrackLocationClick = {
            },
            onRemoteAssistanceClick = { userId ->
            },
            onNotificationClick = {
                navController.navigate(Screen.Notifications.route) {
                    // Pop up to the start destination (now Home)
                    popUpTo(navController.graph.findStartDestination().id) { // Use findStartDestination() for robustness
                        saveState = true
                    }
                    // Avoid multiple copies
                    launchSingleTop = true
                    // Restore state if needed
                    restoreState = true
                }
            },
            onBackClick = {
                navController.popBackStack()
            }
        ) }
        composable(Screen.Tracking.route) { TrackUser() }
        composable(Screen.Notifications.route) {
            val sampleMessages = listOf(
                MessageData("m1", "Royal Parvej", "Sounds awesome!", "19:37", 1, true),
                MessageData("m2", "Cameron Williamson", "Ok, Just hurry up little bit...😊", "19:37", 2, true),
                MessageData("m3", "Ralph Edwards", "Thanks dude.", "19:37", null, true), // No unread count
                MessageData("m4", "Cody Fisher", "How is going...?", "19:37", null, true),
                MessageData("m5", "Eleanor Pena", "Thanks for the awesome food man...!", "19:37", null, false), // Offline
                MessageData("m6", "Esther Howard", "See you tomorrow!", "19:35", 5, true) // More unread
            )
            val sampleNotifications = listOf(
                NotificationData("n1", "John Doe", "requested assistance", "20 min ago"),
                NotificationData("n2", "John Doe", "requested assistance", "20 min ago"),
                NotificationData("n3", "John Doe", "requested assistance", "20 min ago"),
                NotificationData("n4", "John Doe", "requested assistance", "20 min ago"),
                NotificationData("n5", "Jane Smith", "liked your post", "1 hour ago"),
            )
            DefaultNotificationsPreview(sampleMessages,sampleNotifications) }
        composable(Screen.Registration.route) { /* Registration Screen Content */ }
        composable(Screen.Login.route) { /* Login Screen Content */ }

        composable(Screen.Main_account.route) {
            AccountMainScreen(navController = navController)
        }
        composable(Screen.Profile_info.route) {
            val userProfile = remember {
                mutableStateOf(UserProfile("Aymen Bouslama", "aymen.b@example.com", "+1 123-456-7890"))
            }
            ProfileInfoScreen(navController = navController, profile = userProfile.value,
                onSave = { updatedProfile ->
            println("Saving profile: $updatedProfile")
            userProfile.value = updatedProfile
            navController.navigateUp()
        }
            )}
        composable(Screen.Change_password.route) {
            ChangePasswordScreen(navController = navController, onChangePassword = { current, new ->
                println("Changing password: Current=$current, New=$new")
            val success = true
            if (success) navController.navigateUp()
            success
        }) }
        composable(Screen.Push_notifications.route) {
            val pushNotificationsEnabled = rememberSaveable { mutableStateOf(true) }

            PushNotificationsScreen(navController = navController, initialState = pushNotificationsEnabled.value, onToggle = { enabled ->
                println("Notifications toggled: $enabled")
                pushNotificationsEnabled.value = enabled })

        }
        composable(Screen.Faq.route) {
            val faqs = remember { getSampleFaqs() }
            FaqScreen(navController = navController, faqs = faqs)
        }
        composable(Screen.Contact_support.route) {
            val contactEmail = "support@yourapp.com"
            val contactPhone = "+1-800-SUPPORT"
            ContactSupportScreen(navController = navController, email = contactEmail, phone = contactPhone)

        }
        composable(Screen.Report_bug.route) {
            ReportBugScreen(navController = navController, onSubmit = { report ->
                println("Submitting bug report: $report")
                 // Simulate success
                val success = true
                if (success) navController.navigateUp()
                success
            })
        }
        composable(Screen.Logout_delete.route) {
            LogoutDeleteScreen(navController = navController, onLogout = {
                println("Logging out...")
            }, onDeleteAccount = {println("Deleting account...")})

        }

    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}