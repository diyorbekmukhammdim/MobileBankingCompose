package uz.gita.mobilebanking.ui.screen.splash

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.theme.TextColor

object SplashScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<SplashScreenModelImpl>()
        SplashScreenContent()
    }
}

@Composable
fun SplashScreenContent() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = CenterVertically
        ) {
           /* Image(
                painter = painterResource(id = R.drawable.logo),
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(18.dp)),
                contentDescription = "Logo"
            )*/
            Text(
                text = "CUSTOM BANK", color = TextColor, fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
        }
    }
}


@Preview
@Composable
fun PreviewSplashScreen() {
    MobileBankingTheme {
        SplashScreenContent()
    }
}