package cm.daccvo.auth.ui.login

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cm.daccvo.auth.composant.LANGUAGES
import cm.daccvo.auth.composant.LanguageSelector
import cm.daccvo.auth.uiState.AccountUiState
import cm.daccvo.auth.R
import cm.daccvo.auth.utils.Validators


// Couleurs du thème (réutilisation)
//object AppColors {
//    val Primary = Color(0xFF137FEC)
//    val BackgroundDark = Color(0xFF101922)
//    val InputBackground = Color(0xFF192633)
//    val InputBorder = Color(0xFF324D67)
//    val TextSecondary = Color(0xFF92ADC9)
//    val White = Color(0xFFFFFFFF)
//    val WhiteAlpha50 = Color(0x80FFFFFF)
//    val Divider = Color(0xFF324D67)
//    val InputHover = Color(0xFF233446)
//}

@Composable
fun LoginScreenPhone(
    uiState: AccountUiState,
    onPhoneChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit ,
    onBackClick: () -> Unit = {},
    onEmailLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {

    var showCountryPicker by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf(CountryCode.CM) }
    var isSendCodePressed by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var selectedLang by remember {
        mutableStateOf(LANGUAGES.first())
    }

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
            // Top Bar with Back and Info buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(86.dp).alpha(0.8f)
                )

                LanguageSelector(
                    languages = LANGUAGES,
                    selected = selectedLang,
                    onSelected = { selectedLang = it }
                )
            }

            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 32.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = "Connexion",
                    color = AppColors.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 40.sp,
                    textAlign = TextAlign.Center
                )

            }

            // Phone Number Input Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Par téléphone",
                        color = AppColors.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        // Country Code Selector
                        Surface(
                            modifier = Modifier
                                .clickable { showCountryPicker = true }
                                .height(56.dp),
                            shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
                            color = Color.White.copy(alpha = 0.05f),
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = selectedCountry.flag,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = selectedCountry.code,
                                    color = AppColors.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Icon(
                                    imageVector = Icons.Outlined.ExpandMore,
                                    contentDescription = "Select country",
                                    tint = AppColors.TextSecondary,
                                    modifier = Modifier
                                        .padding(start = 4.dp)
                                        .size(18.dp)
                                )
                            }
                        }



                        // Phone Number Input
                        OutlinedTextField(
                            value = uiState.phone,
                            onValueChange = { onPhoneChange(it) },
                            placeholder = {
                                Text(
                                    text = "Entrez votre numéro de téléphone",
                                    color = AppColors.TextSecondary
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = AppColors.White,
                                unfocusedTextColor = AppColors.White,
                                focusedContainerColor = Color.White.copy(alpha = 0.05f),
                                unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
                                focusedBorderColor = AppColors.Primary,
                                unfocusedBorderColor = AppColors.InputBorder,
                                cursorColor = AppColors.Primary
                            ),
                            shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                        )
                    }
                }

                // Password Input
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {

                    OutlinedTextField(
                        value = uiState.password,
                        onValueChange = onPasswordChange,
                        placeholder = {
                            Text(
                                text = "Entrez votre mot de passe",
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
                            focusedContainerColor = Color.White.copy(alpha = 0.05f),
                            unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
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
                    color = AppColors.Primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(vertical = 8.dp)
                        .clickable { onLoginClick() },
                    textAlign = TextAlign.Center
                )

            }

            val isEnabled = remember(uiState) {
                (uiState.phone.isNotBlank() && uiState.password.isNotBlank())
                        && (Validators.isValidPhone(uiState.phone) || Validators.isValidPassword(
                    uiState.password
                ))
            }

            // Send Verification Code Button


            Button(
                onClick = {
                    isSendCodePressed = true
                    onLoginClick()
                },
                enabled = isEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isEnabled)
                        AppColors.Primary
                    else
                        AppColors.Disabled, // 🔥 gris / désactivé

                    contentColor = if (isEnabled)
                        AppColors.White
                    else
                        AppColors.DisabledContent
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 8.dp)
                    .height(56.dp)
                .scale(if (isSendCodePressed) 0.98f else 1f),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = if (isEnabled) 8.dp else 0.dp,
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
            LaunchedEffect(isSendCodePressed) {
                if (isSendCodePressed) {
                    kotlinx.coroutines.delay(100)
                    isSendCodePressed = false
                }
            }

            // Login with Email
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { onEmailLoginClick() }
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Connectez-vous avec e-mail",
                    color = AppColors.Primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }


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
                    modifier = Modifier.clickable { onRegisterClick() }
                )
            }
        }

        // Country Picker Modal
        if (showCountryPicker) {
            CountryPickerDialog(
                selectedCountry = selectedCountry,
                onDismiss = { showCountryPicker = false },
                onCountrySelected = { country ->
                    selectedCountry = country
                    showCountryPicker = false
                }
            )
        }
    }

    val context = LocalContext.current
    LaunchedEffect(
        key1 = uiState.isLoginSucceed,
        key2 = uiState.authErrorMessage,
        block = {

            if (uiState.authErrorMessage != null) {
                //showToast(uiState.authErrorMessage!!)
                Toast.makeText(context,uiState.authErrorMessage!!,Toast.LENGTH_SHORT).show()
            }
        }
    )
}

// Données des pays
data class CountryCode(
    val name: String,
    val code: String,
    val flag: String,
    val dialCode: String
) {
    companion object {
//        val US = CountryCode("United States", "+1", "🇺🇸", "+1")
//        val FR = CountryCode("France", "+33", "🇫🇷", "+33")
//        val GB = CountryCode("United Kingdom", "+44", "🇬🇧", "+44")
//        val DE = CountryCode("Germany", "+49", "🇩🇪", "+49")
        val CM = CountryCode("Cameroon", "+237", "🇨🇲", "+237")
//        val CA = CountryCode("Canada", "+1", "🇨🇦", "+1")
//        val IN = CountryCode("India", "+91", "🇮🇳", "+91")
//        val CN = CountryCode("China", "+86", "🇨🇳", "+86")
//        val JP = CountryCode("Japan", "+81", "🇯🇵", "+81")
//        val BR = CountryCode("Brazil", "+55", "🇧🇷", "+55")

        fun getAllCountries() = listOf(
            CM
        )
    }
}

@Composable
fun CountryPickerDialog(
    selectedCountry: CountryCode,
    onDismiss: () -> Unit,
    onCountrySelected: (CountryCode) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White.copy(alpha = 0.05f),
        title = {
            Text(
                text = "Select Country",
                color = AppColors.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp)
            ) {
                CountryCode.getAllCountries().forEach { country ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCountrySelected(country) }
                            .padding(vertical = 4.dp),
                        color = if (country == selectedCountry)
                            AppColors.Primary.copy(alpha = 0.2f)
                        else
                            Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = country.flag,
                                fontSize = 24.sp,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = country.name,
                                    color = AppColors.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Text(
                                text = country.code,
                                color = AppColors.TextSecondary,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancel",
                    color = AppColors.Primary
                )
            }
        }
    )
}

@Composable
@Preview
fun LoginPhonePreview() {
    LoginScreenPhone(
        uiState = AccountUiState(),
        onPhoneChange = {},
        onPasswordChange = {},
        onLoginClick = {},
    )
}