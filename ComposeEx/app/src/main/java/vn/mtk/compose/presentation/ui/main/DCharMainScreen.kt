package vn.mtk.compose.presentation.ui.main

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import vn.mtk.compose.R
import vn.mtk.compose.Screen
import vn.mtk.compose.domain.model.ErrorType
import vn.mtk.compose.presentation.model.DCharPriceUi
import vn.mtk.compose.presentation.ui.main.component.DCharPriceHeader
import vn.mtk.compose.presentation.ui.main.component.DCharPriceRefreshBox
import vn.mtk.compose.presentation.viewmodel.MainViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen(viewModel: MainViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    LaunchedEffect(Unit) {
        if (!viewModel.isLoaded) {
            viewModel.loadInitial()
        }
    }

    val errorState = state as? DCharMainState.Error

    LaunchedEffect(errorState) {
        errorState?.let {
            val msgId = when (it.errorType) {
                ErrorType.SERVER_ERROR -> R.string.error_server
                ErrorType.NO_INTERNET -> R.string.error_no_internet
                ErrorType.TIMEOUT -> R.string.error_timeout
                ErrorType.UNKNOWN -> R.string.error_unknown
            }
            Toast.makeText(context, context.getString(msgId), Toast.LENGTH_SHORT).show()
        }
    }

    val lastSuccessData = remember { mutableStateOf<List<DCharPriceUi>>(emptyList()) }
    if (state is DCharMainState.Success) {
        lastSuccessData.value = (state as DCharMainState.Success).data
    }
    val data = lastSuccessData.value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.ma_cp)) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
                    containerColor = colorResource(R.color.dart_background),
                    titleContentColor = Color.Black
                )
            )
        },
        containerColor = colorResource(R.color.dart_background),
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DCharPriceHeader()
            if (state is DCharMainState.Loading) CircularProgressIndicator(
                modifier = Modifier.padding(
                    16.dp
                )
            )
            else {
                DCharPriceRefreshBox(data = data,
                    isRefreshing = isRefreshing,
                    onRefresh = { viewModel.refreshData() },
                    onLoadMore = { lastVisibleIndex, totalItems ->
                        viewModel.loadMoreIfNeeded(lastVisibleIndex, totalItems)
                    },
                    onItemClick = {
                        viewModel.selectItem(it)
                        navController.navigate(Screen.Detail.route)
                    })
            }
        }

    }
}

