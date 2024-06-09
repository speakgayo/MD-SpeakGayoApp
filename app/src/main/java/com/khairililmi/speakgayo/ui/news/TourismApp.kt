package com.khairililmi.speakgayo.ui.news

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.khairililmi.speakgayo.ui.news.screen.ui.screen.home.HomeScreen

@Composable
fun TourismApp(
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

//    Scaffold(scaffoldState = scaffoldState) { paddingValues ->
        // Memanggil HomeScreen dan meneruskan fungsi navigateToDetail
//        HomeScreen(
//            modifier = modifier.padding(paddingValues),
//            navigateToDetail = navigateToDetail
//        )
//    }
}
