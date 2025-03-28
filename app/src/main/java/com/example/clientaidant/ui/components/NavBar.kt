import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.clientaidant.R

class BottomBarShape(
    private val cutoutRadius: Dp,
    private val cornerRadius: Dp,
    private val bottomPadding: Dp,
    private val cutoutHorizontalOffset: Float
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path()
        val cutoutRadiusPx = with(density) { cutoutRadius.toPx() }
        val cornerRadiusPx = with(density) { cornerRadius.toPx() }
        val bottomPaddingPx = with(density) { bottomPadding.toPx() }
        val barHeight = size.height - bottomPaddingPx
        val cutoutDiameter = cutoutRadiusPx * 2
        val cutoutWidth = cutoutDiameter * 1.1f
        val startX = cutoutHorizontalOffset - cutoutWidth / 2f
        val endX = cutoutHorizontalOffset + cutoutWidth / 2f
        val centerCutoutY = cutoutRadiusPx
        path.moveTo(0f, cornerRadiusPx)
        path.quadraticBezierTo(0f, 0f, cornerRadiusPx, 0f)
        path.lineTo(startX.coerceIn(cornerRadiusPx, size.width - cornerRadiusPx), 0f)
        path.cubicTo(
            x1 = startX + cutoutWidth * 0.2f, y1 = 0f,
            x2 = cutoutHorizontalOffset - cutoutRadiusPx * 0.6f, y2 = centerCutoutY,
            x3 = cutoutHorizontalOffset, y3 = centerCutoutY
        )
        path.cubicTo(
            x1 = cutoutHorizontalOffset + cutoutRadiusPx * 0.6f, y1 = centerCutoutY,
            x2 = endX - cutoutWidth * 0.2f, y2 = 0f,
            x3 = endX.coerceIn(cornerRadiusPx, size.width - cornerRadiusPx), y3 = 0f
        )
        path.lineTo(size.width - cornerRadiusPx, 0f)
        path.quadraticBezierTo(size.width, 0f, size.width, cornerRadiusPx)
        path.lineTo(size.width, barHeight - cornerRadiusPx)
        path.quadraticBezierTo(size.width, barHeight, size.width - cornerRadiusPx, barHeight)
        path.lineTo(cornerRadiusPx, barHeight)
        path.quadraticBezierTo(0f, barHeight, 0f, barHeight - cornerRadiusPx)
        path.lineTo(0f, cornerRadiusPx)
        path.close()
        return Outline.Generic(path)
    }
}

@Composable
fun AnimatedBottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Screen.Home to "Home",
        Screen.Tracking to "Tracking",
        Screen.Notifications to "Notifications",
        Screen.Account to "Account"
    )

    val currentRoute = currentRoute(navController)

    val selectedItem by remember(currentRoute) {
        derivedStateOf {
            when (currentRoute) {
                Screen.Home.route -> 0
                Screen.Tracking.route -> 1
                Screen.Notifications.route -> 2
                Screen.Account.route -> 3
                else -> 0
            }
        }
    }

    val density = LocalDensity.current
    val cutoutRadius = 30.dp
    val cornerRadius = 0.dp
    val barHeight = 65.dp
    val totalHeight = 100.dp
    val bottomPadding = 16.dp
    val targetOffset = remember(selectedItem, items.size) {
        derivedStateOf {
            if (items.size > 0) {
                val itemWidthPx = density.density * (360.dp.value / items.size)
                val fraction = (selectedItem + 0.5f) / items.size.toFloat()
                fraction
            } else {
                0.5f
            }
        }
    }

    val animatedCutoutFraction by animateFloatAsState(
        targetValue = targetOffset.value,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(totalHeight)
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(barHeight + bottomPadding)
                    .zIndex(0f)
            ) {
                val widthPx = with(LocalDensity.current) { maxWidth.toPx() }
                val animatedCutoutOffsetPx = animatedCutoutFraction * widthPx
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            BottomBarShape(
                                cutoutRadius = cutoutRadius,
                                cornerRadius = cornerRadius,
                                bottomPadding = 0.dp,
                                cutoutHorizontalOffset = animatedCutoutOffsetPx
                            )
                        )
                        .background(Color.White)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(barHeight)
                    .padding(bottom = 0.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Top
            ) {
                items.forEachIndexed { index, (screen, label) ->
                    NavItem(
                        icon = painterResource(id = when (screen) {
                            Screen.Home -> R.drawable.d_home
                            Screen.Tracking -> R.drawable.d_tracking
                            Screen.Notifications -> R.drawable.d_notification
                            Screen.Account -> R.drawable.d_account
                            else -> R.drawable.d_home
                        }),
                        label = label,
                        isSelected = selectedItem == index,
                        cutoutRadius = cutoutRadius,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.NavItem(
    icon: Painter,
    label: String,
    isSelected: Boolean,
    cutoutRadius: Dp,
    onClick: () -> Unit
) {
    val targetOffsetValue = if (isSelected) - (cutoutRadius.value * 0.7f) else 0f
    val animatedOffset by animateFloatAsState(
        targetValue = targetOffsetValue,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.2f else 1f,
        animationSpec = tween(200)
    )

    val itemColor = Color(0xFFFA8609)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .weight(1f)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .zIndex(if (isSelected) 1f else 0f)
            .offset(y = animatedOffset.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 0.dp)
        ) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .background(itemColor, CircleShape)
                        .scale(scale)
                )
            }
            Icon(
                painter = icon,
                contentDescription = label,
                tint = if (isSelected) Color.White else Color.Gray,
                modifier = Modifier
                    .size(if (isSelected) 28.dp else 24.dp)
            )
        }

        AnimatedVisibility(
            visible = isSelected,
            enter = fadeIn(animationSpec = tween(150, delayMillis = 100)) +
                    expandVertically(animationSpec = tween(200, delayMillis = 100)),
            exit = fadeOut(animationSpec = tween(100)) +
                    shrinkVertically(animationSpec = tween(150)),
        ) {
            Text(
                text = label,
                color = itemColor,
                fontSize = 12.sp,
            )
        }
    }
}


