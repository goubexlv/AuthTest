package cm.daccvo.auth.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cm.daccvo.auth.uiState.ProfileDataUiState

data class UserProfile(
    val name: String,
    val email: String,
    val avatarUrl: String? = null
)

@Composable
fun UserProfileScreen(
    user: ProfileDataUiState,
    onBackClick: () -> Unit = {},
    onAccountInfoClick: () -> Unit = {},
    onSecurityClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
               AppColors.BackgroundDark
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 430.dp)
                .align(Alignment.Center)
                .verticalScroll(scrollState)
        ) {
            // Bouton retour
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                    contentDescription = "Back",
                    tint = AppColors.WhiteAlpha50,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onBackClick() }
                )
            }

            // Section Avatar et Info utilisateur
            ProfileHeader(
                user = user,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 32.dp)
            )

            // Liste des options de profil
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ProfileMenuItem(
                    icon = Icons.Outlined.PersonOutline,
                    title = "Informations sur le compte",
                    onClick = onAccountInfoClick
                )

                ProfileMenuItem(
                    icon = Icons.Outlined.Shield,
                    title = "Sécurité",
                    onClick = onSecurityClick
                )

                ProfileMenuItem(
                    icon = Icons.Outlined.Notifications,
                    title = "Notifications",
                    onClick = onNotificationsClick
                )

                ProfileMenuItem(
                    icon = Icons.Outlined.Lock,
                    title = "Confidentialité",
                    onClick = onPrivacyClick
                )
            }

            // Spacer pour pousser le logout en bas
            Spacer(modifier = Modifier.weight(1f))

            // Section Logout et Version
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 40.dp, bottom = 48.dp)
            ) {
                // Bouton Logout
                Button(
                    onClick = onLogoutClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0x1AFF3B30), // #ff3b30 avec 10% d'opacité
                        contentColor = Color(0xFFFF3B30)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        Color(0x4DFF3B30) // #ff3b30 avec 30% d'opacité
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 2.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Logout",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Logout",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Version de l'app
                Text(
                    text = "App Version 1.0.0",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = AppColors.TextMuted.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )
            }
        }
    }
}

@Composable
private fun ProfileHeader(
    user: ProfileDataUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar circulaire
        Box(
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .background(AppColors.SurfaceDark)
                .border(
                    width = 2.dp,
                    color = AppColors.BorderDark,
                    shape = CircleShape
                )
                .padding(bottom = 24.dp),
            contentAlignment = Alignment.Center
        ) {

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile Picture",
                    tint = AppColors.White.copy(alpha = 0.2f),
                    modifier = Modifier.size(72.dp)
                )

        }

        Spacer(modifier = Modifier.height(24.dp))

        // Nom de l'utilisateur
        Text(
            text = "goube",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.White,
            lineHeight = 28.sp
        )

        val identiter : String? = if (user.profile?.isEmailVerified != null) user.profile?.email else user.profile?.phone

        // Email
        Text(
            text = identiter.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = AppColors.TextMuted,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
private fun ProfileMenuItem(
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AppColors.PrimaryBlue,
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppColors.White
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Navigate",
                tint = AppColors.TextMuted,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(name = "User Profile - Dark", showBackground = true, heightDp = 900)
@Composable
private fun UserProfileScreenPreviewDark() {
    UserProfileScreen(
        user = ProfileDataUiState(),
    )
}