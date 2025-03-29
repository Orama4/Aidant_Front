package com.example.myapplication // Replace with your package name

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.Notes // Alternative for Report Bug
import androidx.compose.material.icons.automirrored.filled.Shortcut
import androidx.compose.material.icons.filled.* // Import base filled icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// --- Refined Color Scheme (Light & Modern) ---
val BackgroundColor = Color(0xFFFDFEFF) // Slightly off-white
val SurfaceColor = Color.White
val PrimaryAccentOrange = Color(0xFFFF7B00)
val SecondaryAccentBlue = Color(0xFF007AFF) // Vibrant Blue
val PrimaryText = Color(0xFF1D1D1F)
val SecondaryText = Color(0xFF8A8A8E)
val BorderColor = Color(0xFFEAEAEA)
val ErrorColor = Color(0xFFFF3B30)
val SuccessColor = Color(0xFF34C759) // Standard success green

// --- Data Classes (Example data structures - unchanged) ---
data class UserProfile(
    val name: String,
    val email: String,
    val phone: String
)

data class FaqItem(
    val id: String,
    val question: String,
    val answer: String
)

// --- Navigation Routes (unchanged) ---
object AccountDestinations {
    const val MAIN_ACCOUNT = "main_account"
    const val PROFILE_INFO = "profile_info"
    const val CHANGE_PASSWORD = "change_password"
    const val PUSH_NOTIFICATIONS = "push_notifications"
    const val FAQ = "faq"
    const val CONTACT_SUPPORT = "contact_support"
    const val REPORT_BUG = "report_bug"
    const val LOGOUT_DELETE = "logout_delete"
}

// --- Main Activity (unchanged logic, uses new theme) ---


// --- *** NEW Custom Theme (Light & Modern) *** ---
@Composable
fun MyModernAppTheme(content: @Composable () -> Unit) {
    val customColorScheme = lightColorScheme( // Using lightColorScheme as base
        primary = PrimaryAccentOrange,
        onPrimary = Color.White,
        primaryContainer = PrimaryAccentOrange.copy(alpha = 0.1f), // Light orange background
        onPrimaryContainer = PrimaryAccentOrange,
        secondary = SecondaryAccentBlue,
        onSecondary = Color.White,
        secondaryContainer = SecondaryAccentBlue.copy(alpha = 0.1f), // Light blue background
        onSecondaryContainer = SecondaryAccentBlue,
        tertiary = SecondaryAccentBlue, // Can use tertiary if needed
        onTertiary = Color.White,
        background = BackgroundColor,
        onBackground = PrimaryText,
        surface = SurfaceColor,
        onSurface = PrimaryText,
        surfaceVariant = Color(0xFFF5F5F7), // Slightly different light gray for variants
        onSurfaceVariant = SecondaryText,
        error = ErrorColor,
        onError = Color.White,
        errorContainer = ErrorColor.copy(alpha = 0.1f),
        onErrorContainer = ErrorColor,
        outline = BorderColor, // Subtle borders
        outlineVariant = Color(0xFFD1D1D6) // Slightly darker border if needed
    )

    MaterialTheme(
        colorScheme = customColorScheme,
        typography = MaterialTheme.typography.copy( // Example: Customize typography slightly
            bodyLarge = MaterialTheme.typography.bodyLarge.copy(color = PrimaryText),
            titleMedium = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold, color = PrimaryText),
            // Add other typography customizations here
        ),
        shapes = MaterialTheme.shapes.copy( // Slightly more rounded shapes
            extraSmall = RoundedCornerShape(8.dp),
            small = RoundedCornerShape(12.dp), // Used for buttons, text fields
            medium = RoundedCornerShape(16.dp), // Used for cards
            large = RoundedCornerShape(20.dp)
        ),
        content = content
    )
}

// --- Navigation Host (Unchanged logic, uses new theme context) ---
@Composable
fun AccountScreenNavHost(
    navController: NavHostController = rememberNavController(),
    userProfile: UserProfile,
    pushNotificationsEnabled: Boolean,
    faqs: List<FaqItem>,
    contactEmail: String,
    contactPhone: String,
    onSaveProfile: (UserProfile) -> Unit,
    onChangePassword: (String, String) -> Boolean,
    onNotificationToggle: (Boolean) -> Unit,
    onBugSubmit: (String) -> Boolean,
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = AccountDestinations.MAIN_ACCOUNT,
        modifier = Modifier.background(MaterialTheme.colorScheme.background) // Ensure NavHost bg
    ) {
        composable(AccountDestinations.MAIN_ACCOUNT) {
            AccountMainScreen(navController = navController)
        }
        composable(AccountDestinations.PROFILE_INFO) {
            ProfileInfoScreen(navController = navController, profile = userProfile, onSave = { updatedProfile ->
                onSaveProfile(updatedProfile)
                navController.navigateUp()
            })
        }
        composable(AccountDestinations.CHANGE_PASSWORD) {
            ChangePasswordScreen(navController = navController, onChangePassword = { current, new ->
                val success = onChangePassword(current, new)
                if (success) navController.navigateUp()
                success
            })
        }
        composable(AccountDestinations.PUSH_NOTIFICATIONS) {
            PushNotificationsScreen(navController = navController, initialState = pushNotificationsEnabled, onToggle = onNotificationToggle)
        }
        composable(AccountDestinations.FAQ) {
            FaqScreen(navController = navController, faqs = faqs)
        }
        composable(AccountDestinations.CONTACT_SUPPORT) {
            ContactSupportScreen(navController = navController, email = contactEmail, phone = contactPhone)
        }
        composable(AccountDestinations.REPORT_BUG) {
            ReportBugScreen(navController = navController, onSubmit = { report ->
                val success = onBugSubmit(report)
                if (success) navController.navigateUp()
                success
            })
        }
        composable(AccountDestinations.LOGOUT_DELETE) {
            LogoutDeleteScreen(navController = navController, onLogout = onLogout, onDeleteAccount = onDeleteAccount)
        }
    }
}

// --- Account Main Screen (Revamped UI) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountMainScreen(navController: NavController) {
    Scaffold(
        topBar = { ModernTopAppBar(title = "Account Settings") }, // Use new AppBar
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 10.dp) // Adjust padding
        ) {
            SectionHeader("General") // Added Section Header
            SettingsCard { // Group related items in a Card
                SettingsItem(
                    icon = Icons.Filled.PersonOutline, // Changed Icon
                    title = "Profile Information",
                    onClick = { navController.navigate(AccountDestinations.PROFILE_INFO) }
                )
                SettingsDivider()
                SettingsItem(
                    icon = Icons.Filled.LockOpen, // Changed Icon
                    title = "Change Password",
                    onClick = { navController.navigate(AccountDestinations.CHANGE_PASSWORD) }
                )
                SettingsDivider()
                SettingsItem(
                    icon = Icons.Filled.NotificationsNone, // Changed Icon
                    title = "Push Notifications",
                    onClick = { navController.navigate(AccountDestinations.PUSH_NOTIFICATIONS) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader("Support") // Added Section Header
            SettingsCard {
                SettingsItem(
                    icon = Icons.AutoMirrored.Filled.HelpOutline,
                    title = "FAQ / Help Center",
                    onClick = { navController.navigate(AccountDestinations.FAQ) }
                )
                SettingsDivider()
                SettingsItem(
                    icon = Icons.AutoMirrored.Filled.HelpOutline, // Changed Icon
                    title = "Contact Support",
                    onClick = { navController.navigate(AccountDestinations.CONTACT_SUPPORT) }
                )
                SettingsDivider()
                SettingsItem(
                    icon = Icons.AutoMirrored.Filled.Notes, // Changed Icon (BugReport is not standard filled)
                    title = "Report a Bug",
                    onClick = { navController.navigate(AccountDestinations.REPORT_BUG) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader("Account Actions") // Added Section Header
            SettingsCard {
                SettingsItem(
                    icon = Icons.AutoMirrored.Filled.Logout,
                    title = "Logout / Delete Account",
                    onClick = { navController.navigate(AccountDestinations.LOGOUT_DELETE) },
                    // Optional: Change icon color for emphasis, but orange is primary
                    // iconTint = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(16.dp)) // Space at the bottom
        }
    }
}

// --- Reusable Modern Settings Item & Card ---
@Composable
fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium, // Use medium rounded corners
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), // Remove shadow for flatter look
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline) // Use subtle border
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
    iconTint: Color = MaterialTheme.colorScheme.primary // Orange icon tint
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
                color = MaterialTheme.colorScheme.onSurface // Primary text color
            )
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 13.sp, // Smaller subtitle
                    color = MaterialTheme.colorScheme.onSurfaceVariant // Secondary text color
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.ArrowForwardIos, // Chevron icon
            contentDescription = null, // Decorative
            tint = MaterialTheme.colorScheme.outlineVariant, // Subtle gray color for chevron
            modifier = Modifier.size(16.dp) // Smaller chevron
        )
    }
}

@Composable
fun SettingsDivider() {
    HorizontalDivider(
        color = MaterialTheme.colorScheme.outline, // Use defined outline color
        thickness = 0.5.dp, // Make it very thin
        modifier = Modifier.padding(start = 56.dp) // Indent divider (icon size + spacer)
    )
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title.uppercase(), // Uppercase for distinction
        style = MaterialTheme.typography.labelMedium, // Use a label style
        color = MaterialTheme.colorScheme.secondary, // Use BLUE accent for headers
        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp, top = 16.dp) // Adjust padding
    )
}

// --- Profile Information Screen (Revamped UI) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileInfoScreen(
    navController: NavController,
    profile: UserProfile,
    onSave: (UserProfile) -> Unit
) {
    var name by rememberSaveable(profile.name) { mutableStateOf(profile.name) }
    var email by rememberSaveable(profile.email) { mutableStateOf(profile.email) }
    var phone by rememberSaveable(profile.phone) { mutableStateOf(profile.phone) }
    val hasChanges = name != profile.name || email != profile.email || phone != profile.phone

    Scaffold(
        topBar = { ModernSubPageAppBar(title = "Profile Information", navController = navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp) // More vertical padding
        ) {
            ModernTextField(
                value = name,
                onValueChange = { name = it },
                label = "Full Name",
                leadingIcon = Icons.Outlined.Person // Add icons
            )
            Spacer(modifier = Modifier.height(16.dp))
            ModernTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email Address",
                leadingIcon = Icons.Outlined.Email // Add icons
            )
            Spacer(modifier = Modifier.height(16.dp))
            ModernTextField(
                value = phone,
                onValueChange = { phone = it },
                label = "Phone Number",
                leadingIcon = Icons.Outlined.Phone // Add icons
            )
            Spacer(modifier = Modifier.height(32.dp))
            ModernButton(
                text = "Save Changes",
                onClick = { onSave(UserProfile(name, email, phone)) },
                enabled = hasChanges
            )
        }
    }
}

// --- Change Password Screen (Revamped UI) ---
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
        topBar = { ModernSubPageAppBar(title = "Change Password", navController = navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
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
                    color = MaterialTheme.colorScheme.error,
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

// --- Reusable Modern Text Fields ---
@Composable
fun ModernTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector? = null, // Optional icon
    isError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small, // Consistent rounding
        leadingIcon = leadingIcon?.let {
            { Icon(it, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant) }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedBorderColor = MaterialTheme.colorScheme.primary, // Orange focus
            unfocusedBorderColor = MaterialTheme.colorScheme.outline, // Light gray border
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary, // Orange label focus
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant, // Gray label
            errorBorderColor = MaterialTheme.colorScheme.error,
            errorLabelColor = MaterialTheme.colorScheme.error,
            errorLeadingIconColor = MaterialTheme.colorScheme.error
        ),
        singleLine = true,
        isError = isError
    )
}

@Composable
fun ModernPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = { // Lock icon
            Icon(Icons.Outlined.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        },
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = if (passwordVisible) "Hide password" else "Show password"
            IconButton(onClick = {passwordVisible = !passwordVisible}){
                Icon(imageVector  = image, description, tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedBorderColor = if(isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = if(isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = if(isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            errorBorderColor = MaterialTheme.colorScheme.error,
            errorLabelColor = MaterialTheme.colorScheme.error,
            errorLeadingIconColor = MaterialTheme.colorScheme.error,
            errorTrailingIconColor = MaterialTheme.colorScheme.error
        ),
        isError = isError
    )
}

// --- Push Notifications Screen (Revamped UI) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PushNotificationsScreen(
    navController: NavController,
    initialState: Boolean,
    onToggle: (Boolean) -> Unit
) {
    var isEnabled by rememberSaveable { mutableStateOf(initialState) }

    Scaffold(
        topBar = { ModernSubPageAppBar(title = "Push Notifications", navController = navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Card( // Use Card for better visual grouping
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), // Use light gray variant bg
                elevation = CardDefaults.cardElevation(0.dp) // No shadow needed if bg is different
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 16.dp)) {
                        Text(
                            "Enable Notifications",
                            style = MaterialTheme.typography.titleMedium // Slightly bolder title
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Receive updates, alerts, and important information.",
                            style = MaterialTheme.typography.bodyMedium, // Use body style
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )
                    }

                    Switch(
                        checked = isEnabled,
                        onCheckedChange = { checked ->
                            isEnabled = checked
                            onToggle(checked)
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary, // Orange thumb
                            checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            uncheckedThumbColor = MaterialTheme.colorScheme.outline, // Use outline color for thumb
                            uncheckedTrackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        )
                    )
                }
            }
        }
    }
}


// --- FAQ Screen (Revamped UI) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaqScreen(
    navController: NavController,
    faqs: List<FaqItem>
) {
    Scaffold(
        topBar = { ModernSubPageAppBar(title = "FAQ / Help Center", navController = navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
        ) {
            item {
                Text(
                    "Frequently Asked Questions",
                    style = MaterialTheme.typography.titleLarge, // Bigger Title
                    color = MaterialTheme.colorScheme.secondary, // BLUE title
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaqListItemModern(faq: FaqItem) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        // onClick defined below on the content Column to avoid expanding on Card edges
        modifier = Modifier.fillMaxWidth().animateContentSize(), // Animate size change
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant // Use surface variant bg
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
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.primary // Orange chevron
                )
            }

            // AnimatedVisibility for smooth expand/collapse
            AnimatedVisibility(visible = expanded) {
                Text(
                    text = faq.answer,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(top = 10.dp) // Add padding only when visible
                )
            }
        }
    }
}


// --- Contact Support Screen (Revamped UI) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactSupportScreen(
    navController: NavController,
    email: String,
    phone: String
) {
    Scaffold(
        topBar = { ModernSubPageAppBar(title = "Contact Support", navController = navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Use a vibrant blue icon here for visual interest
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary, // BLUE Icon
                modifier = Modifier.size(56.dp) // Slightly smaller
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Need Assistance?",
                style = MaterialTheme.typography.headlineSmall, // Adjusted style
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Our support team is ready to help. Reach out using the options below.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary, // Orange icons for contrast on gray bg
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(label, style = MaterialTheme.typography.titleMedium) // Bolder label
                Spacer(modifier = Modifier.height(2.dp))
                Text(value, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

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
        topBar = { ModernSubPageAppBar(title = "Report a Bug", navController = navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
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
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedContainerColor = MaterialTheme.colorScheme.surface, // Ensure bg is white when focused
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
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
        color = if (isError) MaterialTheme.colorScheme.error else SuccessColor,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}


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
        topBar = { ModernSubPageAppBar(title = "Manage Account", navController = navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp),
        ) {
            ModernButton(
                text = "Logout",
                onClick = onLogout,
                icon = Icons.AutoMirrored.Filled.Logout
            )

            Spacer(modifier = Modifier.height(32.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline, thickness = 1.dp)
            Spacer(modifier = Modifier.height(32.dp))

            // Delete Account Section
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.WarningAmber,
                    contentDescription = "Warning",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "Delete Account",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Permanently delete your account and all associated data. This action cannot be undone.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
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
            containerColor = MaterialTheme.colorScheme.surface,
            icon = { Icon(Icons.Filled.WarningAmber, contentDescription = null, tint = MaterialTheme.colorScheme.error) },
            title = { Text("Confirm Deletion", fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to permanently delete your account?", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) },
            confirmButton = {
                Button( // Destructive confirm button
                    onClick = {
                        showDeleteConfirmDialog = false
                        onDeleteAccount()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    ),
                    shape = MaterialTheme.shapes.small
                ) { Text("Delete") }
            },
            dismissButton = {
                TextButton( // Standard dismiss button
                    onClick = { showDeleteConfirmDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary) // Orange text
                ) { Text("Cancel") }
            }
        )
    }
}

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
        !enabled -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f) // Standard disabled color
        isDestructive -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.primary // Orange default
    }
    val contentColor = when {
        !enabled -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        isDestructive -> MaterialTheme.colorScheme.onError
        else -> MaterialTheme.colorScheme.onPrimary // White on orange
    }

    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(52.dp), // Slightly taller button
        enabled = enabled,
        shape = MaterialTheme.shapes.small, // Consistent rounding
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f), // Adjusted disabled bg
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
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
            Text(text, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}


// --- Modern Top App Bars ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernTopAppBar(title: String) {
    Column {
        CenterAlignedTopAppBar( // Use CenterAligned for main screen title
            title = { Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp) }, // Bold Title
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface, // White background
                titleContentColor = MaterialTheme.colorScheme.onSurface // Dark Text
            )
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outline, thickness = 1.dp) // Thin divider below app bar
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
                        tint = MaterialTheme.colorScheme.primary // Orange back arrow
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                navigationIconContentColor = MaterialTheme.colorScheme.primary
            )
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outline, thickness = 1.dp)
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

// --- Previews (Updated for Light Theme) ---
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewAccountMainScreenLight() {
    MyModernAppTheme {
        AccountMainScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewProfileInfoScreenLight() {
    val profile = UserProfile("Test User", "test@example.com", "123-456")
    MyModernAppTheme {
        ProfileInfoScreen(navController = rememberNavController(), profile = profile, onSave = {})
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewChangePasswordScreenLight() {
    MyModernAppTheme {
        ChangePasswordScreen(navController = rememberNavController(), onChangePassword = {_,_ -> true })
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewPushNotificationsScreenLight() {
    MyModernAppTheme {
        PushNotificationsScreen(navController = rememberNavController(), initialState = true, onToggle = {})
    }
}

// Add other previews similarly...

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewLogoutDeleteScreenLight() {
    MyModernAppTheme {
        LogoutDeleteScreen(navController = rememberNavController(), onLogout = { }, onDeleteAccount = { })
    }
}