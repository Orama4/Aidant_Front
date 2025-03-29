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
            userName = "John", // Example user name
            notificationCount = 2, // Example notification count
            assistedUsers = listOf(
                AssistedUser(1, "John Doe", "Main Hall", "29 JAN, 12:30", UserStatus.ON_THE_MOVE),
                AssistedUser(2, "Jean Dupont", "Main Hall", "29 JAN, 12:30", UserStatus.WAITING),
                AssistedUser(3, "Alice Martin", "Cafeteria", "29 JAN, 11:15", UserStatus.WAITING),
                AssistedUser(4, "Bob Garcia", "Entrance B", "29 JAN, 12:35", UserStatus.IN_ASSISTANCE),
            ),
            onUserSearch = { /* Handle search */ },
            onTrackLocationClick = {
            },
            onRemoteAssistanceClick = { userId ->
                // Handle remote assistance
            },
            onNotificationClick = {
                // Navigate to Notifications screen
                navController.navigate(Screen.Notifications.route)
            },
            onBackClick = {
                // Handle back click
                navController.popBackStack()
            }
        ) }
        composable(Screen.Tracking.route) { TrackUser() }
        composable(Screen.Notifications.route) { /* Notifications Screen Content */ }
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