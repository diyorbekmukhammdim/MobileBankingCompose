package uz.gita.mobilebanking.ui.screen.verify

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getScreenModel
import com.mukesh.OTP_VIEW_TYPE_BORDER
import com.mukesh.OtpView
import uz.gita.mobilebanking.R
import uz.gita.mobilebanking.data.model.common.SignType
import uz.gita.mobilebanking.ui.screen.verify.VerifyContract.Intent
import uz.gita.mobilebanking.ui.screen.verify.VerifyContract.UiState
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.theme.TextFieldColor
import uz.gita.mobilebanking.ui.theme.buttonColor

class VerifyScreen(private val type: SignType, private val phone: String) : AndroidScreen() {

    @Composable
    override fun Content() {

        val viewModel = getScreenModel<VerifyScreenModelImpl>()
        viewModel.onEventDispatcher(Intent.SetPhone(phone))
        viewModel.onEventDispatcher(Intent.SetVerifyType(type))

        val uiState = viewModel.uiState.collectAsState().value
        MobileBankingTheme {
            VerifyUi(uiState, viewModel::onEventDispatcher)
        }
    }
}

@Composable
fun VerifyUi(
    uiState: UiState,
    eventDispatcher: (Intent) -> Unit,
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
                        .clickable { eventDispatcher(Intent.Back) },
                    contentDescription = "Back"
                )
                Text(
                    text = "Потдвердите", fontSize = 20.sp, modifier = Modifier
                        .fillMaxWidth(), textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = "Код отправлен на номер ${uiState.phone}",
                modifier = Modifier
                    .width(176.dp)//
                    .height(44.dp),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(40.dp))
            OtpView(
                charColor = if (uiState.isError) {
                    Color(0xFFFF0000)
                } else
                    Color(0xFF525252),
                otpText = uiState.code,
                onOtpTextChange = {
                    eventDispatcher(Intent.CodeEntered(it))
                    eventDispatcher(Intent.ButtonState(it.length == 6))
                },
                type = OTP_VIEW_TYPE_BORDER,
                containerSize = 45.dp,
                otpCount = 6,

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                ),
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text =
                if (uiState.resend)
                    "Resend Code"
                else
                    "Запросить через 00:${if(uiState.timer/10<1) 0 
                    else ""}${uiState.timer}",
                Modifier.clickable { eventDispatcher(Intent.ResendCode) }
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = uiState.errorMessage, color = Color(0xFFE30510),
                fontSize = 16.sp,
                modifier = Modifier.clickable {
                })

            Spacer(modifier = Modifier.weight(1f))

            Button(
                enabled = uiState.buttonEnableState,
                onClick = {
                    eventDispatcher(Intent.Verify)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor,
                    disabledContainerColor = TextFieldColor
                ),

                shape = RoundedCornerShape(6.dp), modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Verify")
            }
        }
    }
}

@Preview
@Composable
fun VerifyUiPreview() {
    MobileBankingTheme {
        VerifyUi(UiState()) {}
    }
}