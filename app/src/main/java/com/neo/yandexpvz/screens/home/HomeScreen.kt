package com.neo.yandexpvz.screens.home


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.neo.yandexpvz.INFO_SCREEN
import com.neo.yandexpvz.R
import com.neo.yandexpvz.common.components.InternetConnectionError
import com.neo.yandexpvz.internet.ConnectionState
import com.neo.yandexpvz.internet.connectivityState
import com.neo.yandexpvz.model.Product
import com.neo.yandexpvz.ui.theme.BlueText
import com.neo.yandexpvz.ui.theme.OrangeDarkColor
import com.neo.yandexpvz.ui.theme.OrangeLightColor
import com.neo.yandexpvz.ui.theme.TextColor

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@Composable
fun HomeScreen(
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel()
    ) {

    LaunchedEffect(Unit) { viewModel.initialize() }
    val networkConnectivity by connectivityState()
    val balancedCoin = viewModel.balancedCoins.observeAsState()

    val productsList = viewModel.productList.observeAsState()
    val itemsList: List<Product> = productsList.value!!
    val homeUiState by viewModel.homeState.collectAsState()



    if(networkConnectivity == ConnectionState.Unavailable) {
        InternetConnectionError()
    }else{
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            UserInfo(
                homeUiState.name,
                homeUiState.email,
                homeUiState.mobile,
                homeUiState.image,
                balancedCoin.value.toString(),
                openScreen
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(modifier.fillMaxWidth()) {
                   items(itemsList) { item ->
                       ProductItem(
                           item,
                           balancedCoin.value!!,
                           viewModel = viewModel,
                           openScreen,
                           onProductClick = { })
                   }
               }
        }
    }
}

@Composable
private fun UserInfo(
    userName:String,
    userEmail:String,
    userMobile:String,
    userImage:String,
    balancedCoins:String,
    openScreen: (String) -> Unit,
) {
    val rainbowColorsBrush = remember {
        Brush.sweepGradient(
            listOf(
                Color(0xFFFFC107),
                Color(0xFFE91E63),
                Color(0xFFE57373),
                Color(0xFFFFB74D),
                Color(0xFFFFF176),
                Color(0xFFDDB027),
                Color(0xFFEE5524),
                Color(0xFFFFF176)
            )
        )
    }
    val borderWidth = 4.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.OrangeLightColor)
//            .border(2.dp, Color.Red, RoundedCornerShape(4.dp))
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,

    )
    {
        AsyncImage(
            model = userImage,
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(112.dp)
                .border(
                    BorderStroke(borderWidth, rainbowColorsBrush),
                    CircleShape
                )
                .padding(borderWidth)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .weight(weight = 3f, fill = false)
                .padding(start = 16.dp)
        ) {
            Text(
                text = userName,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                maxLines = 2,
                overflow = TextOverflow.Visible
            )

            Spacer(modifier = Modifier.height(2.dp))

            // User's email
            Text(
                text = userEmail,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    letterSpacing = (0.8).sp,
                    fontWeight = FontWeight.Medium
                ),
                maxLines = 2,
                overflow = TextOverflow.Visible
            )
            Spacer(Modifier.height(2.dp))

            if(userMobile.isNotEmpty()) {

                Text(
                    text = "+7 ".plus(" ").plus(userMobile.subSequence(0, 3)).plus(" ")
                        .plus(userMobile.subSequence(3, 6)).plus("-")
                        .plus(userMobile.subSequence(6, 10)),
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.DarkGray,
                        letterSpacing = (0.8).sp,
                        fontWeight = FontWeight.Medium
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.height(4.dp))
        }
    }

    Row ( modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.BlueText)
        .padding(horizontal = 16.dp, vertical = 0.dp),
        verticalAlignment = Alignment.CenterVertically,

    ){

        Icon(
            painter = painterResource(R.drawable.gold_coins),
            contentDescription = "gold-coins",
            tint = Color.Unspecified
        )
        Spacer(Modifier.width(4.dp))

        Text(
            text = balancedCoins,
            style = MaterialTheme.typography.h5,
            color = Color.White,

            )
        Spacer(
            Modifier
                .weight(1f)
                .fillMaxWidth())
        Text(
            text = stringResource(R.string.how_to_earn_coin),
            style = MaterialTheme.typography.h6,
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.clickable {
                openScreen(INFO_SCREEN)
            }

        )


    }


}



@Composable
fun ProductItem(
    item: Product,
    balancedCoin:Int,
    viewModel:HomeScreenViewModel,
    openScreen: (String) -> Unit,
    onProductClick: (Int) -> Unit,
  ) {



    Row( modifier = Modifier.fillMaxWidth()
        .padding(all= 8.dp),
       ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            placeholder = painterResource(R.drawable.product_placeholder),
            modifier = Modifier.size(128.dp),
            contentScale = ContentScale.FillBounds,
        )

//        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.padding(start = 8.dp)
                .clickable(onClick = { onProductClick(item.id) }),


        ) {

            if(item.inStock > 0) {
                TextButton(onClick = { viewModel.onProductClick(openScreen, item) }) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.TextColor,
                        modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
                    )
                }
            }else{
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.TextColor,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row ( modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                ){


                if(item.inStock > 0) {
                    Text(text = item.coinValue.toString())
                    Icon(
                        painter = painterResource(R.drawable.coin),
                        contentDescription = "gold-coins",
                        tint = Color.Unspecified
                    )
                    Spacer(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth())
                    if(balancedCoin > item.coinValue) {
                        TextButton( onClick = { viewModel.onProductClick(openScreen,item) }){
                            Icon(
                                painterResource(R.drawable.unlock),
                                contentDescription = "Favorite",
                                modifier = Modifier.size(ButtonDefaults.IconSize),
                                tint = Color.Unspecified
                            )
                            Spacer(modifier=Modifier.width(4.dp))
                            Text(text= stringResource(id = R.string.redeem_now), color = MaterialTheme.colors.BlueText)
                        }
                    }else {
                        Icon(
                            painterResource(R.drawable.lock),
                            contentDescription = "Favorite",
                            modifier = Modifier.size(ButtonDefaults.IconSize),
                            tint = Color.Unspecified
                        )
                    }
                }else{
                    Text(
                        text = stringResource(id = R.string.coming_soon),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colors.OrangeDarkColor,
                    )
                }
            }
        }
    }
}
