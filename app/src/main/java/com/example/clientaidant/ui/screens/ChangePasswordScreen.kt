import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    navController: NavController,
    onChangePassword: (current: String, new: String) -> Boolean
) {
    var currentPassword by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var showError by rememberSaveable { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 10.dp)
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
                    text = "Change password",
                    modifier = Modifier.padding(8.dp),
                    color = AppColors.darkBlue,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = PlusJakartaSans,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            ModernPasswordTextField(
                value = currentPassword,
                onValueChange = { currentPassword = it; showError = null },
                label = "Current Password"
            )
            Spacer(modifier = Modifier.height(16.dp))
            ModernPasswordTextField(
                value = newPassword,
                onValueChange = { newPassword = it; showError = null },
                label = "New Password"
            )
            Spacer(modifier = Modifier.height(16.dp))
            ModernPasswordTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it; showError = null },
                label = "Confirm New Password",
                isError = showError != null && (newPassword != confirmPassword || showError?.contains("match") == true)
            )

            AnimatedVisibility(visible = showError != null) {
                Text(
                    text = showError ?: "",
                    color = Color(0xFFFF3B30),
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 8.dp, start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            ModernButton(
                text = "Update Password",
                onClick = {
                    if (newPassword.length < 6) {
                        showError = "New password must be at least 6 characters."
                    } else if (newPassword != confirmPassword) {
                        showError = "New passwords do not match."
                    } else {
                        val success = onChangePassword(currentPassword, newPassword)
                        if (!success) {
                            showError = "Incorrect current password or failed to update."
                        }
                        // Navigation handled in NavHost if success
                    }
                },
                enabled = currentPassword.isNotEmpty() && newPassword.isNotEmpty() && confirmPassword.isNotEmpty()
            )
        }
    }
}