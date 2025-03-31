import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clientaidant.R
import com.example.clientaidant.ui.theme.AppColors
import com.example.clientaidant.ui.theme.PlusJakartaSans


// --- Push Notifications Screen (Revamped UI) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PushNotificationsScreen(
    navController: NavController,
    initialState: Boolean,
    onToggle: (Boolean) -> Unit
) {
    var isEnabled by rememberSaveable { mutableStateOf(initialState) }

    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 10.dp) // More vertical padding
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
                    text = "Profile information",
                    modifier = Modifier.padding(8.dp),
                    color = AppColors.darkBlue,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = PlusJakartaSans,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Card( // Use Card for better visual grouping
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F7)), // Use light gray variant bg
                elevation = CardDefaults.cardElevation(0.dp) // No shadow needed if bg is different
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 16.dp)) {
                        Text(
                            "Enable Notifications",
                            style = MaterialTheme.typography.titleMedium // Slightly bolder title
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Receive updates, alerts, and important information.",
                            style = MaterialTheme.typography.bodyMedium, // Use body style
                            lineHeight = 18.sp
                        )
                    }

                    Switch(
                        checked = isEnabled,
                        onCheckedChange = { checked ->
                            isEnabled = checked
                            onToggle(checked)
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = AppColors.primary, // Orange thumb
                            checkedTrackColor = AppColors.primary.copy(alpha = 0.5f),
                            uncheckedThumbColor = Color(0xFFEAEAEA), // Use outline color for thumb
                            uncheckedTrackColor = Color(0xFFEAEAEA).copy(alpha = 0.3f),
                        )
                    )
                }
            }
        }
    }
}