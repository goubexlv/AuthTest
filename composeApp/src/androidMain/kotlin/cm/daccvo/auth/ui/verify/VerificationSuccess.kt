package cm.daccvo.auth.ui.verify


import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cm.daccvo.auth.ui.login.AppColors
import kotlinx.coroutines.delay


@Composable
fun VerificationSuccessScreen(
    onContinue: () -> Unit = {},
    onClose: () -> Unit = {},
    isDarkMode: Boolean = isSystemInDarkTheme()
) {
    // Auto-redirect après 3 secondes
    var countdown by remember { mutableStateOf(3) }

    LaunchedEffect(Unit) {
        while (countdown > 0) {
            delay(1000)
            countdown--
        }
        onContinue()
    }

    // Animation de pulsation
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (isDarkMode) AppColors.BackgroundDark
                else AppColors.BackgroundDark
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 448.dp)
                .align(Alignment.Center)
        ) {
            // TopAppBar avec bouton Close
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(
                    onClick = onClose,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = if (isDarkMode) AppColors.White else AppColors.TextPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Contenu principal centré
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Icône de succès avec effet de pulsation
                Box(
                    modifier = Modifier.padding(bottom = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Cercle de pulsation externe
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .scale(pulseScale)
                            .background(
                                color = AppColors.PrimaryAlpha20,
                                shape = CircleShape
                            )
                    )

                    // Cercle intérieur avec icône
                    Box(
                        modifier = Modifier
                            .size(128.dp)
                            .background(
                                color = AppColors.PrimaryVert,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Success",
                            tint = AppColors.BackgroundDark,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }

                // Titre
                Text(
                    text = "Vérifié!",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isDarkMode) AppColors.White else AppColors.TextPrimary,
                    textAlign = TextAlign.Center,
                    letterSpacing = (-0.5).sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                // Description
                Text(
                    text = "Votre identité a été confirmée avec succès. Vous pouvez maintenant vous connecter.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = if (isDarkMode) AppColors.WhiteAlpha70 else AppColors.TextPrimaryAlpha70,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp,
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 32.dp)
                )

                // Grille décorative
//                DecorativeGrid(
//                    isDarkMode = isDarkMode,
//                    modifier = Modifier
//                        .widthIn(max = 280.dp)
//                        .padding(top = 16.dp)
//                )
            }

            // Footer avec bouton et texte de redirection
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Bouton principal
                Button(
                    onClick = onContinue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Primary,
                        contentColor = AppColors.BackgroundDark
                    ),
                    shape = RoundedCornerShape(100),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 2.dp
                    )
                ) {
                    Text(
                        text = "Continuer vers le login",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.2).sp
                    )
                }

                // Texte de redirection
                Text(
                    text = "REDIRECTION DANS $countdown SECONDES...",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isDarkMode) AppColors.WhiteAlpha40 else AppColors.TextPrimaryAlpha40,
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.5.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            // Safe area spacing
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun DecorativeGrid(
    isDarkMode: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Colonne 1
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(
                            color = AppColors.PrimaryAlpha40,
                            shape = RoundedCornerShape(12.dp)
                        )
                )
            }

            // Colonne 2
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(
                            color = AppColors.PrimaryAlpha60,
                            shape = RoundedCornerShape(12.dp)
                        )
                )
            }

            // Colonne 3
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(
                            color = AppColors.PrimaryAlpha40,
                            shape = RoundedCornerShape(12.dp)
                        )
                )
            }
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
private fun VerificationSuccessScreenPreviewDark() {
    VerificationSuccessScreen(
        isDarkMode = true
    )
}