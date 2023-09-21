package com.neo.yandexpvz.screens.welcome
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.neo.yandexpvz.R
import com.neo.yandexpvz.ui.theme.OrangeColor
import com.neo.yandexpvz.ui.theme.OrangeDarkColor
import com.neo.yandexpvz.utils.OnBoardingPage
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen(
     openAndPopUp: (String, String) -> Unit,
     viewModel: WelcomeViewModel = hiltViewModel()
) {
    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0)
    val loginButton by remember {
        derivedStateOf {
            pagerState.currentPage == pages.size - 1
        }
    }
    Box(modifier  = Modifier.fillMaxSize().padding(8.dp)) {
    HorizontalPager(
        pageCount = pages.size,
        state = pagerState,

        ) { pageIndex ->
        when (pages[pageIndex]) {
            OnBoardingPage.First -> PagerScreen(onBoardingPage = pages[pageIndex])
            OnBoardingPage.Second -> PagerScreen(onBoardingPage = pages[pageIndex])
            OnBoardingPage.Third -> PagerScreen(onBoardingPage = pages[pageIndex])
            else -> {}
        }
    }
    Row(
        Modifier
            .height(50.dp)
            .fillMaxWidth()
            .align(Alignment.BottomCenter),
        horizontalArrangement = Arrangement.Center,

        ) {
        repeat(pages.size) { iteration ->
            val color = if (pagerState.currentPage == iteration) MaterialTheme.colors.OrangeDarkColor else MaterialTheme.colors.OrangeDarkColor.copy(alpha = 0.5f)
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(12.dp)

            )
        }
    }
        Row(
            Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center,){
            AnimatedVisibility(
                visible = loginButton,
                modifier = Modifier
                    .padding(vertical = 16.dp),
                enter = slideInVertically { it } + fadeIn(),
                exit = slideOutVertically { it } + fadeOut(),
            ) {
                Button(

                    colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.OrangeColor,
                    contentColor = Color.White

                ),
                    onClick = {
                        scope.launch {
                            viewModel.saveOnBoardingState(completed = true)
                            viewModel.onAppStart(openAndPopUp)
                        }
                    },
                ) {
                    Text(
                        text = stringResource(id = R.string.further),
                        modifier = Modifier
                        .padding(horizontal = 32.dp, vertical = 6.dp),
                        fontSize = 18.sp
                    )
                }
            }
        }
}
}
@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f),
            painter = painterResource(id = onBoardingPage.welcomeImage),
            contentDescription = "Pager Image",
            contentScale = ContentScale.Fit
        )
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = "Pager Image"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(onBoardingPage.title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp),
            text = stringResource(onBoardingPage.description),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}
