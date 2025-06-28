import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clientaidant.data.api.ActiveEndUser
import com.example.clientaidant.data.api.User
import com.example.clientaidant.data.api.toAssistedUser
import com.example.clientaidant.data.viewmodels.HelperViewModel
import com.example.clientaidant.ui.theme.ClientAidantTheme

enum class UserStatus(val displayName: String) {
    ON_THE_MOVE("On the move"),
    WAITING("Waiting"),
    IN_ASSISTANCE("In assistance")
}

data class AssistedUser(
    val id: Int,
    val name: String,
    val location: String,
    val timestamp: String,
    val status: UserStatus,
    val status1 :String ,
    val avatarUrl: String? = null
)

val OrangeAccent = Color(0xFFF57C00)
val LightGrayBackground = Color(0xFFF5F5F5)
val IconColor = Color.Gray
val DividerColor = Color.LightGray.copy(alpha = 0.5f)
val StatusGreen = Color(0xFF2E7D32)
val StatusYellow = Color(0xFFF9A825)
val StatusBlue = Color(0xFF1976D2)
/*
@Composable
fun HomeScreen(
    helperViewModel: HelperViewModel ,
    userName: String,
    notificationCount: Int,
    assistedUsers: List<AssistedUser>,
    onUserSearch: (String) -> Unit,
    onTrackLocationClick: (userId: Int) -> Unit,
    onRemoteAssistanceClick: (userId: Int) -> Unit,
    onNotificationClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    val usersByStatus = assistedUsers.groupBy { it.status }
    val onTheMoveUsers = usersByStatus[UserStatus.ON_THE_MOVE] ?: emptyList()
    val waitingUsers = usersByStatus[UserStatus.WAITING] ?: emptyList()
    val inAssistanceUsers = usersByStatus[UserStatus.IN_ASSISTANCE] ?: emptyList()


    LaunchedEffect(Unit) {
        helperViewModel.fetchActiveEndUsers(helperUserId = 5)
    }

    val loading by helperViewModel.loading.collectAsState()
    val users by helperViewModel.activeEndUsers.collectAsState()
    val error by helperViewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .background(Color.White)
    ) {
        TopAppBarSection(
            userName = userName,
            notificationCount = notificationCount,
            onBackClick = onBackClick,
            onNotificationClick = onNotificationClick,

        )
        Text(
            text = buildAnnotatedString {
                append("Hey $userName, ")
                withStyle(style = androidx.compose.ui.text.SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Good Afternoon!")
                }
            },
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        )
        SearchBar(
            searchText = searchText,
            onSearchTextChanged = {
                searchText = it
                onUserSearch(it)
            }
        )
        Text(
            text = "Assisted Users",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 8.dp,horizontal = 16.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .weight(1f)
            ,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {




            if (onTheMoveUsers.isNotEmpty()) {
                item {
                    UserSection(
                        title = UserStatus.ON_THE_MOVE.displayName,
                        titleColor = StatusGreen,
                        users = onTheMoveUsers,
                        onTrackLocationClick = onTrackLocationClick,
                        onRemoteAssistanceClick = onRemoteAssistanceClick
                    )
                }
            }

            if (waitingUsers.isNotEmpty()) {
                item {
                    UserSection(
                        title = UserStatus.WAITING.displayName,
                        titleColor = StatusYellow,
                        users = waitingUsers,
                        onTrackLocationClick = onTrackLocationClick,
                        onRemoteAssistanceClick = onRemoteAssistanceClick
                    )
                }
            }

            if (inAssistanceUsers.isNotEmpty()) {
                item {
                    UserSection(
                        title = UserStatus.IN_ASSISTANCE.displayName,
                        titleColor = StatusBlue,
                        users = inAssistanceUsers,
                        onTrackLocationClick = onTrackLocationClick,
                        onRemoteAssistanceClick = onRemoteAssistanceClick
                    )
                }
            }

            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}
*/
@Composable
fun HomeScreen(
    getToken: suspend () -> User?,
    helperViewModel: HelperViewModel,
    activeUsers : State<List<ActiveEndUser>>,
    userName: String,
    notificationCount: Int,
    onUserSearch: (String) -> Unit,
    onTrackLocationClick: (userId: Int) -> Unit,
    onRemoteAssistanceClick: (userId: Int) -> Unit,
    onNotificationClick: () -> Unit,
    onBackClick: () -> Unit,
    navController: NavController
) {
    var searchText by remember { mutableStateOf("") }

    val loading by helperViewModel.loading.collectAsState()
    val error by helperViewModel.error.collectAsState()

    // Hardcoder le mapping pour éliminer le problème de status
    val assistedUsers by remember(activeUsers.value, searchText) {
        derivedStateOf {
            try {
                Log.d("HomeScreen", "Processing ${activeUsers.value.size} active users")

                val mapped = activeUsers.value.mapIndexed { index, activeUser ->
                    Log.d("HomeScreen", "Mapping user $index: $activeUser")

                    // HARDCODED STATUS - Alternance pour tester
                    val hardcodedStatus = when (index % 3) {
                        0 -> UserStatus.ON_THE_MOVE
                        1 -> UserStatus.WAITING
                        else -> UserStatus.IN_ASSISTANCE
                    }

                    val assistedUser = AssistedUser(
                        id = activeUser.userId,
                        name = activeUser.username,
                        status = hardcodedStatus, // STATUS HARDCODÉ
                        location = activeUser.addresse?: "not defined" ,
                        avatarUrl = null,
                        timestamp = activeUser.lastPosition?.timestamp ?: "Not defined",
                        status1 = activeUser.status
                    )

                    Log.d("HomeScreen", "Mapped to: $assistedUser")
                    assistedUser
                }

                val filtered = mapped.filter { it.name.contains(searchText, ignoreCase = true) }
                Log.d("HomeScreen", "Filtered users: $filtered")
                filtered
            } catch (e: Exception) {
                Log.e("HomeScreen", "Error mapping users", e)
                emptyList()
            }
        }
    }

    val usersByStatus by remember(assistedUsers) {
        derivedStateOf {
            val grouped = assistedUsers.groupBy { it.status }
            Log.d("HomeScreen", "Grouped by status: $grouped")
            grouped
        }
    }

    val onTheMoveUsers by remember(usersByStatus) {
        derivedStateOf {
            usersByStatus[UserStatus.ON_THE_MOVE] ?: emptyList()
        }
    }




    LaunchedEffect(Unit) {
        val user = getToken()
        val id = user?.id
        if (id != null) {
            helperViewModel.fetchActiveEndUsers(helperUserId = id)
        } else {
            helperViewModel.fetchActiveEndUsers(helperUserId = 12)
        }
    }

    // Debugging - vérifiez ces logs
    LaunchedEffect(activeUsers.value) {
        Log.d("HomeScreen", "ActiveUsers count: ${activeUsers.value.size}")
        Log.d("HomeScreen", "ActiveUsers: ${activeUsers.value}")
    }

    LaunchedEffect(assistedUsers) {
        Log.d("HomeScreen", "AssistedUsers count: ${assistedUsers.size}")
        Log.d("HomeScreen", "AssistedUsers: $assistedUsers")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .background(Color.White)
    ) {
        TopAppBarSection(
            userName = userName,
            notificationCount = notificationCount,
            onBackClick = onBackClick,
            onNotificationClick = onNotificationClick,
        )

        Text(
            text = buildAnnotatedString {
                append("Hey $userName, ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Good Afternoon!")
                }
            },
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        )

        SearchBar(
            searchText = searchText,
            onSearchTextChanged = {
                searchText = it
                onUserSearch(it)
            }
        )

        if (loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Text(
                text = "Error: $error",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            Text(
                text = "Assisted Users (${assistedUsers.size})",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .fillMaxHeight()
                    .weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Section de debug pour voir TOUS les utilisateurs
            /*    if (assistedUsers.isNotEmpty()) {
                    item {
                        Text(
                            text = "DEBUG - TOUS LES UTILISATEURS:",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Red,
                            modifier = Modifier.padding(8.dp)
                        )
                        assistedUsers.forEach { user ->
                            Text(
                                text = "• ${user.name} - Status: ${user.status}",
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                                color = Color.Blue
                            )
                        }
                    }
                }
                 */
                // Message si aucun utilisateur
                if (assistedUsers.isEmpty()) {
                    item {
                        Text(
                            text = "Aucun utilisateur trouvé",
                            modifier = Modifier.padding(16.dp),
                            color = Color.Gray
                        )
                    }
                }else{
                    item {
                        UserSection(
                           // title = "${UserStatus.ON_THE_MOVE.displayName} (${onTheMoveUsers.size})",
                            title = "",
                            titleColor = StatusGreen,
                            users = assistedUsers,
                            onTrackLocationClick = onTrackLocationClick,
                            onRemoteAssistanceClick = onRemoteAssistanceClick,
                            navController = navController
                        )
                    }
                }

                /*      if (onTheMoveUsers.isNotEmpty()) {
                       item {
                           UserSection(
                               title = "${UserStatus.ON_THE_MOVE.displayName} (${onTheMoveUsers.size})",
                               titleColor = StatusGreen,
                               users = onTheMoveUsers,
                               onTrackLocationClick = onTrackLocationClick,
                               onRemoteAssistanceClick = onRemoteAssistanceClick
                           )
                       }
                   }

                   if (waitingUsers.isNotEmpty()) {
                       item {
                           UserSection(
                               title = "${UserStatus.WAITING.displayName} (${waitingUsers.size})",
                               titleColor = StatusYellow,
                               users = waitingUsers,
                               onTrackLocationClick = onTrackLocationClick,
                               onRemoteAssistanceClick = onRemoteAssistanceClick
                           )
                       }
                   }

                if (inAssistanceUsers.isNotEmpty()) {
                       item {
                           UserSection(
                               title = "${UserStatus.IN_ASSISTANCE.displayName} (${inAssistanceUsers.size})",
                               titleColor = StatusBlue,
                               users = inAssistanceUsers,
                               onTrackLocationClick = onTrackLocationClick,
                               onRemoteAssistanceClick = onRemoteAssistanceClick
                           )
                       }
                   } */

                item { Spacer(Modifier.height(16.dp)) }
            }
        }
    }
}


@Composable
fun TopAppBarSection(
    userName: String,
    notificationCount: Int,
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .size(40.dp)
                .background(LightGrayBackground, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = IconColor
            )
        }



        BadgedBox(
            badge = {
                if (notificationCount > 0) {
                    Badge(
                        containerColor = OrangeAccent,
                        contentColor = Color.White
                    ) { Text("$notificationCount") }
                }
            }
        ) {
            IconButton(
                onClick = onNotificationClick,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFF181C2E), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 20.dp)
            .background(Color(0xFFF6F6F6), RoundedCornerShape(10.dp)),
        placeholder = { Text("Search users", color = Color.Gray) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray
            )
        },
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = OrangeAccent,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent
        ),
        singleLine = true
    )
}

@Composable
fun UserSection(
    title: String,
    titleColor: Color,
    users: List<AssistedUser>,
    onTrackLocationClick: (userId: Int) -> Unit,
    onRemoteAssistanceClick: (userId: Int) -> Unit,
    navController: NavController
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = titleColor,
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        users.forEach { user ->
            UserCard(
                user = user,
                navController = navController,
                onTrackLocationClick = onTrackLocationClick,
                onRemoteAssistanceClick = onRemoteAssistanceClick
            )
            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = DividerColor, thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun UserCard(
    user: AssistedUser,
    navController: NavController,
    onTrackLocationClick: (userId: Int) -> Unit,
    onRemoteAssistanceClick: (userId: Int) -> Unit
) {
    val context =  LocalContext.current

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray)
            ) {
                // Placeholder for avatar
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
               Row (

               ){
                   Text(
                   text = user.name,
                   style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
               );
                   Text(text = " | ",
                   style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
               )

                       Icon(
                           imageVector = when {
                               user.status1 == "online" -> Icons.Default.Circle
                               user.status1 == "offline" -> Icons.Default.Circle
                               else -> Icons.Default.RadioButtonUnchecked
                           },
                           contentDescription = "Status",
                           modifier = Modifier.size(12.dp),
                           tint = when {
                               user.status1 == "online" -> Color.Green
                               user.status1 == "offline" -> Color.Red
                               else -> Color.Gray
                           }
                       )

                       Spacer(modifier = Modifier.width(4.dp))

                       Text(
                           text = when {
                               user.status1 == "online" -> "En ligne"
                               user.status1 == "offline" -> "Hors ligne"
                               else -> user.status1.capitalize()
                           },
                           style = MaterialTheme.typography.bodySmall,
                           color =  when {
                               user.status1 == "online" -> Color.Green
                               user.status1 == "offline" -> Color.Red
                               else -> Color.Gray
                           }
                       )
                   }



                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "Location",
                        tint = IconColor,
                        modifier = Modifier.size(16.dp)

                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = user.location,
                        style = MaterialTheme.typography.bodyMedium,
                        color = IconColor

                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("|", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = user.timestamp,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { onTrackLocationClick(user.id)

                    if(user.status1 =="offline") {
                        Toast.makeText(context,"L'utilisateur ${user.name} est hors Ligne",Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(context,user.id.toString(),Toast.LENGTH_LONG).show()
                        navController.navigate(Screen.TrackingUser.createRoute(user.id.toString()))
                    }

                          },
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = OrangeAccent, // Couleur du texte et de l'icône
                    containerColor = Color.Transparent // Pour un fond transparent
                ),
                border = BorderStroke(1.dp, OrangeAccent) // Bordeure orange
            ) {
                Text("Track location", fontSize = 12.sp)
            }

            Button(
                onClick = { onRemoteAssistanceClick(user.id) },
                modifier = Modifier.weight(1f).height(40.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OrangeAccent)
            ) {
                Text("Remote assistance", fontSize = 12.sp, color = Color.White)
            }
        }
    }
}
/*
@Composable
fun DefaultPreviewOfHomeScreen() {
    val sampleUsers = listOf(
        AssistedUser(1, "John Doe", "Main Hall", "29 JAN, 12:30", UserStatus.ON_THE_MOVE),
        AssistedUser(2, "Jean Dupont", "Main Hall", "29 JAN, 12:30", UserStatus.WAITING),
        AssistedUser(3, "Alice Martin", "Cafeteria", "29 JAN, 11:15", UserStatus.WAITING),
        AssistedUser(4, "Bob Garcia", "Entrance B", "29 JAN, 12:35", UserStatus.IN_ASSISTANCE),
    )

    ClientAidantTheme {
        HomeScreen(
            userName = "John",
            notificationCount = 2,
            assistedUsers = sampleUsers,
            onUserSearch = {},
            onTrackLocationClick = {},
            onRemoteAssistanceClick = {},
            onNotificationClick = {},
            onBackClick = {}
        )
    }
}
*/