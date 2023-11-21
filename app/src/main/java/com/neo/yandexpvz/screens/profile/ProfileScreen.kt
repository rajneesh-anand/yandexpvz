package com.neo.yandexpvz.screens.profile
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.neo.yandexpvz.R
import com.neo.yandexpvz.screens.home.DrawerNavItem
import com.neo.yandexpvz.ui.theme.OrangeDarkColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
@ExperimentalMaterialApi
fun ProfileScreen(
    restartApp: ( String) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()

) {

    val navItemList = listOf(
        DrawerNavItem.ProfileInfoNav,
        DrawerNavItem.GiftCardNav,
        DrawerNavItem.SupportNav,
        DrawerNavItem.FeedbackNav,
        DrawerNavItem.SignOutNav,
    )
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

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 4.dp, bottom = 20.dp),
    ) {

        stickyHeader {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = R.string.profile),
                    color = MaterialTheme.colors.OrangeDarkColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                AsyncImage(
                    model = viewModel.userImage,
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(112.dp)
                        .border(
                            BorderStroke(4.dp, rainbowColorsBrush),
                            CircleShape
                        )
                        .padding(4.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = viewModel.name,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = viewModel.email,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                if(viewModel.mobile.isNotEmpty()) {

                    Text(
                        text = "+7 ".plus(" ").plus(viewModel.mobile.subSequence(0, 3)).plus(" ")
                            .plus(viewModel.mobile.subSequence(3, 6)).plus("-")
                            .plus(viewModel.mobile.subSequence(6, 10)),
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
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        items(navItemList) { item ->
            Row ( modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .clickable {
                    viewModel.onNavItemClick(restartApp, item.route)
                 },
                verticalAlignment = Alignment.CenterVertically,
                ){
                Icon(
                    painter = painterResource(item.icon),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = item.tittle,
                    color = Color.Gray,
                    fontSize = 20.sp,
                )

            }
            Divider (
                color = Color.LightGray,
                modifier = Modifier
                    .height(0.8.dp)
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

        }
    }
}


