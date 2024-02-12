package com.sergiupuhalschi.presentation.ui.home.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sergiupuhalschi.presentation.R
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appColors
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appDimens
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appSpacing
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appTypography
import com.sergiupuhalschi.presentation.ui.common.viewmodel.utils.lifecycleAwareHiltViewModel
import com.sergiupuhalschi.presentation.ui.common.views.images.NetworkImage
import com.sergiupuhalschi.presentation.ui.common.views.indicators.roundedShimmer
import com.sergiupuhalschi.presentation.ui.common.views.screens.BottomTabScreen
import com.sergiupuhalschi.presentation.ui.common.views.texts.AutoSizeText
import com.sergiupuhalschi.presentation.ui.home.tab.models.HomeEventState
import com.sergiupuhalschi.presentation.ui.home.tab.models.HomeViewState
import com.sergiupuhalschi.presentation.ui.home.tab.models.PersonViewData

@Composable
fun HomeScreen(viewModel: HomeViewModel = lifecycleAwareHiltViewModel()) {
    val viewState = viewModel.viewState().collectAsState().value
    val eventState = viewModel.eventState().collectAsState().value

    BottomTabScreen {
        HomeLayout(
            viewState = viewState,
            isRefreshing = remember(eventState) { eventState is HomeEventState.Refreshing },
            onRefreshRequest = viewModel::refresh
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeLayout(
    viewState: HomeViewState,
    isRefreshing: Boolean,
    onRefreshRequest: () -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefreshRequest
    )

    Box(
        modifier = Modifier.pullRefresh(
            state = pullRefreshState,
            enabled = remember(viewState) { viewState !is HomeViewState.Loading }
        )
    ) {
        val contentPadding = PaddingValues(
            horizontal = MaterialTheme.appSpacing.SmallPadding,
            vertical = MaterialTheme.appSpacing.MediumPadding
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.appSpacing.SmallPadding)
        ) {
            item {
                TitleLayout()
            }

            when (viewState) {
                is HomeViewState.Data -> items(viewState.items) {
                    PersonItemLayout(data = it)
                }
                HomeViewState.Empty -> item {
                    EmptyLayout()
                }
                HomeViewState.Loading -> items(4) {
                    LoadingItemLayout()
                }
            }
        }

        PullRefreshIndicator(
            modifier = Modifier
                .padding(contentPadding)
                .align(Alignment.TopCenter),
            refreshing = isRefreshing,
            state = pullRefreshState
        )
    }
}

@Composable
private fun PersonItemLayout(data: PersonViewData) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        NetworkImage(
            modifier = Modifier.size(MaterialTheme.appDimens.LargePictureSize),
            url = data.picture
        )

        Spacer(modifier = Modifier.width(MaterialTheme.appSpacing.SmallPadding))

        Column(
            modifier = Modifier.weight(1f),
        ) {
            AutoSizeText(
                text = data.name,
                style = MaterialTheme.appTypography.titleLarge,
                color = MaterialTheme.appColors.primary,
                minFontSize = 13.sp
            )

            AutoSizeText(
                text = data.username,
                style = MaterialTheme.appTypography.bodySmall,
                color = MaterialTheme.appColors.tertiary,
                minFontSize = 10.sp
            )

            Spacer(modifier = Modifier.height(MaterialTheme.appSpacing.SmallPadding))

            AutoSizeText(
                text = stringResource(id = R.string.Home_NetWorthLabel),
                style = MaterialTheme.appTypography.bodySmall,
                color = MaterialTheme.appColors.primary
            )

            Spacer(modifier = Modifier.height(2.dp))

            AutoSizeText(
                text = data.netWorth,
                style = MaterialTheme.appTypography.titleMedium,
                color = MaterialTheme.appColors.primary,
                minFontSize = 13.sp,
            )
        }
    }
}

@Composable
private fun EmptyLayout() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.appSpacing.VeryLargePadding),
        contentAlignment = Alignment.Center
    ) {
        AutoSizeText(
            text = stringResource(id = R.string.Home_Empty),
            color = MaterialTheme.appColors.primary,
            style = MaterialTheme.appTypography.titleSmall,
            maxLines = 2,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LoadingItemLayout() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(MaterialTheme.appDimens.LargePictureSize)
                .roundedShimmer(shapeRadius = MaterialTheme.appDimens.LargeImageCornerRadius)
        )

        Spacer(modifier = Modifier.width(MaterialTheme.appSpacing.SmallPadding))

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Box(
                modifier = Modifier
                    .width(168.dp)
                    .height(20.dp)
                    .roundedShimmer()
            )

            Spacer(modifier = Modifier.height(2.dp))

            Box(
                modifier = Modifier
                    .width(98.dp)
                    .height(16.dp)
                    .roundedShimmer()
            )

            Spacer(modifier = Modifier.height(MaterialTheme.appSpacing.SmallPadding))

            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(18.dp)
                    .roundedShimmer()
            )

            Spacer(modifier = Modifier.height(2.dp))

            Box(
                modifier = Modifier
                    .width(156.dp)
                    .height(20.dp)
                    .roundedShimmer()
            )
        }
    }
}

@Composable
private fun TitleLayout() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.appSpacing.MediumPadding),
        text = stringResource(id = R.string.Home_Title),
        color = MaterialTheme.appColors.primary,
        style = MaterialTheme.appTypography.headlineLarge,
        maxLines = 2
    )
}

@Composable
@Preview(showBackground = true)
private fun HomePreview(
    @PreviewParameter(HomePreviewProvider::class) viewState: HomeViewState
) {
    HomeLayout(
        viewState = viewState,
        isRefreshing = false,
        onRefreshRequest = {}
    )
}

class HomePreviewProvider : PreviewParameterProvider<HomeViewState> {

    override val values: Sequence<HomeViewState>
        get() = sequenceOf(
            HomeViewState.Data(
                items = listOf(
                    PersonViewData(
                        id = "1",
                        name = "Elon Musk",
                        username = "@emusk",
                        picture = "",
                        netWorth = "$202,000,000,000"
                    ),
                    PersonViewData(
                        id = "2",
                        name = "Bernard Arnault",
                        username = "@barnault",
                        picture = "",
                        netWorth = "$183,000,000,000"
                    ),
                    PersonViewData(
                        id = "3",
                        name = "Jeff Bezos",
                        username = "@jbezos",
                        picture = "",
                        netWorth = "$180,000,000,000"
                    ),
                    PersonViewData(
                        id = "4",
                        name = "Bill Gates",
                        username = "@bgates",
                        picture = "",
                        netWorth = "$144,000,000,000"
                    ),
                    PersonViewData(
                        id = "5",
                        name = "Mark Zuckerberg",
                        username = "@zuck",
                        picture = "",
                        netWorth = "$141,000,000,000"
                    )
                )
            ),
            HomeViewState.Empty,
            HomeViewState.Loading
        )
}