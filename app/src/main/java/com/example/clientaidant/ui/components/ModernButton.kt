import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

// --- Reusable Modern Button ---
@Composable
fun ModernButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    isDestructive: Boolean = false // Flag for error color scheme
) {
    val containerColor = when {
        !enabled -> Color(0xFF1D1D1F).copy(alpha = 0.12f) // Standard disabled color
        isDestructive -> Color(0xFFFF3B30)
        else -> AppColors.primary // Orange default
    }
    val contentColor = when {
        !enabled -> Color(0xFF1D1D1F).copy(alpha = 0.38f)
        isDestructive -> Color.White
        else -> Color.White // White on orange
    }

    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(52.dp), // Slightly taller button
        enabled = enabled,
        shape = MaterialTheme.shapes.small, // Consistent rounding
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = Color(0xFF1D1D1F).copy(alpha = 0.08f), // Adjusted disabled bg
            disabledContentColor = Color(0xFF1D1D1F).copy(alpha = 0.38f)
        ),
        elevation = ButtonDefaults.buttonElevation( // No shadow for flat look
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
            }
            Text(text, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
        }
    }
}
