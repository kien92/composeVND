package vn.mtk.compose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import vn.mtk.compose.presentation.ui.detail.DetailScreen
import vn.mtk.compose.presentation.viewmodel.MainViewModel
import androidx.navigation.compose.composable
import vn.mtk.compose.presentation.ui.theme.ComposeVPTheme
import vn.mtk.compose.presentation.ui.main.MainScreen

sealed class Screen(val route: String) {
    data object Main : Screen("main")
    data object Detail : Screen("detail")
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = koinViewModel<MainViewModel>()
            val navController = rememberNavController()
            ComposeVPTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(R.color.background)),
                ) {

                    NavHost(navController = navController, startDestination = Screen.Main.route) {
                        composable(Screen.Main.route) { MainScreen(viewModel, navController) }
                        composable(Screen.Detail.route) { DetailScreen(viewModel, navController) }
                    }
                }
            }
        }
    }
}

