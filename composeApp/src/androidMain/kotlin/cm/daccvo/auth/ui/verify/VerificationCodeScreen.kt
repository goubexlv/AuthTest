package cm.daccvo.auth.ui.verify

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cm.daccvo.auth.ui.login.AppColors
import cm.daccvo.auth.ui.login.LoginScreenPhone
import cm.daccvo.auth.uiState.AccountUiState
import cm.horion.models.domain.LoginMethod
import kotlinx.coroutines.delay

@Composable
fun VerificationCodeScreen(
    uiState: AccountUiState,
    onCodeChange: (String) -> Unit,
    onBackClick: () -> Unit = {},
    onVerification: () -> Unit = {},
    onResendCode: () -> Unit = {},
    onNavigaterToResult: () -> Unit = {}
) {

    var isVerifyPressed by remember { mutableStateOf(false) }
    var resendCountdown by remember { mutableStateOf(60) }
    var canResend by remember { mutableStateOf(false) }
    var code by remember { mutableStateOf(List(6) { "" }) }
    val focusRequesters = remember { List(6) { FocusRequester() } }

    val isVerifying = uiState.isAuthenticating
    val error = uiState.authErrorMessage

    // Focus premier champ
    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }




    // Countdown timer pour resend
    LaunchedEffect(Unit) {
        while (resendCountdown > 0) {
            delay(1000)
            resendCountdown--
        }
        canResend = true
    }

    // Focus automatique sur le premier champ
    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }

//    LaunchedEffect(Unit) {
//        onResendCode()
//    }


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
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    enabled = !isVerifying,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBackIos,
                        contentDescription = "Back",
                        tint = AppColors.WhiteAlpha50,
                        modifier = Modifier.size(20.dp)
                    )
                }

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
                    .padding(top = 32.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon
                Surface(
                    modifier = Modifier
                        .size(80.dp)
                        .padding(bottom = 24.dp),
                    shape = RoundedCornerShape(40.dp),
                    color = AppColors.Primary.copy(alpha = 0.2f)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Message,
                            contentDescription = null,
                            tint = AppColors.Primary,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                Text(
                    text = "Entrez le code de vérification",
                    color = AppColors.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 36.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Nous avons envoyé un code à 6 chiffres à",
                    color = AppColors.TextSecondary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(top = 8.dp),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = if (uiState.phone.isEmpty()) uiState.phone else uiState.email,
                    color = AppColors.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Code Input Fields
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                modifier = Modifier.fillMaxWidth()
            ) {
                code.forEachIndexed { index, digit ->
                    CodeDigitField(
                        value = digit,
                        onValueChange = { newValue ->
                            if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                                val newCode = code.toMutableList()
                                newCode[index] = newValue
                                code = newCode

                                // envoyer le code complet au VM
                                onCodeChange(newCode.joinToString(""))

                                if (newValue.isNotEmpty() && index < 5) {
                                    focusRequesters[index + 1].requestFocus()
                                }
                            }
                        },
                        focusRequester = focusRequesters[index],
                        onBackspace = {
                            if (digit.isEmpty() && index > 0) {
                                focusRequesters[index - 1].requestFocus()
                            }
                        },
                        isError = error != null
                    )
                }


            }
            if(uiState.authErrorMessage == null) "" else uiState.authErrorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 4.dp)

                )
            }

            // Verify Button
            Button(
                onClick = {
                    //if (code.all { it.isNotEmpty() }) {
                        onVerification()
                    //}
                },
                enabled = !isVerifying && code.all { it.isNotEmpty() },
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
                if (isVerifying) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Suivant")
                }
            }



            // Reset pressed state
            LaunchedEffect(isVerifyPressed) {
                if (isVerifyPressed) {
                    delay(100)
                    isVerifyPressed = false
                }
            }



            // Resend Code
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Vous n'avez pas reçu le code? ",
                    color = AppColors.TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )

                if (canResend) {
                    Text(
                        text = "Resend",
                        color = AppColors.Primary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable {
                            // Resend code
                            resendCountdown = 60
                            canResend = false
                            code = List(6) { "" }
                            onCodeChange("")
                            onResendCode()
                            focusRequesters[0].requestFocus()
                        }
                    )
                } else {
                    Text(
                        text = "Renvoyer dans ${resendCountdown}s",
                        color = AppColors.TextSecondary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Help text
            Text(
                text = "Consultez vos SMS. Le code expire dans 10 minutes.",
                color = AppColors.TextSecondary,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .padding(bottom = 40.dp)
            )
        }
    }

    val context = LocalContext.current
    LaunchedEffect(
        key1 = uiState.authenticationSucceed,
        key2 = uiState.authErrorMessage,
        block = {
            if (uiState.authenticationSucceed) {
                onNavigaterToResult()
            }

            if (uiState.authErrorMessage != null) {
                //showToast(uiState.authErrorMessage!!)
                Toast.makeText(context,uiState.authErrorMessage!!,Toast.LENGTH_SHORT).show()
            }
        }
    )
}

@Composable
fun CodeDigitField(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    onBackspace: () -> Unit,
    isError: Boolean = false
) {
    var textFieldValue by remember(value) {
        mutableStateOf(TextFieldValue(value, TextRange(value.length)))
    }

    BasicTextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            if (newValue.text.isEmpty() && textFieldValue.text.isNotEmpty()) {
                // Backspace pressed
                onBackspace()
            }
            textFieldValue = newValue
            onValueChange(newValue.text)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        singleLine = true,
        modifier = Modifier
            .size(48.dp)
            .background(
                color = AppColors.InputBackground,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 2.dp,
                color = when {
                    isError -> MaterialTheme.colorScheme.error
                    value.isNotEmpty() -> AppColors.Primary
                    else -> AppColors.InputBorder
                },
                shape = RoundedCornerShape(12.dp)
            )
            .focusRequester(focusRequester),
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = "·",
                        color = AppColors.TextSecondary,
                        fontSize = 32.sp,
                        textAlign = TextAlign.Center
                    )
                }
                innerTextField()
            }
        },
        textStyle = LocalTextStyle.current.copy(
            color = AppColors.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    )
}

@Composable
@Preview
fun VerificationCodeScreenPreview() {
    VerificationCodeScreen(
        uiState = AccountUiState(),
        onCodeChange = {}
    )
}