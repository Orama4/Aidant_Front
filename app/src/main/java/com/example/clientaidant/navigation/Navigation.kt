import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.clientaidant.data.api.RetrofitClient
import com.example.clientaidant.data.viewmodels.AuthViewModel
import com.example.clientaidant.data.viewmodels.AuthViewModelFactory
import com.example.clientaidant.data.viewmodels.HelperViewModel
import com.example.clientaidant.data.viewmodels.SocketViewModel
import com.example.clientaidant.repositories.AuthRepository
import com.example.clientaidant.repositories.HelperRepository
import com.example.clientaidant.ui.screens.ForgetPasswordScreen
import com.example.clientaidant.ui.screens.HelperScreen
import com.example.clientaidant.ui.screens.LoginScreen
import com.example.clientaidant.ui.screens.OnboardingScreen
import com.example.clientaidant.ui.screens.RegistrationScreen
import com.example.clientaidant.ui.screens.TrackUser
import com.example.clientaidant.ui.screens.TrackUserScreen
import com.example.clientaidant.ui.screens.VerificationScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Tracking : Screen("tracking")
    object Notifications : Screen("notifications")

    object Login : Screen("login")
    object Registration : Screen("registration")
    object ForgotPassword : Screen("ForgotPassword")
    object Verification : Screen("verification")
    object OnBoarding : Screen("onboarding")

    object Main_account : Screen("main_account")
    object Profile_info : Screen("profile_info")
    object Change_password : Screen("change_password")
    object Push_notifications : Screen("push_notifications")
    object Faq : Screen("faq")
    object Contact_support : Screen("contact_support")
    object Report_bug : Screen("report_bug")
    object Logout_delete : Screen("logout_delete")
    object TrackingUser : Screen("track") {
        const val routeWithArgs = "track/{userId}"
        fun createRoute(userId: String) = "track/$userId"
    }


    object Test_webSocket :Screen("test_websocket")


}

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun NavigationController(navController: NavHostController = rememberNavController(),authRepository: AuthRepository) {

    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(authRepository,LocalContext.current)
    )

    val socketViewModel = SocketViewModel()
    val apiService = RetrofitClient.helperApiService
    val helperViewModel = HelperViewModel(HelperRepository(apiService),context)
    val isLoggedIn = remember {
        derivedStateOf {
            authViewModel.getToken() != null
        }
    }
    Log.d("LOGGED__IN",isLoggedIn.value.toString())
    val startDestination = if (isLoggedIn.value) Screen.Home.route else Screen.Login.route
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Screen.Home.route) {
            val user = authViewModel.getUserInfo()
            val id = user?.id
            if (id != null) {
                helperViewModel.fetchActiveEndUsers(helperUserId = id)
            } else {
                helperViewModel.fetchActiveEndUsers(helperUserId = 12)
            }
            val activeUser = helperViewModel.activeEndUsers.collectAsState();
            HomeScreen(
            getToken = {
                authViewModel.getUserInfo()
            },
            helperViewModel =helperViewModel ,
            activeUsers = activeUser,
            userName = "John",
            notificationCount = 2,
           /* assistedUsers = listOf(
                AssistedUser(1, "John Doe", "Main Hall", "29 JAN, 12:30", UserStatus.ON_THE_MOVE),
                AssistedUser(2, "Jean Dupont", "Main Hall", "29 JAN, 12:30", UserStatus.WAITING),
                AssistedUser(3, "Alice Martin", "Cafeteria", "29 JAN, 11:15", UserStatus.WAITING),
                AssistedUser(4, "Bob Garcia", "Entrance B", "29 JAN, 12:35", UserStatus.IN_ASSISTANCE),
            ),*/

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
            },
                navController = navController
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
        composable(Screen.OnBoarding.route) { OnboardingScreen(navController) }
        composable(Screen.Registration.route) { RegistrationScreen(navController) }
        composable(Screen.Verification.route) { VerificationScreen() }
        composable(Screen.Login.route) { LoginScreen(context,authViewModel,navController) }
        composable(Screen.ForgotPassword.route) { ForgetPasswordScreen() }


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
        composable (Screen.Test_webSocket.route){
            val socketViewMode = SocketViewModel()
            HelperScreen(socketViewMode)
        }
      /*  composable(
            route = "${Screen.TrackingUser.route}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userID = backStackEntry.arguments?.getString("userId") ?: ""
            val  socketViewModel = SocketViewModel()
            val User = authViewModel.getUserInfo()
            val helperID = User?.id
            Log.d("HELPER ID IS ",helperID.toString())
            if (helperID != null) {
                socketViewModel.connectToServer(helperID)
            }
            val connectionState by socketViewModel.connectionState.collectAsState()

           // je veux recupuer le user qui a userId == userID
            val user = connectionState.activeUsers.find { it.userId.toString() == userID }

            if (user != null) {
                Toast.makeText(context,user.toString(),Toast.LENGTH_LONG).show()
                socketViewModel.selectUser(user)
            }else{
                Toast.makeText(context,"THE USER IS NULL ",Toast.LENGTH_LONG).show()

            }




            socketViewModel.loadGeoJSONFromAssets(context)
           TrackUserScreen(socketViewModel)
        }*/
        composable(
            route = "${Screen.TrackingUser.route}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userID = backStackEntry.arguments?.getString("userId") ?: ""
            val User = authViewModel.getUserInfo()
            val helperID = User?.id

            // Connecter seulement si pas déjà connecté
            LaunchedEffect(helperID) {
                if (helperID != null && !socketViewModel.isConnected()) {
                    socketViewModel.connectToServer(helperID)
                }
            }

            val connectionState by socketViewModel.connectionState.collectAsState()

            // Utiliser LaunchedEffect pour éviter les recompositions infinies
            LaunchedEffect(connectionState.activeUsers, userID) {
                val user = connectionState.activeUsers.find { it.userId.toString() == userID }
                if (user != null) {
                  //  Toast.makeText(context,user.toString(),Toast.LENGTH_LONG).show()
                    socketViewModel.selectUser(user)
                }
            }

            LaunchedEffect(Unit) {
                socketViewModel.loadGeoJSONFromAssets(context)
            }

            TrackUserScreen(navController,socketViewModel)
        }

    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}