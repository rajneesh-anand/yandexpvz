package com.neo.yandexpvz.screens.redeem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neo.yandexpvz.HOME_SCREEN
import com.neo.yandexpvz.R
import com.neo.yandexpvz.REDEEM_SCREEN
import com.neo.yandexpvz.components.DefaultBackArrow
import com.neo.yandexpvz.ui.theme.BlueText
import com.neo.yandexpvz.ui.theme.OrangeDarkColor


@Composable
@ExperimentalMaterialApi
fun RedeemScreen(
    openAndPopUp: (String, String) -> Unit,
    redeemId: String,
    modifier: Modifier = Modifier,

) {


    Column(
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(12.dp)
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(0.5f)) {
                DefaultBackArrow {
                    openAndPopUp(HOME_SCREEN, REDEEM_SCREEN)
                }
            }

        }

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(R.drawable.hurray),
            contentDescription = "hurray",
            modifier = Modifier.size(240.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Surface(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.wrapContentSize(),
            color = MaterialTheme.colors.BlueText
        ) {
            Text(
                text = "Код : " +  ("$redeemId").uppercase(),
                fontSize =  32.sp,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.h1,
                color= Color.White,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.redeem_msg_one))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.contact_us), color = MaterialTheme.colors.OrangeDarkColor)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.coin),
                contentDescription = "gold-coins",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.address),
                fontSize =  20.sp,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.h3
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.shop_address),
            fontSize =  20.sp,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.h3
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.mobile),
                contentDescription = "gold-coins",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.conatct_number),
                fontSize =  20.sp,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.h3
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.mobile_number),
            fontSize =  20.sp,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.h3
        )

    }





}
