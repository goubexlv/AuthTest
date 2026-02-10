package cm.daccvo.auth.ui.Onboarding

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Shield
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cm.daccvo.auth.ui.Splash.SplashScreen

// Couleurs pour l'onboarding
object OnboardingColors {
    val Primary = Color(0xFF137FEC)
    val BackgroundDark = Color(0xFF0A1017)
    val SurfaceDark = Color(0xFF192633)
    val BorderDark = Color(0xFF324D67)
    val TextSubtle = Color(0xFF92ADC9)
    val White = Color(0xFFFFFFFF)
    val WhiteAlpha10 = Color(0x1AFFFFFF)
    val WhiteAlpha20 = Color(0x33FFFFFF)
    val Blue700 = Color(0xFF1976D2)
}

/**
 * Données pour une page d'onboarding
 */
data class OnboardingPage(
    val title: String,
    val description: String,
    val iconName: String = "shield_with_heart"
)


@Composable
fun OnboardingScreen(
    onSkip: () -> Unit = {},
    onFinish: () -> Unit = {}
) {
    var currentPage by remember { mutableStateOf(0) }

    val pages = listOf(
        OnboardingPage(
            title = "Sécurisez votre monde",
            description = "Votre confidentialité et votre sécurité sont notre priorité absolue grâce à un cryptage de niveau bancaire.",
            iconName = "shield_with_heart"
        ),
        OnboardingPage(
            title = "Restez connecté",
            description = "Restez en contact avec vos amis et votre famille en toute simplicité sur tous vos appareils.",
            iconName = "diversity_3"
        ),
        OnboardingPage(
            title = "Commencer",
            description = "Rejoignez les millions d'utilisateurs qui nous font confiance pour leurs conversations les plus importantes.",
            iconName = "rocket_launch"
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OnboardingColors.BackgroundDark)
            .widthIn(max = 430.dp)
    ) {
        // Background blur effects
        BackgroundBlurEffects()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
        ) {
            // Top section with Skip button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 56.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Skip",
                    color = OnboardingColors.TextSubtle,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .clickable { onSkip() }
                        .padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Content section
            OnboardingContent(
                page = pages[currentPage],
                pageIndex = currentPage
            )

            Spacer(modifier = Modifier.weight(1f))

            // Bottom section
            BottomNavigationSection(
                currentPage = currentPage,
                totalPages = pages.size,
                onNext = {
                    if (currentPage < pages.size - 1) {
                        currentPage++
                    } else {
                        onFinish()
                    }
                }
            )

            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@Composable
fun BackgroundBlurEffects() {
    // Top right blur circle
    Box(
        modifier = Modifier
            .size(320.dp)
            .offset(x = 120.dp, y = (-96).dp)
            .alpha(0.05f)
            .blur(80.dp)
            .background(
                color = OnboardingColors.Primary,
                shape = CircleShape
            )
    )

    // Bottom left blur circle
    Box(
        modifier = Modifier
            .size(320.dp)
            .offset(x = (-96).dp, y = 120.dp)
            .alpha(0.05f)
            .blur(80.dp)
            .background(
                color = OnboardingColors.Primary,
                shape = CircleShape
            )
    )
}

@Composable
fun OnboardingContent(
    page: OnboardingPage,
    pageIndex: Int
) {
    // Animation pour le changement de page
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "content_scale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .offset(y = (-40).dp)
    ) {
        // Illustration
        IllustrationCard()

        Spacer(modifier = Modifier.height(64.dp))

        // Title and description
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = page.title,
                color = OnboardingColors.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = page.description,
                color = OnboardingColors.TextSubtle,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun IllustrationCard() {
    // Animation du glow
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowScale by infiniteTransition.animateFloat(
        initialValue = 1.15f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_scale"
    )

    Box(
        modifier = Modifier
            .size(288.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer glow effect
        Box(
            modifier = Modifier
                .fillMaxSize()
                .scale(glowScale)
                .alpha(0.1f)
                .blur(60.dp)
                .background(
                    color = OnboardingColors.Primary,
                    shape = CircleShape
                )
        )

        // Main illustration card
        Box(
            modifier = Modifier.size(192.dp),
            contentAlignment = Alignment.Center
        ) {
            // Card with gradient
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(24.dp),
                color = Color.Transparent
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    OnboardingColors.Primary,
                                    OnboardingColors.Blue700
                                )
                            ),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = OnboardingColors.WhiteAlpha10,
                            shape = RoundedCornerShape(24.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Top light reflection
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                            .align(Alignment.TopCenter)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        OnboardingColors.WhiteAlpha20,
                                        Color.Transparent
                                    ),
                                    center = androidx.compose.ui.geometry.Offset(0.3f, 0.3f)
                                ),
                                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                            )
                    )

                    // Additional top shine
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(96.dp)
                            .align(Alignment.TopCenter)
                            .background(
                                color = OnboardingColors.WhiteAlpha10.copy(alpha = 0.05f),
                                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                            )
                    )

                    // Icon with subtle animation
                    val iconRotation by infiniteTransition.animateFloat(
                        initialValue = -3f,
                        targetValue = 3f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 3000, easing = FastOutSlowInEasing),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "icon_rotation"
                    )

                    Icon(
                        imageVector = Icons.Outlined.Shield,
                        contentDescription = "Security",
                        tint = OnboardingColors.White,
                        modifier = Modifier
                            .size(128.dp)
                            .scale(1.2f)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationSection(
    currentPage: Int,
    totalPages: Int,
    onNext: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // Page indicators
        PageIndicators(
            currentPage = currentPage,
            totalPages = totalPages
        )

        // Next button
        NextButton(
            isLastPage = currentPage == totalPages - 1,
            onClick = onNext
        )
    }
}

@Composable
fun PageIndicators(
    currentPage: Int,
    totalPages: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalPages) { index ->
            val isActive = index == currentPage

            // Animation de la largeur
            val width by animateDpAsState(
                targetValue = if (isActive) 24.dp else 8.dp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "indicator_width_$index"
            )

            Box(
                modifier = Modifier
                    .width(width)
                    .height(8.dp)
                    .background(
                        color = if (isActive)
                            OnboardingColors.Primary
                        else
                            OnboardingColors.WhiteAlpha20,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun NextButton(
    isLastPage: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = OnboardingColors.Primary,
            contentColor = OnboardingColors.White
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (isLastPage) "Get Started" else "Next",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Outlined.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview
@Composable
fun OnboardingPreview(){
    OnboardingScreen()
}