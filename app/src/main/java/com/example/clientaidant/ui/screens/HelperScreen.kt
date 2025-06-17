package com.example.clientaidant.ui.screens

import ModernButton
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOff
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.clientaidant.data.viewmodels.HelperViewModel
import com.example.clientaidant.data.viewmodels.SocketViewModel
import com.example.clientaidant.network.EndUserInfo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HelperScreen(helperViewModel: SocketViewModel) {
    val connectionState by helperViewModel.connectionState.collectAsState()
    val selectedUser by helperViewModel.selectedUser.collectAsState()

    Scaffold {  innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding)
        ) {
            // Bouton de connexion
            ModernButton(
                text = if (connectionState.isConnected) "Disconnect" else "Connect to Server",
                onClick = {
                    if (connectionState.isConnected) {
                        helperViewModel.disconnect()
                    } else {
                        helperViewModel.connectToServer(helperId = 12) // Votre ID helper
                    }
                },
                icon = if (connectionState.isConnected) Icons.Default.Close else Icons.Default.Wifi
            )

            // Liste des utilisateurs actifs
            LazyColumn {
                items(connectionState.activeUsers) { user ->
                    UserItem(
                        user = user,
                        isSelected = selectedUser?.id == user.id,
                        onClick = { helperViewModel.selectUser(user) }
                    )
                }
            }

            // Affichage de la position de l'utilisateur sélectionné
            selectedUser?.let { user ->
                user.lastPosition?.let { position ->
                    Text("Position: ${position.lat}, ${position.lng}")
                    Text("Dernière mise à jour: ${position.timestamp}")
                }
            }
        }
    }
}



@Composable
fun UserItem(
    user: EndUserInfo,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onClick() }
            .then(
                if (isSelected) {
                    Modifier.border(
                        2.dp,
                        MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Status indicator (cercle coloré)
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            user.status == "online" -> Color.Green
                            user.status == "offline" -> Color.Red
                            else -> Color.Gray
                        }
                    )
            )

            Spacer(modifier = Modifier.width(12.dp))

            // User avatar/icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User Avatar",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // User info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Username
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Status
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = when {
                            user.status == "online" -> Icons.Default.Circle
                            else -> Icons.Default.RadioButtonUnchecked
                        },
                        contentDescription = "Status",
                        modifier = Modifier.size(12.dp),
                        tint = when {
                            user.isOnline -> Color.Green
                            user.status == "online" -> Color.Green
                            else -> Color.Gray
                        }
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = when {
                            user.status == "online" -> "En ligne"
                            user.status == "offline" -> "Hors ligne"
                            else -> user.status.capitalize()
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Last position info if available
                user.lastPosition?.let { position ->
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            modifier = Modifier.size(12.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = try {
                                val date = SimpleDateFormat(
                                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                                    Locale.getDefault()
                                ).parse(position.timestamp)
                                "Pos: ${dateFormat.format(date ?: Date())}"
                            } catch (e: Exception) {
                                "Position disponible"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // Right side indicators
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Location indicator
                if (user.lastPosition != null) {
                    Icon(
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = "Has Location",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.LocationOff,
                        contentDescription = "No Location",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // User ID badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.padding(2.dp)
                ) {
                    Text(
                        text = "ID: ${user.id}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            // Selection indicator
            if (isSelected) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

// Composable pour afficher les détails de l'utilisateur sélectionné
@Composable
fun UserDetailsCard(
    user: EndUserInfo,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = user.username,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "User ID: ${user.userId}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider()

            Spacer(modifier = Modifier.height(16.dp))

            // Status info
            InfoRow(
                icon = Icons.Default.Circle,
                label = "Statut",
                value = when {
                    user.status == "online" -> "En ligne"
                    user.status == "offline" -> "Hors ligne"
                    else -> user.status.capitalize()
                },
                valueColor = when {
                    user.isOnline -> Color.Green
                    user.status == "online" -> Color.Blue
                    else -> Color.Red
                }
            )

            // Position info
            user.lastPosition?.let { position ->
                Spacer(modifier = Modifier.height(12.dp))

                InfoRow(
                    icon = Icons.Default.LocationOn,
                    label = "Latitude",
                    value = String.format("%.6f", position.lat)
                )

                Spacer(modifier = Modifier.height(8.dp))

                InfoRow(
                    icon = Icons.Default.LocationOn,
                    label = "Longitude",
                    value = String.format("%.6f", position.lng)
                )

                Spacer(modifier = Modifier.height(8.dp))

                InfoRow(
                    icon = Icons.Default.Schedule,
                    label = "Dernière mise à jour",
                    value = try {
                        val date = SimpleDateFormat(
                            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                            Locale.getDefault()
                        ).parse(position.timestamp)
                        SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                            .format(date ?: Date())
                    } catch (e: Exception) {
                        position.timestamp
                    }
                )
            } ?: run {
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOff,
                        contentDescription = "No location",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Aucune position disponible",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
private fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    valueColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.width(120.dp)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = valueColor
        )
    }
}