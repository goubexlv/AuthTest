package cm.daccvo.auth.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class DashboardUser(
    val initials: String,
    val firstName: String,
    val lastName: String,
    val lastLogin: String = "2h ago"
)

object AppColors {
    val Primary = Color(0xFF137FEC)
    val PrimaryBlue = Color(0xFF137FEC)
    val BackgroundLight = Color(0xFFF6F7F8)
    val BackgroundDark = Color(0xFF101922)
    val SurfaceDark = Color(0xFF192633)
    val InputBackground = Color(0xFF192633)
    val InputBorder = Color(0xFF324D67)
    val BorderDark = Color(0xFF324D67)
    val InputHover = Color(0xFF233446)
    val TextPrimary = Color(0xFF0D1B10)
    val TextSecondary = Color(0xFF92ADC9)
    val TextMuted = Color(0xFF92ADC9)
    val White = Color(0xFFFFFFFF)
    val WhiteAlpha50 = Color(0x80FFFFFF)
    val WhiteAlpha70 = Color(0xB3FFFFFF)
    val TextPrimaryAlpha70 = Color(0xB30D1B10)
    val TextPrimaryAlpha40 = Color(0x660D1B10)
    val WhiteAlpha40 = Color(0x66FFFFFF)
    val Divider = Color(0xFF324D67)
    val PrimaryAlpha20 = Color(0x3313EC37)
    val PrimaryBlueAlpha20 = Color(0x33137FEC)
    val PrimaryAlpha40 = Color(0x6613EC37)
    val PrimaryAlpha60 = Color(0x9913EC37)
    val RedError = Color(0xFFEF4444)
    val RedAlpha10 = Color(0x1AEF4444)
    val RedAlpha50 = Color(0x80EF4444)
}

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    user: DashboardUser = DashboardUser("JD", "John", "Doe"),
    onNotificationClick: () -> Unit = {},
    onViewProfileClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onSubscriptionClick: () -> Unit = {},
    onSupportClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToExplore: () -> Unit = {},
    onNavigateToActivity: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    selectedTab: Int = 0,
    isDarkMode: Boolean = true
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BackgroundDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 430.dp)
                .align(Alignment.Center)
        ) {
            // Contenu scrollable
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                // En-tête avec avatar et notification
                DashboardHeader(
                    user = user,
                    onNotificationClick = onNotificationClick
                )

                // Titre et description
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 24.dp, bottom = 16.dp)
                ) {
                    Text(
                        text = "Dashboard",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.White,
                        letterSpacing = (-0.5).sp,
                        lineHeight = 38.sp
                    )

                    Text(
                        text = "Gérez votre compte et vos paramètres",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = AppColors.TextMuted,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // Carte "My Profile"
                ProfileCard(
                    onViewProfileClick = onViewProfileClick,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                // Grille de statut (Security & Last Login)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatusCard(
                        icon = Icons.Default.Shield,
                        label = "Statut de sécurité",
                        value = "Sécurisé",
                        modifier = Modifier.weight(1f)
                    )

                    StatusCard(
                        icon = Icons.Default.Schedule,
                        label = "Dernière connexion",
                        value = user.lastLogin,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Liste des options
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MenuItemCard(
                        icon = Icons.Default.Settings,
                        title = "Paramètres du compte",
                        onClick = onSettingsClick
                    )

                    MenuItemCard(
                        icon = Icons.Default.Payment,
                        title = "Abonnement",
                        onClick = onSubscriptionClick
                    )

                    MenuItemCard(
                        icon = Icons.Default.Help,
                        title = "Assistance et FAQ",
                        onClick = onSupportClick
                    )
                }

                // Bouton Logout et version
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 40.dp, bottom = 40.dp)
                ) {
                    Button(
                        onClick = onLogoutClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.SurfaceDark,
                            contentColor = AppColors.RedError
                        ),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            AppColors.BorderDark
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Logout",
                            tint = AppColors.RedError,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Logout",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppColors.RedError
                        )
                    }

                    Text(
                        text = "App Version 1.0.0",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = AppColors.TextMuted,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }

            // Bottom Navigation Bar
            BottomNavigationBar(
                selectedTab = selectedTab,
                onNavigateToHome = onNavigateToHome,
                onNavigateToExplore = onNavigateToExplore,
                onNavigateToActivity = onNavigateToActivity,
                onNavigateToProfile = onNavigateToProfile
            )
        }
    }
}

@Composable
private fun DashboardHeader(
    user: DashboardUser,
    onNotificationClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar et nom
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar avec initiales
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(AppColors.PrimaryBlue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user.initials,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.White
                )
            }

            // Texte de bienvenue
            Column {
                Text(
                    text = "Content de te revoir,",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = AppColors.TextMuted
                )
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.White
                )
            }
        }

        // Bouton notifications
        IconButton(
            onClick = onNotificationClick,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(AppColors.SurfaceDark)
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = AppColors.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun ProfileCard(
    onViewProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = AppColors.SurfaceDark,
        border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.BorderDark)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icône
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(AppColors.PrimaryBlueAlpha20),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = AppColors.PrimaryBlue,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Texte
                Column {
                    Text(
                        text = "Mon profil",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.White
                    )
                    Text(
                        text = "Détails du compte",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = AppColors.TextMuted
                    )
                }
            }

            // Bouton
            Button(
                onClick = onViewProfileClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.PrimaryBlue,
                    contentColor = AppColors.White
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Voir le profil",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun StatusCard(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = AppColors.SurfaceDark,
        border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.BorderDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AppColors.PrimaryBlue,
                modifier = Modifier
                    .size(24.dp)
                    .padding(bottom = 8.dp)
            )

            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = AppColors.TextMuted
            )

            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.White,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun MenuItemCard(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = AppColors.SurfaceDark,
        border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.BorderDark)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AppColors.TextMuted,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = AppColors.White,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Navigate",
                tint = AppColors.TextMuted,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
private fun BottomNavigationBar(
    selectedTab: Int,
    onNavigateToHome: () -> Unit,
    onNavigateToExplore: () -> Unit,
    onNavigateToActivity: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = AppColors.SurfaceDark.copy(alpha = 0.5f),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            AppColors.BorderDark
        ),
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .padding(bottom = 1.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = selectedTab == 0,
                onClick = onNavigateToHome
            )

            BottomNavItem(
                icon = Icons.Default.Explore,
                label = "Explore",
                isSelected = selectedTab == 1,
                onClick = onNavigateToExplore
            )

            BottomNavItem(
                icon = Icons.Default.History,
                label = "Activity",
                isSelected = selectedTab == 2,
                onClick = onNavigateToActivity
            )

            BottomNavItem(
                icon = Icons.Default.Person,
                label = "Profile",
                isSelected = selectedTab == 3,
                onClick = onNavigateToProfile
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) AppColors.Primary else AppColors.TextMuted,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) AppColors.Primary else AppColors.TextMuted
        )
    }
}

@Preview(name = "Dashboard", showBackground = true, heightDp = 900)
@Composable
private fun DashboardScreenPreview() {
    DashboardScreen(
        user = DashboardUser(
            initials = "JD",
            firstName = "John",
            lastName = "Doe",
            lastLogin = "2h ago"
        ),
        selectedTab = 0
    )
}