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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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

// --- Report a Bug Screen (Revamped UI) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportBugScreen(
    navController: NavController,
    onSubmit: (String) -> Boolean
) {
    var bugReportText by rememberSaveable { mutableStateOf("") }
    var showSuccessMessage by rememberSaveable { mutableStateOf(false) }
    var showErrorMessage by rememberSaveable { mutableStateOf(false) }

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
            Text(
                "Describe the issue",
                style = MaterialTheme.typography.titleMedium, // Title for the text area
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField( // Keep OutlinedTextField for multi-line
                value = bugReportText,
                onValueChange = {
                    bugReportText = it
                    showSuccessMessage = false
                    showErrorMessage = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 180.dp), // Make it taller
                placeholder = { Text("Please include steps to reproduce the problem if possible...") },
                shape = MaterialTheme.shapes.medium, // More rounding
                colors = OutlinedTextFieldDefaults.colors( // Consistent styling
                    focusedTextColor = Color(0xFF1D1D1F),
                    unfocusedTextColor = Color(0xFF1D1D1F),
                    focusedBorderColor = AppColors.primary,
                    unfocusedBorderColor = Color(0xFFEAEAEA),
                    cursorColor = AppColors.primary,
                    focusedContainerColor = Color.White, // Ensure bg is white when focused
                    unfocusedContainerColor = Color.White,
                    focusedPlaceholderColor = Color(0xFFF5F5F7).copy(alpha = 0.7f),
                    unfocusedPlaceholderColor = Color(0xFFF5F5F7).copy(alpha = 0.7f)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Feedback Messages
            AnimatedVisibility(visible = showSuccessMessage) {
                FeedbackText("Bug report submitted successfully!", isError = false)
            }
            AnimatedVisibility(visible = showErrorMessage) {
                FeedbackText("Failed to submit bug report. Please try again.", isError = true)
            }

            Spacer(modifier = Modifier.height(if (showSuccessMessage || showErrorMessage) 8.dp else 24.dp)) // Adjust space before button

            ModernButton(
                text = "Submit Report",
                onClick = {
                    if (bugReportText.isNotBlank()) {
                        val success = onSubmit(bugReportText)
                        showSuccessMessage = success
                        showErrorMessage = !success
                        // Navigation handled in NavHost
                    }
                },
                enabled = bugReportText.isNotBlank()
            )
        }
    }
}
@Composable
fun FeedbackText(text: String, isError: Boolean) {
    Text(
        text = text,
        color = if (isError) Color(0xFFFF3B30) else Color(0xFF34C759),
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}