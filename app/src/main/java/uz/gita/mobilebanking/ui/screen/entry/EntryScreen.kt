package uz.gita.mobilebanking.ui.screen.entry

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getScreenModel
import uz.gita.mobilebanking.R
import uz.gita.mobilebanking.ui.screen.entry.EntryContract.*
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme

class EntryScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<EntryScreenModelImpl>()
        val uiState = viewModel.uiState.collectAsState().value
        MobileBankingTheme {
            EntryScreenContent(uiState, viewModel::onEventDispatcher)
        }
    }
}


@Composable
fun EntryScreenContent(
    uiState: UiState,
    eventDispatcher: (Intent) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Image(
                    painter = painterResource(id = R.drawable.gita_bank_logo),
                    contentDescription = "logo", modifier = Modifier.height(38.dp)
                )
                Text(
                    text = "GITA BANK",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.weight(0.1f))
            Text(text = "Политика конфиденциалности сервиса",
                fontSize = 18.sp,
                modifier = Modifier.clickable {
                })
            Spacer(modifier = Modifier.weight(0.1f))
            Text(
                text = stringResource(id = R.string.privacy_policy_full).repeat(20),
                fontSize = 16.sp, modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .weight(4f),
                textAlign = TextAlign.Justify, overflow = TextOverflow.Clip
            )
            Spacer(modifier = Modifier.weight(0.3f))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = uiState.agreed,
                    onClick = { eventDispatcher(Intent.ChangeAgree) })
                Text(
                    text = stringResource(id = R.string.public_offer_1),
                    fontSize = 16.sp,
                    modifier = Modifier.clickable {
                        eventDispatcher(Intent.ChangeAgree)
                    }
                )
            }
            Spacer(modifier = Modifier.weight(0.15f))
            Button(
                onClick = { eventDispatcher(Intent.Login) },
                enabled = uiState.agreed,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFF3862F8),
                    disabledContainerColor = Color.LightGray
                ),
                shape = RoundedCornerShape(12.dp), modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Login")
            }
        }
    }
}

@[Preview Composable]
fun PreviewEntryScreen() {
    EntryScreenContent(UiState(agreed = false)) {}
}