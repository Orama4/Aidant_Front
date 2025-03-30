import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val avatarUrl: String? = null
)

val OrangeAccent = Color(0xFFF57C00)
val LightGrayBackground = Color(0xFFF5F5F5)
val IconColor = Color.Gray
val DividerColor = Color.LightGray.copy(alpha = 0.5f)
val StatusGreen = Color(0xFF2E7D32)
val StatusYellow = Color(0xFFF9A825)
val StatusBlue = Color(0xFF1976D2)

@Composable
fun HomeScreen(
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
    onRemoteAssistanceClick: (userId: Int) -> Unit
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
    onTrackLocationClick: (userId: Int) -> Unit,
    onRemoteAssistanceClick: (userId: Int) -> Unit
) {
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
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
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
                onClick = { onTrackLocationClick(user.id) },
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
