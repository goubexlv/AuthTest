package cm.daccvo.auth.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Couleurs du thème
object AppColors {
    val Primary = Color(0xFF137FEC)
    val BackgroundDark = Color(0xFF101922)
    val BackgroundLight = Color(0xFFF6F8F6)
    val InputBackground = Color(0xFF192633)
    val InputBorder = Color(0xFF324D67)
    val TextPrimary = Color(0xFF0D1B10)
    val TextSecondary = Color(0xFF92ADC9)
    val White = Color(0xFFFFFFFF)
    val WhiteAlpha50 = Color(0x80FFFFFF)
    val Divider = Color(0xFF324D67)
    val InputHover = Color(0xFF233446)

    val PrimaryVert = Color(0xFF13EC37)
    val WhiteAlpha70 = Color(0xB3FFFFFF)
    val TextPrimaryAlpha70 = Color(0xB30D1B10)
    val TextPrimaryAlpha40 = Color(0x660D1B10)
    val WhiteAlpha40 = Color(0x66FFFFFF)
    val PrimaryAlpha20 = Color(0x3313EC37)
    val PrimaryAlpha40 = Color(0x6613EC37)
    val PrimaryAlpha60 = Color(0x9913EC37)
}


@Composable
fun LoginScreenEmail() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoginPressed by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BackgroundDark)
            .widthIn(max = 430.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Info Icon
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Info",
                    tint = AppColors.WhiteAlpha50,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 32.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "Content de te revoir",
                    color = AppColors.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 40.sp
                )
                Text(
                    text = "Connectez-vous pour poursuivre votre voyage",
                    color = AppColors.TextSecondary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Form Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                // Email Input
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Email",
                        color = AppColors.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = {
                            Text(
                                text = "Entrez votre email",
                                color = AppColors.TextSecondary
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = AppColors.White,
                            unfocusedTextColor = AppColors.White,
                            focusedContainerColor = AppColors.InputBackground,
                            unfocusedContainerColor = AppColors.InputBackground,
                            focusedBorderColor = AppColors.Primary,
                            unfocusedBorderColor = AppColors.InputBorder,
                            cursorColor = AppColors.Primary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                }

                // Password Input
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Mot de passe",
                        color = AppColors.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = {
                            Text(
                                text = "Entrez votre email",
                                color = AppColors.TextSecondary
                            )
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                                    contentDescription = if (passwordVisible) "Masquer le mot de passe" else "Afficher le mot de passe",
                                    tint = AppColors.TextSecondary
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = AppColors.White,
                            unfocusedTextColor = AppColors.White,
                            focusedContainerColor = AppColors.InputBackground,
                            unfocusedContainerColor = AppColors.InputBackground,
                            focusedBorderColor = AppColors.Primary,
                            unfocusedBorderColor = AppColors.InputBorder,
                            cursorColor = AppColors.Primary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                }

                // Forgot Password
                Text(
                    text = "Mot de passe oublié ?",
                    color = AppColors.TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(vertical = 8.dp)
                        .clickable { /* Handle forgot password */ },
                    textAlign = TextAlign.End
                )
            }

            // Login Button
            Button(
                onClick = {
                    isLoginPressed = true
                    // Handle login
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Primary,
                    contentColor = AppColors.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 8.dp)
                    .height(56.dp)
                    .scale(if (isLoginPressed) 0.98f else 1f),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 4.dp
                )
            ) {
                Text(
                    text = "Se connecter",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Reset pressed state
            LaunchedEffect(isLoginPressed) {
                if (isLoginPressed) {
                    kotlinx.coroutines.delay(100)
                    isLoginPressed = false
                }
            }

            // Login with Phone Number
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { /* Handle phone login */ }
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Smartphone,
                    contentDescription = "Phone",
                    tint = AppColors.Primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Se connecter avec un numéro de téléphone",
                    color = AppColors.Primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Divider with text
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    modifier = Modifier.weight(1f),
                    color = AppColors.Divider,
                    thickness = 1.dp
                )
                Text(
                    text = "Ou continuez avec",
                    color = AppColors.TextSecondary,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Divider(
                    modifier = Modifier.weight(1f),
                    color = AppColors.Divider,
                    thickness = 1.dp
                )
            }

            // Social Login Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Google Button
                OutlinedButton(
                    onClick = { /* Handle Google login */ },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = AppColors.InputBackground,
                        contentColor = AppColors.White
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.InputBorder),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = "Google",
                            tint = AppColors.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Google",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Apple Button
                OutlinedButton(
                    onClick = { /* Handle Apple login */ },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = AppColors.InputBackground,
                        contentColor = AppColors.White
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.InputBorder),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Smartphone,
                            contentDescription = "Apple",
                            tint = AppColors.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Apple",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Sign Up Link
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Vous n'avez pas de compte ? ",
                    color = AppColors.TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "S'inscrire",
                    color = AppColors.Primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { /* Handle sign up */ }
                )
            }
        }
    }
}

@Composable
@Preview
fun LoginEmailPreview() {
    LoginScreenEmail()
}


