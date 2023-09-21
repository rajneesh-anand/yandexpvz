package com.neo.yandexpvz.screens.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neo.yandexpvz.HOME_SCREEN
import com.neo.yandexpvz.INFO_SCREEN
import com.neo.yandexpvz.R
import com.neo.yandexpvz.components.DefaultBackArrow
import com.neo.yandexpvz.ui.theme.BlueText
import com.neo.yandexpvz.ui.theme.OrangeColor
import com.neo.yandexpvz.ui.theme.OrangeDarkColor
import com.neo.yandexpvz.ui.theme.OrangeLightColor

@Composable
@ExperimentalMaterialApi
fun InfoScreen(
    openAndPopUp: (String,String) -> Unit,
    modifier: Modifier = Modifier,
  ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(0.5f)) {
                DefaultBackArrow {
                    openAndPopUp(HOME_SCREEN, INFO_SCREEN)
                }
            }
//            Box(modifier = Modifier.weight(0.7f)) {
//                Text(
//                    text = stringResource(R.string.how_to_earn_coin),
//                    color = MaterialTheme.colors.TextColor,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
        }
        LazyColumn(
            modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
           item {
               
               Image(
                   painter = painterResource(id = R.drawable.step_1),
                   contentDescription = "step_1"
               )
               Spacer(modifier = Modifier.height(16.dp))
            Text(
              text = buildAnnotatedString {
                   append("Выберите ")
                   withStyle(style = SpanStyle(color = MaterialTheme.colors.OrangeDarkColor, fontWeight = FontWeight.Medium)) {
                       append("Пункт выдачи ")
                   }
                   append("при покупке товаров на ")

                   withStyle(style = SpanStyle(color = MaterialTheme.colors.OrangeDarkColor, fontWeight = FontWeight.Medium)) {
                       append("маркетплейсе Яндекс")
                   }
                 },
//                color = MaterialTheme.colors.BlueText,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
//                textAlign = TextAlign.Center,
            )
               Spacer(modifier = Modifier.height(16.dp))
               Image(painter = painterResource(R.drawable.yandex_1), contentDescription ="yandex one" )
               Spacer(modifier = Modifier.height(16.dp))
               Text(
                   text = buildAnnotatedString {
                       append("Выберите адрес пункта выдачи ")
                       withStyle(style = SpanStyle( color = MaterialTheme.colors.OrangeColor,fontWeight = FontWeight.Medium)) {
                           append(" Саратов, 2-й Магнитный проезд, д. 6 ")
                       }
                   },
//                   color = MaterialTheme.colors.BlueText,
                   fontWeight = FontWeight.Normal,
                   fontSize = 16.sp,
//                   textAlign = TextAlign.Center,
                   )
               Spacer(modifier = Modifier.height(16.dp))
               Image(painter = painterResource(R.drawable.yandex_2), contentDescription ="yandex two" )
           }
            item{
                Spacer(modifier = Modifier.height(24.dp))
            }
            item {
                Image(
                    painter = painterResource(id = R.drawable.step_2),
                    contentDescription = "step_2"
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = buildAnnotatedString {
                        append("Выберите товар в ")
                        withStyle(style = SpanStyle( color = MaterialTheme.colors.OrangeColor,fontWeight = FontWeight.Medium)) {
                            append(" Саратов, 2-й Магнитный проезд, д. 6 "
                            )
                        }
                    },
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
//                    textAlign = TextAlign.Center,
                    )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp)
                        .background(
                            color = MaterialTheme.colors.OrangeLightColor,
                            shape = RoundedCornerShape(8.dp)
                        ),
                ) {
                    Text(
                        text = "Вознаграждения будут зачислены на ваш счет ЯША в течение 24 часов.",
                        modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
//                        textAlign = TextAlign.Center,
                    )
                }


//                Text(
//                    text = "Вознаграждения будут зачислены на ваш счет ЯША в течение 24 часов.",
//                    fontWeight = FontWeight.Medium,
//                    fontSize = 16.sp,
//                    textAlign = TextAlign.Center,
//                )
                Spacer(modifier = Modifier.height(16.dp))
            }

//            item {
//
//                Image(
//                    painter = painterResource(id = R.drawable.step_3),
//                    contentDescription = "step_3"
//                )
//
//                Text(
//                    text = buildAnnotatedString {
//                        append("Выберите товар в ")
//                        withStyle(
//                            style = SpanStyle(
//                                fontWeight = FontWeight.Bold,
//                                color = MaterialTheme.colors.OrangeColor,
//                            )
//                        ) {
//                            append(" Саратов, 2-й Магнитный проезд, д. 6 "
//                            )
//                        }
//
//
//                    },
//                    color = MaterialTheme.colors.TextColor,
//                    fontSize = 24.sp,
//                    textAlign = TextAlign.Center,
//
//                    )
//
//            }
        }
    }

}