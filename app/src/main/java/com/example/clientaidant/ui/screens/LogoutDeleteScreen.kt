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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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


// --- Logout / Delete Account Screen (Revamped UI) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogoutDeleteScreen(
    navController: NavController,
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit
) {
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

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
                    text = "Report a bug",
                    modifier = Modifier.padding(8.dp),
                    color = AppColors.darkBlue,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = PlusJakartaSans,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            ModernButton(
                text = "Logout",
                onClick = onLogout,
                icon = Icons.AutoMirrored.Filled.Logout
            )

            Spacer(modifier = Modifier.height(32.dp))
            HorizontalDivider(color = Color(0xFFEAEAEA), thickness = 1.dp)
            Spacer(modifier = Modifier.height(32.dp))

            // Delete Account Section
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.WarningAmber,
                    contentDescription = "Warning",
                    tint = Color(0xFFFF3B30),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "Delete Account",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFFFF3B30),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Permanently delete your account and all associated data. This action cannot be undone.",
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            ModernButton(
                text = "Delete My Account",
                onClick = { showDeleteConfirmDialog = true },
                icon = Icons.Filled.DeleteForever,
                isDestructive = true // Use destructive styling
            )
        }
    }

    // Confirmation Dialog (Modern Styling)
    if (showDeleteConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmDialog = false },
            shape = MaterialTheme.shapes.large, // More rounded dialog
            containerColor = Color.White,
            icon = { Icon(Icons.Filled.WarningAmber, contentDescription = null, tint = Color(0xFFFF3B30)) },
            title = { Text("Confirm Deletion", fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to permanently delete your account?", style = MaterialTheme.typography.bodyMedium) },
            confirmButton = {
                Button( // Destructive confirm button
                    onClick = {
                        showDeleteConfirmDialog = false
                        onDeleteAccount()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF3B30),
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.small
                ) { Text("Delete", color = Color.White) }
            },
            dismissButton = {
                TextButton( // Standard dismiss button
                    onClick = { showDeleteConfirmDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = AppColors.primary) // Orange text
                ) { Text("Cancel") }
            }
        )
    }
}