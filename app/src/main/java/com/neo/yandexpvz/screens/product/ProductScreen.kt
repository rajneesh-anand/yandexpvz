package com.neo.yandexpvz.screens.product


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.neo.yandexpvz.HOME_SCREEN
import com.neo.yandexpvz.PRODUCT_ID
import com.neo.yandexpvz.PRODUCT_SCREEN
import com.neo.yandexpvz.R
import com.neo.yandexpvz.REDEEM_ID
import com.neo.yandexpvz.REDEEM_SCREEN
import com.neo.yandexpvz.common.components.DialogCancelButton
import com.neo.yandexpvz.common.components.DialogConfirmButton
import com.neo.yandexpvz.common.components.InternetConnectionError
import com.neo.yandexpvz.internet.ConnectionState
import com.neo.yandexpvz.internet.connectivityState
import com.neo.yandexpvz.ui.theme.BlueText
import com.neo.yandexpvz.ui.theme.OrangeDarkColor
import com.neo.yandexpvz.ui.theme.OrangeLightColor
import com.neo.yandexpvz.ui.theme.TextColor
import com.neo.yandexpvz.ui.theme.WhiteColor


@Composable
@ExperimentalMaterialApi
fun ProductScreen(
    restartApp: (String) -> Unit,
    openAndPopUp: (String,String) -> Unit,
    productId: String,
    viewModel: ProductViewModel = hiltViewModel()

) {
    val networkConnectivity by connectivityState()
    val balancedCoin = viewModel.balancedCoins.observeAsState()
    val productsInfo = viewModel.productInfo.observeAsState()
    LaunchedEffect(Unit) { viewModel.initialize(productId) }
    LaunchedEffect(viewModel.redeemMessage) {
        if (viewModel.redeemMessage == "success") {
            openAndPopUp("$REDEEM_SCREEN?$REDEEM_ID=${viewModel.redeemID}", "$PRODUCT_SCREEN?$PRODUCT_ID=${productId}")
        }
    }

    if(networkConnectivity == ConnectionState.Unavailable) {
        InternetConnectionError{ viewModel.initialize(productId) }
    }else {
        if (viewModel.isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.OrangeDarkColor)
        }else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0x8DFFFFFF))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        restartApp(HOME_SCREEN)
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

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(productsInfo.value?.image)
                    .crossfade(true)
                    .build(),
                contentDescription = "product image",
                placeholder = painterResource(R.drawable.product_placeholder),
                modifier = Modifier.size(256.dp),
                contentScale = ContentScale.Fit,
            )
            Spacer(modifier = Modifier.height(50.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colors.OrangeLightColor,
                        shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                    )
                    .padding(start = 15.dp, end = 15.dp, top = 30.dp, bottom = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {

                        Text(
                            text = productsInfo.value?.name.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(25.dp))

                        var seeMore by remember { mutableStateOf(true) }

                        Text(
                            text = productsInfo.value?.description.toString(),
                            fontSize = 16.sp,
                            color = MaterialTheme.colors.TextColor,
                            maxLines = if (seeMore) 5 else Int.MAX_VALUE,
                            overflow = TextOverflow.Ellipsis,

                        )
                        val textButton = if (seeMore) {
                            stringResource(id = R.string.see_more)
                        } else {
                            stringResource(id = R.string.see_less)
                        }
                        Text(
                            text = textButton,
                            style = MaterialTheme.typography.button,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.BlueText,
                            modifier = Modifier
                                .heightIn(20.dp)
                                .fillMaxWidth()
                                .padding(top = 15.dp)
                                .clickable {
                                    seeMore = !seeMore
                                }
                        )
                        Spacer(Modifier.height(40.dp))
                        if (productsInfo.value?.coinValue != null) {
                            if (balancedCoin.value!! >= productsInfo.value?.coinValue!!) {
                                RedeemCard { viewModel.onRedeemCoin() }
                            } else {
                                Text(
                                    text = stringResource(R.string.no_sufficient_coins),
                                    color = MaterialTheme.colors.OrangeDarkColor,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 24.sp,

                                    )
                            }
                        }
                    }

            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun RedeemCard(redeemCoins: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = {  showWarningDialog = true },
        shape = RoundedCornerShape(1.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.OrangeDarkColor),
    ) {
        Icon(
            painterResource(R.drawable.coin),
            contentDescription = "product coin",
            modifier = Modifier.size(ButtonDefaults.IconSize),
            tint = Color.Unspecified
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text= stringResource(id = R.string.redeem_now), color=MaterialTheme.colors.BlueText)
    }



    if (showWarningDialog) {
        AlertDialog(
//            title = { Text(stringResource(R.string.redeem)) },
            text = { Text(stringResource(R.string.redeem_dialog_message),  color = MaterialTheme.colors.BlueText, fontWeight = FontWeight.SemiBold) },
            dismissButton = { DialogCancelButton(R.string.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(R.string.yes) {
                    redeemCoins()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}
