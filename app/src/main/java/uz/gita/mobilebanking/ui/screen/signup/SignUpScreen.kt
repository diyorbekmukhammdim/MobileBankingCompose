package uz.gita.mobilebanking.ui.screen.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getScreenModel
import uz.gita.mobilebanking.R
import uz.gita.mobilebanking.ui.screen.signin.MyButton
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.theme.TextFieldColor

class SignUpScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<SignUpScreenModelImpl>()
        val uiState = viewModel.uiState.collectAsState().value
        MobileBankingTheme {
            SignUpScreenContent(uiState, viewModel::onEventDispatcher)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreenContent(
    uiState: SignUpContract.UiState,
    eventDispatcher: (SignUpContract.Intent) -> Unit
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
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    modifier = Modifier
                        .clickable { eventDispatcher(SignUpContract.Intent.Back) },
                    contentDescription = "Back"
                )
                Text(
                    text = "Sign up", fontSize = 20.sp, modifier = Modifier
                        .fillMaxWidth(), textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                for (i in 1..5) {
                    Text(
                        text = when (i) {
                            1 -> "First name"
                            2 -> "Last name"
                            3 -> "Date of birth"
                            4 -> "Phone number"
                            else -> "Password"
                        },
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = when (i) {
                            1 -> uiState.firstName
                            2 -> uiState.lastName
                            3 -> uiState.dateOfBirth
                            4 -> uiState.phone
                            else -> uiState.password
                        },
                        singleLine = true,
                        onValueChange = { str ->
                            eventDispatcher(when (i) {
                                1 -> SignUpContract.Intent.FirstNameEntered(str)
                                2 -> SignUpContract.Intent.LastNameEntered(str)
                                3 -> { if (str.length<=10) SignUpContract.Intent.DateOfBirthEntered(str) else SignUpContract.Intent.DateOfBirthEntered("") }
                                4 -> SignUpContract.Intent.PhoneEntered(str)
                                else -> SignUpContract.Intent.PasswordEntered(str)
                            })
                        },

                        colors =
                        TextFieldDefaults.textFieldColors(
                            containerColor = TextFieldColor,
                            cursorColor = Color.Black,
                            disabledLabelColor = Color.LightGray,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = if (i == 5) ImeAction.Done else ImeAction.Next,
                            keyboardType = if (i in 3..4) KeyboardType.Number else KeyboardType.Text
                        ),
                        modifier = Modifier
                            .height(56.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Column {
                    Text(
                        text = "Gender",
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    val state = remember { mutableStateOf(true) }
                    Row(Modifier.selectableGroup(), verticalAlignment = Alignment.CenterVertically)
                    {
                        RadioButton(
                            selected = state.value,
                            onClick = { state.value = true }
                        )
                        Text(
                            text = "male",
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(start = 2.dp, end = 10.dp)
                        )
                        RadioButton(
                            selected = !state.value,
                            onClick = { state.value = false }
                        )
                        Text(
                            text = "female",
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = uiState.errorMessage, color = Color(0xFFE30510),
                    fontSize = 16.sp, maxLines = 3)
                Spacer(modifier = Modifier.weight(1f))

                MyButton(text = "Register", uiState.buttonEnableState, uiState.progressAlpha) {
                        eventDispatcher(SignUpContract.Intent.SignUp)
                }
            }
        }
    }
}


@[Preview(showBackground = true) Composable]
fun LoginDefaultPreview() {
    MobileBankingTheme {
        SignUpScreenContent(SignUpContract.UiState()) {}
    }
}
