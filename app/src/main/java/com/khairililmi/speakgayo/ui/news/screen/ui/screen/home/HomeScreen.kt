package com.khairililmi.speakgayo.ui.news.screen.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.khairililmi.speakgayo.ui.news.screen.ViewModelFactory
import com.khairililmi.speakgayo.ui.news.screen.di.Injection
import com.khairililmi.speakgayo.ui.news.screen.model.Tourism
import com.khairililmi.speakgayo.ui.news.screen.ui.common.UiState
import com.khairililmi.speakgayo.ui.news.screen.ui.components.PopularPlaceCard
import com.khairililmi.speakgayo.ui.news.screen.ui.components.RecommendationCard

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        val uiState = viewModel.uiState.collectAsState(initial = UiState.Loading).value
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllRewards()
            }
            is UiState.Success -> {
                HomeContent(
                    tourismList = uiState.data,
                    modifier = modifier,
                    navigateToDetail = { id ->
                        navController.navigate("detail/$id")
                    }
                )
            }
            is UiState.Error -> {
                // Handle error state
            }
        }
    }
}

@Composable
fun HomeContent(
    tourismList: List<Tourism>,
    modifier: Modifier,
    navigateToDetail: (Int) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(tourismList.size) { index ->
            PopularPlaceCard(
                tourism = tourismList[index],
                modifier = modifier,
                onClickCard = { navigateToDetail(tourismList[index].id) }
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "Recommendation",
        modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(tourismList.size) { index ->
            RecommendationCard(
                modifier = modifier,
                tourism = tourismList[index],
                onClickCard = { navigateToDetail(tourismList[index].id) }
            )
        }
    }
}
