import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    object Account : Screen("account")
    object Registration : Screen("registration")
    object Login : Screen("login")
}

@Composable
fun NavigationController(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Tracking.route,


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
                navController.navigate(Screen.Notifications.route)
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
        composable(Screen.Account.route) { /* Account Screen Content */ }
        composable(Screen.Registration.route) { /* Registration Screen Content */ }
        composable(Screen.Login.route) { /* Login Screen Content */ }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}