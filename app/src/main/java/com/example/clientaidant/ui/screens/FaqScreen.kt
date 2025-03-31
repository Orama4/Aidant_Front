import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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


// --- FAQ Screen (Revamped UI) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaqScreen(
    navController: NavController,
    faqs: List<FaqItem>
) {
    Scaffold(
        containerColor = Color(0xFFFDFEFF)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
        ) {
           item{ Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp,) .background(Color.White) ,
                horizontalArrangement = Arrangement.Start // Align to the left
            ) {
                Image(
                    painter = painterResource(id = R.drawable.d_back),
                    contentDescription = null,
                )
                Text(
                    text = "Frequent questions",
                    modifier = Modifier.padding(8.dp),
                    color = AppColors.darkBlue,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = PlusJakartaSans,
                )
            }}
           item{ Spacer(modifier = Modifier.height(20.dp))}
            item {
                Text(
                    "Frequently Asked Questions",
                    style = MaterialTheme.typography.titleLarge, // Bigger Title
                    color = AppColors.darkBlue, // BLUE title
                    modifier = Modifier.padding(bottom = 16.dp, start = 4.dp)
                )
            }
            items(faqs, key = { it.id }) { faq ->
                FaqListItemModern(faq = faq)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
// --- Sample Data Function (Unchanged) ---
fun getSampleFaqs(): List<FaqItem> {
    return listOf(
        FaqItem("faq1", "How do I reset my password?", "You can reset your password from the 'Change Password' section in the Account Settings. If you've forgotten your current password, use the 'Forgot Password' link on the login screen."),
        FaqItem("faq2", "How is my data protected?", "We use industry-standard encryption and security practices to protect your data. Please refer to our Privacy Policy for more details."),
        FaqItem("faq3", "Can I use the app offline?", "Basic functionality might be available offline, but most features require an active internet connection to sync data and provide real-time updates."),
        FaqItem("faq4", "How do I update my profile picture?", "Profile picture updates are currently handled through your linked social media account or will be available in a future update.")
    )
}
