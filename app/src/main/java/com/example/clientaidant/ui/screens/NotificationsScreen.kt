
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack // Use autoMirrored for RTL support
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- Data Classes ---

data class MessageData(
    val id: String,
    val senderName: String,
    val preview: String,
    val timestamp: String,
    val unreadCount: Int?, // Nullable for messages without unread count
    val isOnline: Boolean
)

data class NotificationData(
    val id: String,
    val senderName: String,
    val action: String,
    val timestamp: String
)

// --- Main Activity (Example Usage) ---

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                // Sample Data (replace with your actual data source)
                val sampleMessages = remember { getSampleMessages() }
                val sampleNotifications = remember { getSampleNotifications() }

                MessagesNotificationsScreen(
                    messages = sampleMessages,
                    notifications = sampleNotifications,
                    onBackClick = { /* Handle back navigation */ }
                )

        }
    }
}

// --- Composable Screen ---

@OptIn(ExperimentalMaterial3Api::class) // Opt-in for TopAppBar
@Composable
fun MessagesNotificationsScreen(
    messages: List<MessageData>,
    notifications: List<NotificationData>,
    onBackClick: () -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(1) } // 0 for Notifications, 1 for Messages
    val tabs = listOf("Notifications", "Messages")

    // Calculate unread message count for the tab title
    val unreadMessageCount = messages.count { it.unreadCount != null && it.unreadCount > 0 }
    val messageTabTitle = if (unreadMessageCount > 0) "Messages ($unreadMessageCount)" else "Messages"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (selectedTabIndex == 0) "Notifications" else "Messages",
                        fontWeight = FontWeight.Medium // Adjust weight as needed
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface, // Or primaryContainer, surfaceVariant etc.
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary // Color for the selected tab text/indicator
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Notifications") },
                    selectedContentColor = MaterialTheme.colorScheme.primary, // Color for selected text
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant // Color for unselected text
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text(messageTabTitle) },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Content based on selected tab
            when (selectedTabIndex) {
                0 -> NotificationList(notifications = notifications)
                1 -> MessageList(messages = messages)
            }
        }
    }
}

// --- Message List Composables ---

@Composable
fun MessageList(messages: List<MessageData>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp) // Add some padding around the list
    ) {
        items(messages, key = { it.id }) { message ->
            MessageItem(message = message)
            // Optional Divider
            // HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 0.5.dp)
        }
    }
}

@Composable
fun MessageItem(message: MessageData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp), // Adjust padding as needed
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserAvatar(isOnline = message.isOnline)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = message.senderName,
                fontWeight = FontWeight.Medium, // Make name slightly bolder
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = message.preview,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant, // Lighter color for preview
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = message.timestamp,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant // Lighter color for time
            )
            // Show badge only if there are unread messages
            message.unreadCount?.let { count ->
                if (count > 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                    UnreadBadge(count = count)
                }
            }
        }
    }
}

@Composable
fun UnreadBadge(count: Int) {
    Box(
        modifier = Modifier
            .size(20.dp) // Adjust size as needed
            .clip(CircleShape)
            .background(Color(0xFFFFA500)), // Orange color
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = count.toString(),
            color = Color.White,
            fontSize = 11.sp, // Smaller font for badge
            fontWeight = FontWeight.Bold
        )
    }
}

// --- Notification List Composables ---

@Composable
fun NotificationList(notifications: List<NotificationData>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(notifications, key = { it.id }) { notification ->
            NotificationItem(notification = notification)
            // Optional Divider
            // HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 0.5.dp)
        }
    }
}

@Composable
fun NotificationItem(notification: NotificationData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserAvatar(isOnline = false) // Assuming notifications don't show online status
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = notification.senderName,
                fontSize = 15.sp, // Slightly smaller than message name
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = notification.action,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = notification.timestamp,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        // Placeholder for the action button/area
        Box(
            modifier = Modifier
                .size(width = 60.dp, height = 35.dp) // Adjust size as needed
                .clip(RoundedCornerShape(8.dp)) // Rounded corners
                .background(MaterialTheme.colorScheme.surfaceVariant) // Placeholder color
        )
    }
}


// --- Common Composables ---

@Composable
fun UserAvatar(isOnline: Boolean) {
    Box(contentAlignment = Alignment.BottomEnd) {
        // Placeholder Avatar
        Box(
            modifier = Modifier
                .size(48.dp) // Adjust avatar size
                .clip(CircleShape)
                .background(Color.LightGray) // Placeholder color
        )
        // Online Indicator (only if isOnline is true)
        if (isOnline) {
            Box(
                modifier = Modifier
                    .size(12.dp) // Adjust indicator size
                    .offset(x = (2).dp, y = (2).dp) // Slight offset from corner
                    .clip(CircleShape)
                    .background(Color.Green)
                    .border(1.5.dp, MaterialTheme.colorScheme.surface, CircleShape) // Add border matching background
            )
        }
    }
}


// --- Sample Data Functions ---

fun getSampleMessages(): List<MessageData> {
    return listOf(
        MessageData("m1", "Royal Parvej", "Sounds awesome!", "19:37", 1, true),
        MessageData("m2", "Cameron Williamson", "Ok, Just hurry up little bit...😊", "19:37", 2, true),
        MessageData("m3", "Ralph Edwards", "Thanks dude.", "19:37", null, true), // No unread count
        MessageData("m4", "Cody Fisher", "How is going...?", "19:37", null, true),
        MessageData("m5", "Eleanor Pena", "Thanks for the awesome food man...!", "19:37", null, false), // Offline
        MessageData("m6", "Esther Howard", "See you tomorrow!", "19:35", 5, true) // More unread
    )
}

fun getSampleNotifications(): List<NotificationData> {
    return listOf(
        NotificationData("n1", "John Doe", "requested assistance", "20 min ago"),
        NotificationData("n2", "John Doe", "requested assistance", "20 min ago"),
        NotificationData("n3", "John Doe", "requested assistance", "20 min ago"),
        NotificationData("n4", "John Doe", "requested assistance", "20 min ago"),
        NotificationData("n5", "Jane Smith", "liked your post", "1 hour ago"),
    )
}


// --- Preview ---

@Preview(showBackground = true, device = "id:pixel_6") // Example device
@Composable
fun DefaultMessagesPreview() {

        MessagesNotificationsScreen(
            messages = getSampleMessages(),
            notifications = getSampleNotifications(),
            onBackClick = {}
        )

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, device = "id:pixel_6")
@Composable
fun DefaultNotificationsPreview() {
    // To preview notifications selected, we need to manage state within the preview
    // or create a dedicated preview function. Let's manage state here.
    var selectedTabIndex by remember { mutableStateOf(0) } // Start with Notifications selected


        val sampleMessages = remember { getSampleMessages() }
        val sampleNotifications = remember { getSampleNotifications() }
        val unreadMessageCount = sampleMessages.count { it.unreadCount != null && it.unreadCount > 0 }
        val messageTabTitle = if (unreadMessageCount > 0) "Messages ($unreadMessageCount)" else "Messages"

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(if (selectedTabIndex == 0) "Notifications" else "Messages") },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                TabRow(selectedTabIndex = selectedTabIndex) {
                    Tab(
                        selected = selectedTabIndex == 0,
                        onClick = { selectedTabIndex = 0 },
                        text = { Text("Notifications") }
                    )
                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = { selectedTabIndex = 1 },
                        text = { Text(messageTabTitle) }
                    )
                }
                when (selectedTabIndex) {
                    0 -> NotificationList(notifications = sampleNotifications)
                    1 -> MessageList(messages = sampleMessages)
                }
            }
        }

}