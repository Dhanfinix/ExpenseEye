package com.dhandev.rekapin.presentation.landing

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.dhandev.rekapin.R
import com.dhandev.rekapin.navigation.NavigationDestination
import com.dhandev.rekapin.presentation.ui.component.LandingBottomSheet
import com.dhandev.rekapin.presentation.ui.component.StepProgressIndicator
import com.dhandev.rekapin.ui.theme.BlueMain
import com.dhandev.rekapin.ui.theme.BlueSecondary
import com.dhandev.rekapin.ui.theme.raleway
import com.dhandev.rekapin.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

object LandingDestination : NavigationDestination {
    override val route: String = "landing"
    override val titleRes: Int = R.string.landing
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(
    modifier: Modifier = Modifier,
    autoSlideDuration: Long,
    viewModel: MainViewModel = koinViewModel(),
    navigateToHome: () -> Unit
) {
    val contentData = Constants.LandingPageItems
    val itemsCount = contentData.size
    val gradient = Brush.linearGradient(
        colors = listOf(BlueSecondary, MaterialTheme.colorScheme.background),
        start = Offset(100f, 0f),
        end = Offset(0f, 200f)
    )
    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        itemsCount
    }
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    val currentPage = pagerState.currentPage

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradient)
    ) {
        LaunchedEffect(currentPage) {
            delay(autoSlideDuration)
            coroutineScope.launch {
                pagerState.animateScrollToPage(
                    if (currentPage + 1 == itemsCount) 0
                    else currentPage + 1
                )
            }
        }

        Box(
            modifier = modifier.fillMaxSize(),
        ) {
            HorizontalPager(
                state = pagerState,
                pageSpacing = 0.dp,
                userScrollEnabled = true,
                reverseLayout = false,
                contentPadding = PaddingValues(0.dp),
                beyondBoundsPageCount = 0,
                pageSize = PageSize.Fill,
                key = null,
                pageContent = {
                    val composition =
                        rememberLottieComposition(
                            LottieCompositionSpec.RawRes(
                                contentData[currentPage].lottieRaw
                            )
                        )
                    val progress by animateLottieCompositionAsState(
                        composition = composition.value,
                        iterations = LottieConstants.IterateForever
                    )
                    LottieAnimation(
                        composition = composition.value,
                        progress = { progress },
                        modifier = Modifier
                            .padding(30.dp)
                            .fillMaxSize(),
                    )
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom
        ) {
            StepProgressIndicator(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                totalDots = itemsCount,
                selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage,
                dotSize = 8.dp
            )
            Column(
                modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp)
            ) {
                Text(text = stringResource(id = contentData[currentPage].title), style = raleway(20, FontWeight.Bold))
                Text(text = stringResource(id = contentData[currentPage].subtitle), style = raleway(16, FontWeight.Normal))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = if (currentPage == 0) R.string.landing_skip else R.string.landing_back),
                    style = raleway(16, FontWeight.Bold),
                    color = BlueMain,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .clickable {
                            scope.launch {
                                pagerState.animateScrollToPage(if (currentPage == 0) itemsCount - 1 else currentPage - 1)
                            }
                        }
                )

                Button(
                    onClick = {
                        if (currentPage == itemsCount - 1) showBottomSheet = true else
                            scope.launch {
                                pagerState.animateScrollToPage(currentPage + 1)
                            }
                    },
                    modifier = Modifier
                        .padding(12.dp)
                ) {
                    Text(text = stringResource(id = if (currentPage == itemsCount - 1) R.string.landing_start_btn else R.string.landing_next))
                }
            }

        }
        if (showBottomSheet) {
            LandingBottomSheet(
                sheetState = sheetState,
                scope = scope,
                onProceed = {
                    viewModel.saveProfileData(it)
                    navigateToHome.invoke()
                },
                isShown = { showBottomSheet = it })
        }
    }


}