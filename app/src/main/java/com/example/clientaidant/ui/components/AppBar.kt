import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clientaidant.ui.theme.AppColors

// --- Modern Top App Bars ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernTopAppBar(title: String) {
    Column {
        CenterAlignedTopAppBar( // Use CenterAligned for main screen title
            title = { Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp) }, // Bold Title
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.White, // White background
                titleContentColor = Color(0xFF1D1D1F)// Dark Text
            )
        )
        HorizontalDivider(color = Color(0xFFEAEAEA), thickness = 1.dp) // Thin divider below app bar
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernSubPageAppBar(title: String, navController: NavController) {
    Column {
        TopAppBar( // Standard TopAppBar for sub-pages
            title = { Text(title, fontWeight = FontWeight.SemiBold, fontSize = 17.sp) }, // SemiBold Title
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = AppColors.primary // Orange back arrow
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color(0xFF1D1D1F),
                navigationIconContentColor = AppColors.primary
            )
        )
        HorizontalDivider(color = Color(0xFFEAEAEA), thickness = 1.dp)
    }
}
