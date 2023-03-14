package uz.gita.mobilebanking.ui.screen.signin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getScreenModel
import uz.gita.mobilebanking.R
import uz.gita.mobilebanking.ui.theme.*

class SignInScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<SignInScreenModelImpl>()
        val uiState = viewModel.uiState.collectAsState().value
        MobileBankingTheme {
            SignInScreenContent(uiState, viewModel::onEventDispatcher)
        }
    }
}

@[Composable OptIn(ExperimentalMaterial3Api::class)]
fun SignInScreenContent(
    uiState: SignInContract.UiState,
    eventDispatcher: (SignInContract.Intent) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    modifier = Modifier
                        .clickable { eventDispatcher(SignInContract.Intent.Back) },
                    contentDescription = "Back"
                )
                Text(
                    text = "Sign In", style = MaterialTheme.typography.titleLarge, modifier = Modifier
                        .fillMaxWidth(), textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp)
            ) {
                Text(
                    text = "Phone number",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = uiState.phone,
                    onValueChange = {
                        if (it.length <= 13) {
                            eventDispatcher(SignInContract.Intent.PhoneEntered(it))
                        }
                    },

                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = TextFieldColor,
                        cursorColor = Color.Black,
                        disabledLabelColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),

                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Password",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = uiState.password,
                    onValueChange = {
                        eventDispatcher(SignInContract.Intent.PasswordEntered(it))
                    },
                    singleLine = true,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_eye),
                            modifier = Modifier
                                .padding(14.dp)
                                .clickable {
                                    eventDispatcher(SignInContract.Intent.ShowPassword)
                                }, contentDescription = "show password"
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
//                    visualTransformation = if (uiState.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors =
                    TextFieldDefaults.textFieldColors(
                        containerColor = TextFieldColor,
                        cursorColor = Color.Black,
                        disabledLabelColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = uiState.errorMessage, color = Color(0xFFE30510),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.clickable {
                    })
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Sign Up", color = buttonColor,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .clickable {
                            eventDispatcher(SignInContract.Intent.CreateAccount)
                        })

                Spacer(modifier = Modifier.weight(1f))
                Row() {


//theme
                    val radioOptions = listOf(Theme.LITE, Theme.DARK, Theme.TERTIARY)
                    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[2]) }
                    Column(

                        modifier = Modifier,
                        verticalArrangement = Arrangement.Center,

                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Column {
                            radioOptions.forEach { text ->
                                Row(
                                    Modifier
                                        .selectable(
                                            selected = (text == selectedOption),
                                            onClick = { onOptionSelected(text) }
                                        )
                                        .padding(horizontal = 16.dp)
                                ) {
                                    val context = LocalContext.current

                                    RadioButton(
                                        selected = (text == selectedOption),
                                        modifier = Modifier.padding(all = Dp(value = 8F)),
                                        onClick = {
                                            onOptionSelected(text)
                                            ApplicationTheme.theme.value = text

                                        }
                                    )
                                    Text(
                                        text = text.toString(),
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                    //theme
                    val radioOptionsT = listOf(TYPOGRAPHY.LARGE, TYPOGRAPHY.MEDIUM, TYPOGRAPHY.SMALL)
                    val (selectedOptionT, onOptionSelectedT) = remember {
                        mutableStateOf(
                            radioOptionsT[2]
                        )
                    }
                    Column(

                        modifier = Modifier,
                        verticalArrangement = Arrangement.Center,

                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Column {
                            radioOptionsT.forEach { text ->
                                Row(
                                    Modifier
                                        .selectable(
                                            selected = (text == selectedOptionT),
                                            onClick = { onOptionSelectedT(text) }
                                        )
                                        .padding(horizontal = 8.dp)
                                ) {
                                    val context = LocalContext.current

                                    RadioButton(
                                        selected = (text == selectedOptionT),
                                        modifier = Modifier.padding(all = Dp(value = 8F)),
                                        onClick = {
                                            onOptionSelectedT(text)
                                            ApplicationTheme.typography.value = text

                                        }
                                    )
                                    Text(
                                        text = text.toString(),
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                MyButton(text = "Login", uiState.buttonEnableState, uiState.progressAlpha) { eventDispatcher(SignInContract.Intent.Login) }
            }
        }
    }
}

@[Composable Preview]
fun LoginUiPreview() {
    SignInScreenContent(SignInContract.UiState()) {}
}

@Composable
fun MyButton(
    text: String,
    buttonState: Boolean,
    progressAlpha: Float,
    block: () -> Unit
) {
    Button(
        onClick = { block.invoke() },
        enabled = buttonState,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = buttonColor, disabledContainerColor = TextFieldColor
        ),
        shape = RoundedCornerShape(12.dp), modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
            Text(modifier = Modifier.fillMaxWidth(), text = text, textAlign = TextAlign.Center)
            CircularProgressIndicator(color = Color.White, modifier = Modifier
                .padding(4.dp)
                .height(30.dp)
                .alpha(progressAlpha)
                .align(Center))
        }
    }
}