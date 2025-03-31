import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.outlined.ContactSupport
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clientaidant.R
import com.example.clientaidant.ui.theme.AppColors
import com.example.clientaidant.ui.theme.PlusJakartaSans

// --- Contact Support Screen (Revamped UI) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactSupportScreen(
    navController: NavController,
    email: String,
    phone: String
) {
    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp,) .background(Color.White) ,
            horizontalArrangement = Arrangement.Start // Align to the left
        ) {
            Image(
                painter = painterResource(id = R.drawable.d_back),
                contentDescription = null,
            )
            Text(
                text = "Contact Support",
                modifier = Modifier.padding(8.dp),
                color = AppColors.darkBlue,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.SemiBold,
                fontFamily = PlusJakartaSans,
            )
        }
            Spacer(modifier = Modifier.height(20.dp))

            // Use a vibrant blue icon here for visual interest
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ContactSupport,
                contentDescription = null,
                tint = AppColors.darkBlue, // BLUE Icon
                modifier = Modifier.size(56.dp) // Slightly smaller
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Need Assistance?",
                style = MaterialTheme.typography.headlineSmall, // Adjusted style
                color = Color(0xFF1D1D1F),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Our support team is ready to help. Reach out using the options below.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF1D1D1F),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(32.dp))

            ModernContactOption(
                icon = Icons.Outlined.Email,
                label = "Email Support",
                value = email,
                onClick = { /* TODO: Implement email intent */ }
            )
            Spacer(modifier = Modifier.height(16.dp))
            ModernContactOption(
                icon = Icons.Outlined.Phone,
                label = "Call Support",
                value = phone,
                onClick = { /* TODO: Implement phone dial intent */ }
            )
        }
    }
}

@Composable
fun ModernContactOption(icon: ImageVector, label: String, value: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F7)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AppColors.primary, // Orange icons for contrast on gray bg
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(label, style = MaterialTheme.typography.titleMedium) // Bolder label
                Spacer(modifier = Modifier.height(2.dp))
                Text(value, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}