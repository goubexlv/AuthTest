package cm.daccvo.auth.ui.register

import android.R
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cm.daccvo.auth.composant.LoadingOverlay
import cm.daccvo.auth.ui.login.AppColors
import cm.daccvo.auth.uiState.AccountUiState
import cm.horion.models.domain.LoginMethod
import kotlinx.coroutines.delay


@Composable
fun CreateAccountScreen(
    uiState: AccountUiState,
    onBackClick: () -> Unit = {},
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onModelChange: (LoginMethod) -> Unit,
    onNavigateToVerifieCode: () -> Unit,
) {

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    // Indicateur de chargement centré
    LaunchedEffect(Unit) {
        onModelChange(LoginMethod.EMAIL)
    }

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

            // En-tête
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Créer un compte",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.White,
                    letterSpacing = (-0.5).sp,
                    lineHeight = 38.sp
                )

                Text(
                    text = "Rejoignez-nous et commencez votre voyage dès aujourd'hui",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = AppColors.TextSecondary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Formulaire
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Champ Nom complet
//                CustomTextField(
//                    value = fullName,
//                    onValueChange = { fullName = it },
//                    label = "Full Name",
//                    placeholder = "Enter your full name",
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Text,
//                        imeAction = ImeAction.Next
//                    ),
//                    keyboardActions = KeyboardActions(
//                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
//                    )
//                )

                // Champ Email
                CustomTextField(
                    value = uiState.email,
                    onValueChange = onEmailChange,
                    label = "Email",
                    placeholder = "Entrez votre emaill",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )

                // Champ Mot de passe
                PasswordTextField(
                    value = uiState.password,
                    onValueChange = onPasswordChange,
                    label = "Mot de passe",
                    placeholder = "Créer un mot de passe",
                    passwordVisible = passwordVisible,
                    onVisibilityToggle = { passwordVisible = !passwordVisible },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )

                // Champ Confirmation mot de passe
                PasswordTextField(
                    value = uiState.confirmPassword,
                    onValueChange = onConfirmPasswordChange,
                    label = "Confirmez le mot de passe",
                    placeholder = "Confirmez votre mot de passe",
                    passwordVisible = confirmPasswordVisible,
                    onVisibilityToggle = { confirmPasswordVisible = !confirmPasswordVisible },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            onSignUpClick()
                        }
                    )
                )
            }

            val isFormValid = remember(uiState) {
                uiState.email.isNotBlank() &&
                        uiState.password.isNotBlank() &&
                        uiState.confirmPassword.isNotBlank() &&
                        uiState.password == uiState.confirmPassword
            }

            if (
                uiState.confirmPassword.isNotBlank() &&
                uiState.password != uiState.confirmPassword
            ) {
                Text(
                    text = "Les mots de passe ne correspondent pas",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            if (
                uiState.authErrorMessage != null
            ){
                Text(
                    text = uiState.authErrorMessage!!,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }


            // Bouton Sign Up
            Button(
                onClick = { onSignUpClick() },
                enabled = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 40.dp, bottom = 8.dp)
                    .height(56.dp)
                    .scale(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Primary,
                    contentColor = AppColors.White
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 2.dp
                )
            ) {
                Text(
                    text = "S'inscrire",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Texte des conditions
            TermsText(
                onTermsClick = onTermsClick,
                onPrivacyClick = onPrivacyClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )

            // Spacer pour pousser le texte "Already have an account" en bas
            Spacer(modifier = Modifier.weight(1f))

            // Texte "Already have an account"
            LoginPrompt(
                onLoginClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 40.dp)
            )


        }
        LoadingOverlay(uiState.isAuthenticating)
    }

    val context = LocalContext.current
    LaunchedEffect(
        key1 = uiState.authenticationSucceed,
        key2 = uiState.authErrorMessage,
        block = {
            if (uiState.authenticationSucceed) {
                onNavigateToVerifieCode()
            }

        }
    )

}

@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = AppColors.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = AppColors.TextSecondary
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
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
            singleLine = true,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )
    }
}

@Composable
private fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    passwordVisible: Boolean,
    onVisibilityToggle: () -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = AppColors.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = AppColors.TextSecondary
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
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
            singleLine = true,
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = onVisibilityToggle) {
                    Icon(
                        imageVector = if (passwordVisible)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible)
                            "Hide password"
                        else
                            "Show password",
                        tint = AppColors.TextSecondary
                    )
                }
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )
    }
}

@Composable
private fun TermsText(
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = AppColors.TextSecondary,
                fontSize = 12.sp
            )
        ) {
            append("En vous inscrivant, vous acceptez nos conditions générales. ")
        }

        pushStringAnnotation(tag = "terms", annotation = "terms")
        withStyle(
            style = SpanStyle(
                color = AppColors.White,
                fontSize = 12.sp,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Conditions d'utilisation")
        }
        pop()

        withStyle(
            style = SpanStyle(
                color = AppColors.TextSecondary,
                fontSize = 12.sp
            )
        ) {
            append(" et ")
        }

        pushStringAnnotation(tag = "privacy", annotation = "privacy")
        withStyle(
            style = SpanStyle(
                color = AppColors.White,
                fontSize = 12.sp,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("politique de confidentialité")
        }
        pop()

        withStyle(
            style = SpanStyle(
                color = AppColors.TextSecondary,
                fontSize = 12.sp
            )
        ) {
            append(".")
        }
    }

    Text(
        text = annotatedText,
        textAlign = TextAlign.Center,
        lineHeight = 18.sp,
        modifier = modifier.clickable {
            annotatedText.getStringAnnotations(tag = "terms", start = 0, end = annotatedText.length)
                .firstOrNull()?.let { onTermsClick() }
            annotatedText.getStringAnnotations(tag = "privacy", start = 0, end = annotatedText.length)
                .firstOrNull()?.let { onPrivacyClick() }
        }
    )
}

@Composable
private fun LoginPrompt(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = AppColors.TextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        ) {
            append("Vous avez déjà un compte ? ")
        }

        pushStringAnnotation(tag = "login", annotation = "login")
        withStyle(
            style = SpanStyle(
                color = AppColors.Primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        ) {
            append("Se connecter")
        }
        pop()
    }

    Text(
        text = annotatedText,
        textAlign = TextAlign.Center,
        modifier = modifier.clickable { onLoginClick() }
    )
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
private fun CreateAccountScreenPreview() {
    CreateAccountScreen(
        uiState = AccountUiState(),
        onSignUpClick = {},
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onNavigateToVerifieCode = {},
        onModelChange = {}
    )
}