import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clientaidant.ui.theme.AppColors

// --- Reusable Modern Settings Item & Card ---
@Composable
fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium, // Use medium rounded corners
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), // Remove shadow for flatter look
        border = BorderStroke(1.dp, Color(0xFFEAEAEA)) // Use subtle border
    ) {
        Column {
            content()
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit,
    iconTint: Color =  AppColors.primary
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp), // Consistent padding
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = iconTint,
            modifier = Modifier.size(24.dp) // Standard icon size
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp, // Slightly adjusted size
                fontWeight = FontWeight.Normal, // Normal weight for cleaner look
                color = Color(0xFF1D1D1F)// Primary text color
            )
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 13.sp, // Smaller subtitle
                    color = Color(0xFFF5F5F7) // Secondary text color
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.ArrowForwardIos, // Chevron icon
            contentDescription = null, // Decorative
            tint = Color(0xFFD1D1D6), // Subtle gray color for chevron
            modifier = Modifier.size(16.dp) // Smaller chevron
        )
    }
}

@Composable
fun SettingsDivider() {
    HorizontalDivider(
        color = Color(0xFFEAEAEA), // Use defined outline color
        thickness = 0.5.dp, // Make it very thin
        modifier = Modifier.padding(start = 56.dp) // Indent divider (icon size + spacer)
    )
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title.uppercase(), // Uppercase for distinction
        style = MaterialTheme.typography.labelMedium, // Use a label style
        color = AppColors.darkBlue, // Use BLUE accent for headers
        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp, top = 16.dp) // Adjust padding
    )
}