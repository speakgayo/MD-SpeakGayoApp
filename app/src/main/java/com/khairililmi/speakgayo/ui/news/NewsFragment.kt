package com.khairililmi.speakgayo.ui.news
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.fragment.findNavController
import androidx.navigation.compose.rememberNavController
import com.khairililmi.speakgayo.ui.news.screen.ui.screen.detail.DetailScreen
import com.khairililmi.speakgayo.ui.news.screen.ui.screen.home.HomeScreen
import com.khairililmi.speakgayo.ui.news.screen.ui.theme.TourismAppTheme

@OptIn(ExperimentalMaterialApi::class)
class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                TourismAppTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        val navController = rememberNavController()
                        NavGraph(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun NavGraph(navController: androidx.navigation.NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            DetailScreen(id = id, navigateBack = { navController.popBackStack() })
        }
    }
}
@Composable
fun ProvideNavController(navController: NavController, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalNavController provides navController, content = content)
}

private val LocalNavController = staticCompositionLocalOf<NavController> {
    error("No NavController provided")
}
