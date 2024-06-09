package com.khairililmi.speakgayo.ui.news.screen.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.khairililmi.speakgayo.ui.news.screen.ViewModelFactory
import com.khairililmi.speakgayo.ui.news.screen.di.Injection
import com.khairililmi.speakgayo.ui.news.screen.ui.common.UiState

@Composable
fun DetailScreen(
    id: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
) {
    val detailState = viewModel.detailState.collectAsState(initial = UiState.Loading).value

    LaunchedEffect(id) {
        viewModel.getDetailById(id)
    }

    when (detailState) {
        is UiState.Loading -> {
            // Loading UI
            Text(text = "Loading...", modifier = modifier.fillMaxSize())
        }
        is UiState.Success -> {
            val tourism = detailState.data
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = tourism.picture),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 16.dp)
                )
                Text(
                    text = tourism.name,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = tourism.location,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = tourism.description,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                // Display other details if needed...
            }
        }
        is UiState.Error -> {
            Text(text = "Error: ${detailState.message}", modifier = modifier.fillMaxSize())
        }
    }
}



