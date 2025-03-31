import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clientaidant.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaqListItemModern(faq: FaqItem) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        // onClick defined below on the content Column to avoid expanding on Card edges
        modifier = Modifier.fillMaxWidth().animateContentSize(), // Animate size change
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F7) // Use surface variant bg
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // No shadow
    ) {
        Column(
            modifier = Modifier
                .clickable { expanded = !expanded } // Click expands/collapses
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = faq.question,
                    fontWeight = FontWeight.Medium, // Medium weight question
                    fontSize = 15.sp,
                    color = Color(0xFF1D1D1F),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = AppColors.primary // Orange chevron
                )
            }

            // AnimatedVisibility for smooth expand/collapse
            AnimatedVisibility(visible = expanded) {
                Text(
                    text = faq.answer,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(top = 10.dp) // Add padding only when visible
                )
            }
        }
    }
}