package com.neo.yandexpvz.screens.gift

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.neo.yandexpvz.GIFT_SCREEN
import com.neo.yandexpvz.HOME_SCREEN
import com.neo.yandexpvz.R
import com.neo.yandexpvz.common.components.InternetConnectionError
import com.neo.yandexpvz.internet.ConnectionState
import com.neo.yandexpvz.internet.connectivityState
import com.neo.yandexpvz.model.GiftCard
import com.neo.yandexpvz.ui.theme.BlueText
import com.neo.yandexpvz.ui.theme.OrangeDarkColor
import com.neo.yandexpvz.ui.theme.OrangeLightColor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun GiftScreen(
    openAndPopUp: (String,String) -> Unit,
    viewModel: GiftViewModel = hiltViewModel()
) {
    val networkConnectivity by connectivityState()
    LaunchedEffect(Unit) { viewModel.initialize() }
    val giftList = viewModel.giftList.observeAsState()
    val itemsList: List<GiftCard> = giftList.value!!

    if(networkConnectivity == ConnectionState.Unavailable) {
        InternetConnectionError { viewModel.initialize() }
    }else {
        if (viewModel.isLoading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                CircularProgressIndicator(color = MaterialTheme.colors.OrangeDarkColor)
            }
        } else {

            if (itemsList.isNotEmpty()) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(0.3f)) {
                            IconButton(
                                onClick = {
                                    openAndPopUp(HOME_SCREEN, GIFT_SCREEN)
                                },
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colors.OrangeLightColor,
                                        shape = CircleShape
                                    )
                                    .clip(CircleShape)

                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.back_icon),
                                    contentDescription = "Arrow Back"
                                )
                            }
                        }

                        Box(modifier = Modifier.weight(0.7f)) {
                            Text(
                                text = stringResource(id = R.string.gifts),
                                color = MaterialTheme.colors.OrangeDarkColor,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(itemsList) { item ->
                            GiftItem(item)
                        }
                    }

                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(0.3f)) {
                        IconButton(
                            onClick = {
                                openAndPopUp(HOME_SCREEN, GIFT_SCREEN)
                            },
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colors.OrangeLightColor,
                                    shape = CircleShape
                                )
                                .clip(CircleShape)

                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.back_icon),
                                contentDescription = "Arrow Back"
                            )
                        }
                    }

                    Box(modifier = Modifier.weight(0.7f)) {
                        Text(
                            text = stringResource(id = R.string.gifts),
                            color = MaterialTheme.colors.OrangeDarkColor,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Image(
                        painter = painterResource(R.drawable.no_gifts),
                        contentDescription = "No Internet",
                        modifier = Modifier.size(240.dp)
                    )

                }
            }
        }
    }
}


//
//@Composable
//fun GiftItem(item: GiftCard) {
//
//    Column(modifier = Modifier.padding(all = 8.dp)) {
//        Text(
//            text = item.product,
//            style = MaterialTheme.typography.subtitle1,
//            color = MaterialTheme.colors.TextColor,
//            modifier = Modifier.padding(top = 8.dp)
//        )
//        Spacer(modifier = Modifier.height(4.dp))
//        Text(
//            text = item.productValue,
//            style = MaterialTheme.typography.subtitle1,
//            color = MaterialTheme.colors.TextColor,
//            modifier = Modifier.padding(top = 8.dp)
//        )
//        Spacer(modifier = Modifier.height(4.dp))
//
//        Text(
//            text = item.redeemCode,
//            style = MaterialTheme.typography.subtitle1,
//            color = MaterialTheme.colors.TextColor,
//            modifier = Modifier.padding(top = 8.dp)
//        )
//        Spacer(modifier = Modifier.height(4.dp))
//        Text(
//            text = item.redeemStatus,
//            style = MaterialTheme.typography.subtitle1,
//            color = MaterialTheme.colors.TextColor,
//            modifier = Modifier.padding(top = 8.dp)
//        )
//        Spacer(modifier = Modifier.height(4.dp))
//
//        Text(
//            text = formatDate(item.date),
//            style = MaterialTheme.typography.subtitle1,
//            color = MaterialTheme.colors.TextColor,
//            modifier = Modifier.padding(top = 8.dp)
//        )
//
//
//    }
//}
//


@Composable
fun GiftItem(item: GiftCard) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colors.OrangeLightColor,
        modifier = Modifier
            .height(256.dp)
            .padding(10.dp),

    ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                   .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.wrapContentSize(),
                    color = Color(0xFFD1D5E1)
                ) {
                    Text(
                        text = item.redeemStatus,
                        fontSize =  12.sp,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text =item.product,
                    fontSize =  20.sp,
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))


                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Монеты : ")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = item.productValue,
                        fontSize =  16.sp,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.h3
                    )
                    Spacer(modifier = Modifier.width(2.dp))

                    Icon(
                        painter = painterResource(R.drawable.coin),
                        contentDescription = "gold-coins",
                        tint = Color.Unspecified
                    )


                }

                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Дата : ")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = formatDate(item.createdAt),
                        fontSize =  16.sp,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.h3
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.wrapContentSize(),
                    color = MaterialTheme.colors.BlueText
                ) {
                    Text(
                        text = "Код : " + ("${item.redeemCode}").uppercase(),
                        fontSize =  32.sp,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.h1,
                        color= Color.White,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
                    )
                }

            }


    }
}



private fun formatDate(date: String): String {
    return try {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val formattedDate = LocalDateTime.parse(date, dateFormatter)
        val res = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(formattedDate)

        res
    } catch (e: Exception) {
        date
    }
}


