
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun AccountScreen(navController: NavHostController) {
    Box(
            modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE8F5E9)),
            contentAlignment = Alignment.Center
    ) {
        Text("Account Screen", fontSize = 24.sp)
    }
}