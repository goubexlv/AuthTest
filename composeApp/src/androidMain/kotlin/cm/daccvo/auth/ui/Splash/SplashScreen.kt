package cm.daccvo.auth.ui.Splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cm.daccvo.auth.ui.login.AppColors
import kotlinx.coroutines.delay
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.ui.tooling.preview.Preview
import cm.daccvo.auth.security.AuthState
import cm.daccvo.auth.security.UserSettingsDataStore


@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit = {},
    userSettings: UserSettingsDataStore
) {
    var startAnimation by remember { mutableStateOf(false) }

    // Lancer l'animation au démarrage
    LaunchedEffect(Unit) {
        startAnimation = true
        // Durée du splash screen (3 secondes)
        delay(3000)
        userSettings.onAppStart()
        //onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BackgroundDark)
            .widthIn(max = 430.dp)
    ) {
        // Cercle flou en haut à droite
        Box(
            modifier = Modifier
                .size(256.dp)
                .offset(x = 120.dp, y = (-96).dp)
                .alpha(0.05f)
                .blur(80.dp)
                .background(
                    color = AppColors.Primary,
                    shape = CircleShape
                )
                .align(Alignment.TopEnd)
        )

        // Cercle flou en bas à gauche
        Box(
            modifier = Modifier
                .size(256.dp)
                .offset(x = (-96).dp, y = 120.dp)
                .alpha(0.05f)
                .blur(80.dp)
                .background(
                    color = AppColors.Primary,
                    shape = CircleShape
                )
                .align(Alignment.BottomStart)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // Logo section avec animation
            LogoSection(startAnimation = startAnimation)

            Spacer(modifier = Modifier.weight(1f))

            // Bottom section
            BottomSection(startAnimation = startAnimation)
        }
    }
}

@Composable
fun LogoSection(startAnimation: Boolean) {
    // Animation de scale pour le logo
    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.3f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "logo_scale"
    )

    // Animation de fade pour le logo
    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "logo_alpha"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .scale(scale)
            .alpha(alpha)
            .offset(y = (-64).dp)
    ) {
        // Logo container avec glow effect
        Box(
            modifier = Modifier.padding(bottom = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            // Glow effect (blur background)
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .scale(1.1f)
                    .alpha(0.3f)
                    .blur(40.dp)
                    .background(
                        color = AppColors.Primary,
                        shape = CircleShape
                    )
            )

            // Logo card
            Surface(
                modifier = Modifier.size(96.dp),
                shape = RoundedCornerShape(16.dp),
                color = AppColors.Primary,
                shadowElevation = 30.dp,
                tonalElevation = 0.dp
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Shield,
                        contentDescription = "Logo",
                        tint = AppColors.White,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        }

        // App name
        Text(
            text = "Orion",
            color = AppColors.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.5).sp
        )
    }
}

@Composable
fun BottomSection(startAnimation: Boolean) {
    // Animation de fade pour la section bottom
    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000, delayMillis = 300),
        label = "bottom_alpha"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier.alpha(alpha)
    ) {
        // Loading dots
        LoadingDots()

        // Company branding
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "FROM",
                color = AppColors.TextSecondary.copy(alpha = 0.4f),
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 2.sp
            )
            Text(
                text = "DACCVO",
                color = AppColors.WhiteAlpha80,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 3.sp
            )
        }
    }
}

@Composable
fun LoadingDots() {
    val infiniteTransition = rememberInfiniteTransition(label = "dots_transition")

    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { index ->
            val alpha by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 0.2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 600),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(index * 200)
                ),
                label = "dot_${index}_alpha"
            )

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .alpha(alpha)
                    .background(
                        color = AppColors.Primary,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Preview
@Composable
fun SplashPreview(){
    SplashScreen()
}