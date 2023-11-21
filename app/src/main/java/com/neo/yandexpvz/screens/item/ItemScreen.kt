package com.neo.yandexpvz.screens.item

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.neo.yandexpvz.GIFT_SCREEN
import com.neo.yandexpvz.HOME_SCREEN
import com.neo.yandexpvz.R
import com.neo.yandexpvz.common.components.InternetConnectionError
import com.neo.yandexpvz.internet.ConnectionState
import com.neo.yandexpvz.internet.connectivityState
import com.neo.yandexpvz.model.GiftCard
import com.neo.yandexpvz.model.ItemCard
import com.neo.yandexpvz.screens.home.ProductItem
import com.neo.yandexpvz.ui.theme.BlueText
import com.neo.yandexpvz.ui.theme.LightGray
import com.neo.yandexpvz.ui.theme.OrangeDarkColor
import com.neo.yandexpvz.ui.theme.OrangeLightColor
import com.neo.yandexpvz.ui.theme.WarningColor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random


@Composable
fun ItemScreen(
    openAndPopUp: (String,String) -> Unit,
    viewModel: ItemViewModel = hiltViewModel()
) {

    val networkConnectivity by connectivityState()
    LaunchedEffect(Unit) { viewModel.initialize() }
    val itemList = viewModel.itemList.observeAsState()
    val itemsList: List<ItemCard> = itemList.value!!

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
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(itemsList) { item ->
                        ItemDetail(item)
                    }
                }
        }
    }
}


@Composable
fun ItemDetail(itm: ItemCard) {
    val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {

            Surface(
                shape = RoundedCornerShape(4.dp),
                color = MaterialTheme.colors.LightGray,
                modifier = Modifier
                    .fillMaxWidth()

            )
            {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(itm.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.product_placeholder),
                    modifier = Modifier.size(136.dp),
                    contentScale = ContentScale.Fit,
                )
            }



//            Surface(
//                shape = RoundedCornerShape(24.dp),
//                modifier = Modifier.wrapContentSize(),
//                color = Color(0xFFD1D5E1)
//            ) {
//                Text(
//                    text = itm.discount.toString(),
//                    fontSize =  12.sp,
//                    style = MaterialTheme.typography.h6,
//                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
//                )
//            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = itm.name,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                ),
            )

            Spacer(modifier = Modifier.height(12.dp))


           Row(modifier = Modifier
                .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                )
           {
                Text(
                    text = itm.salePrice.toString() + " ₽",
                    color= MaterialTheme.colors.BlueText,
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                    ),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = itm.price.toString() + " ₽",
                    color= Color.Gray,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                    ),
                    textDecoration = TextDecoration.LineThrough
                )
                Spacer(modifier = Modifier.width(12.dp))
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.wrapContentSize(),
                    color = MaterialTheme.colors.WarningColor
                ) {
                    Text(
                        text = " - ${itm.discount.toString()}" + " %",
                        color= Color.White,
                        style = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                        ),
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if(itm.marketPlace == "{\"label\":\"Yandex Market\",\"value\":\"Yandex Market\"}"){
                Row(
                    modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color.Red, RoundedCornerShape(8.dp))
                        .clickable { context.startActivity( Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(itm.link)))  },
                    verticalAlignment = Alignment.CenterVertically,
                  )
                {
                    Text(
                        text = "Купить на ",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        ),
                    modifier = Modifier.padding(start = 8.dp,end=4.dp, top=8.dp, bottom = 8.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.yandex),
                        contentDescription = "yandex marketplace",
                        modifier= Modifier.fillMaxWidth().height(24.dp).padding(end=8.dp),
                        contentScale = ContentScale.Fit,
                    )


                }
            }else{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, Color.Red, RoundedCornerShape(10.dp))
                        .clickable { context.startActivity( Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(itm.link)))  },
                    verticalAlignment = Alignment.CenterVertically,
                )
                {

                    Text(
                        text = "Купить на ",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.padding(start = 8.dp,end=4.dp, top=8.dp, bottom = 8.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ozon),
                        contentDescription = "ozone marketplace",
                        modifier= Modifier.fillMaxWidth().padding(end=8.dp),

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


