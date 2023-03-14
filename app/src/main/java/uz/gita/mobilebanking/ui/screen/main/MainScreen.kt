package uz.gita.mobilebanking.ui.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getScreenModel
import uz.gita.mobilebanking.R
import uz.gita.mobilebanking.data.model.response.CardResponse
import uz.gita.mobilebanking.ui.screen.main.MainContract.Intent
import uz.gita.mobilebanking.ui.screen.main.MainContract.UiState
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme

class MainScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<MainScreenModelImpl>()
        val uiState = viewModel.uiState.collectAsState().value
        MobileBankingTheme() {
            MainContent(uiState, viewModel::onEventDispatcher)
        }
    }
}

@Composable
fun MainContent(
    uiState: UiState,
    eventDispatcher: (Intent) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xfff4f5fa))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                Text(text = "My Accounts", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f, true))
                Icon(painter = painterResource(id = R.drawable.ic_notification), contentDescription = null)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            ) {

                LazyRow(content = {
                    items(2) {
                        PayCard(
                            CardResponse(
                                owner = "Vali Aliyev",
                                amount = 1800,
                                themeType = 1,
                                name = "CardName",
                                expiredYear = 2025,
                                expiredMonth = 12,
                                id = 1,
                                isVisible = true,
                                pan = "",
                            )
                        )
                    }
                })
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp)
            ) {

                Column(modifier = Modifier.weight(1f, true), horizontalAlignment = Alignment.CenterHorizontally) {
                    MenuItem(title = "Add Cart", icon = R.drawable.ic_wallet)
                }

                Column(modifier = Modifier.weight(1f, true), horizontalAlignment = Alignment.CenterHorizontally) {
                    MenuItem(title = "Pay", icon = R.drawable.pay)
                }

                Column(modifier = Modifier.weight(1f, true), horizontalAlignment = Alignment.CenterHorizontally) {
                    MenuItem(title = "Send", icon = R.drawable.send)
                }

                Column(modifier = Modifier.weight(1f, true), horizontalAlignment = Alignment.CenterHorizontally) {
                    MenuItem(title = "More", icon = R.drawable.more)
                }
            }

            Row(
                modifier = Modifier
                    .padding(top = 48.dp)
                    .fillMaxWidth()
                    .background(
                        Color(0xfff4f5fa)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    Card(
                        modifier = Modifier.fillMaxSize(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(topEnd = 32.dp, topStart = 32.dp, bottomEnd = 0.dp, bottomStart = 0.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Divider(
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .width(50.dp)
                                    .height(5.dp)
                                    .background(shape = RoundedCornerShape(20.dp), color = Color.Gray)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(top = 32.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)


                        ) {
                            Text(text = "Recent Transactions", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.weight(1f, true))
                            Icon(painter = painterResource(id = R.drawable.search), contentDescription = null)
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(top = 24.dp)
                                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_1), contentDescription = null, modifier = Modifier
                                        .height(40.dp)
                                        .width(40.dp)
                                )
                                Text(text = "Netflix", fontSize = 17.sp, modifier = Modifier.padding(start = 16.dp), fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.weight(1f, true))
                                Text(text = "-10$", fontWeight = FontWeight.Bold, fontSize = 17.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MenuItem(title: String, icon: Int) {
    Card(
        shape = RoundedCornerShape(50),

        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.padding(20.dp)
        )

    }
    Text(
        text = title, fontSize = 12.sp, modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(), textAlign = TextAlign.Center
    )
}


@Composable
fun PayCard(data: CardResponse) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp)
            .height(168.dp)
            .width(312.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF87B38))

    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.mastercard),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 20.dp, top = 20.dp)
                        .height(28.dp)
                        .width(42.dp)
                )
                Text(
                    text = "**** ${data.pan}",
                    color = Color.White,
                    fontSize = 17.sp,
                    modifier = Modifier
                        .padding(top = 22.dp, start = 16.dp)
                )
                Spacer(modifier = Modifier.weight(1f, true))
                Text(
                    text = "${data.expiredMonth}/${data.expiredYear}",
                    color = Color.White,
                    fontSize = 17.sp,
                    modifier = Modifier
                        .padding(top = 22.dp, end = 20.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 38.dp, start = 20.dp)
            ) {

                Text(
                    text = "Balance",
                    color = Color.White,
                    fontSize = 17.sp,
                )

                Text(
                    text = "$ 5 300.00",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }

}

@Preview
@Composable
fun PreviewMainContent() {
    MobileBankingTheme {
        MainContent(UiState()) {}
    }
}