package cm.daccvo.auth.ui.Onboarding

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

data class OnboardingPageData(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val gradientColors: List<Color> = listOf(
        OnboardingColors.Primary,
        OnboardingColors.Blue700
    )
)

/**
 * Écran d'onboarding avec HorizontalPager pour swipe
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreenPager(
    onSkip: () -> Unit = {},
    onFinish: () -> Unit = {}
) {
    val pages = remember {
        listOf(
            OnboardingPageData(
                title = "Secure Your World",
                description = "Your privacy and security are our top priority with bank-grade encryption.",
                icon = Icons.Outlined.Shield
            ),
            OnboardingPageData(
                title = "Stay Connected",
                description = "Connect with friends and family seamlessly across all your devices.",
                icon = Icons.Outlined.Diversity3
            ),
            OnboardingPageData(
                title = "Get Started",
                description = "Join millions of users who trust us with their most important conversations.",
                icon = Icons.Outlined.RocketLaunch
            )
        )
    }

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OnboardingColors.BackgroundDark)
            .widthIn(max = 430.dp)
    ) {
        // Background effects
        BackgroundBlurEffects()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
        ) {
            // Top Skip button
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

            // Pager content
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingPageContent(
                    page = pages[page],
                    pageIndex = page
                )
            }

            // Bottom section
            BottomNavigationSectionPager(
                pagerState = pagerState,
                totalPages = pages.size,
                onNext = {
                    coroutineScope.launch {
                        if (pagerState.currentPage < pages.size - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            onFinish()
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@Composable
fun OnboardingPageContent(
    page: OnboardingPageData,
    pageIndex: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .offset(y = (-40).dp)
    ) {
        // Illustration
        IllustrationCardWithIcon(icon = page.icon)

        Spacer(modifier = Modifier.height(64.dp))

        // Content
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
fun IllustrationCardWithIcon(
    icon: ImageVector
) {
    val infiniteTransition = rememberInfiniteTransition(label = "illustration")

    val glowScale by infiniteTransition.animateFloat(
        initialValue = 1.15f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_scale"
    )

    val iconRotation by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "icon_rotation"
    )

    Box(
        modifier = Modifier.size(288.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer glow
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

        // Card
        Surface(
            modifier = Modifier.size(192.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color.Transparent,
            shadowElevation = 20.dp
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
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = OnboardingColors.WhiteAlpha10,
                        shape = RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Radial gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    OnboardingColors.WhiteAlpha20,
                                    Color.Transparent
                                ),
                                center = androidx.compose.ui.geometry.Offset(0.3f, 0.3f),
                                radius = 0.8f
                            ),
                            shape = RoundedCornerShape(24.dp)
                        )
                )

                // Top shine
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

                // Icon
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = OnboardingColors.White,
                    modifier = Modifier
                        .size(128.dp)
                        .scale(1.2f)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNavigationSectionPager(
    pagerState: androidx.compose.foundation.pager.PagerState,
    totalPages: Int,
    onNext: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // Page indicators
        PageIndicatorsPager(
            currentPage = pagerState.currentPage,
            totalPages = totalPages
        )

        // Next button
        NextButton(
            isLastPage = pagerState.currentPage == totalPages - 1,
            onClick = onNext
        )
    }
}

@Composable
fun PageIndicatorsPager(
    currentPage: Int,
    totalPages: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalPages) { index ->
            val isActive = index == currentPage

            val width by animateDpAsState(
                targetValue = if (isActive) 24.dp else 8.dp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "indicator_$index"
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


@Preview
@Composable
fun Onboarding2Preview(){
    OnboardingScreenPager()
}