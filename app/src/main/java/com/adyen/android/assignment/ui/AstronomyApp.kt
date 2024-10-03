package com.adyen.android.assignment.ui

import AstronomyDetailScreen
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.api.model.DayAdapter
import com.adyen.android.assignment.network.ConnectionState
import com.adyen.android.assignment.network.ConnectivityObserver
import com.adyen.android.assignment.ui.list.AstronomyListScreen
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.net.URLEncoder

/**
 * enum values that represent the screens in the app
 */
enum class AstronomyScreen(@StringRes val title: Int) {
    List(title = R.string.app_name),
    Details(title = R.string.details),
}

@Composable
fun AstronomyApp(
    viewModel: AstronomyViewModel,
    navController: NavHostController = rememberNavController()
) {

    val uiState by viewModel.uiState.collectAsState()
    val showDialog = remember { mutableStateOf(false) }

    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).add(DayAdapter()).build()
    val adapter = moshi.adapter(AstronomyPicture::class.java)

    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route

    // ConnectivityObserver instance
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val connectivityObserver = remember { ConnectivityObserver(context) }

    // Previous connection state to track changes
    val previousConnectionState = remember { mutableStateOf<ConnectionState?>(null) }

    // Listen for connectivity changes
    LaunchedEffect(Unit) {
        connectivityObserver.observeConnectivity().collect { state ->
            if (state == ConnectionState.Unavailable && previousConnectionState.value != ConnectionState.Unavailable) {
                // Show "No internet connection" Snackbar
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "No internet connection",
                        duration = SnackbarDuration.Short // Show for 5 seconds
                    )
                }
            } else if (state == ConnectionState.Available && previousConnectionState.value == ConnectionState.Unavailable) {
                // Show "Connection restored" Snackbar
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Connection restored",
                        duration = SnackbarDuration.Short // Show for 5 seconds
                    )
                }
            }

            // Update previous connection state to current state
            previousConnectionState.value = state
        }
    }

    Scaffold(
        topBar = {
            AstronomyAppBar(
                currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = AstronomyScreen.List.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(if (currentScreen?.contains("Details") == true) PaddingValues(0.dp) else innerPadding) // No padding for detail screen
        ) {

            composable(route = AstronomyScreen.List.name) {
                AstronomyListScreen(
                    state = uiState,
                    currentSelection = viewModel.currentSelection(),
                    showDialog = showDialog.value,
                    onItemClicked = { astronomyItem ->
                        val serializedItem =
                            URLEncoder.encode(adapter.toJson(astronomyItem), "UTF-8")
                        navController.navigate("${AstronomyScreen.Details.name}/$serializedItem")
                    },
                    onFabClicked = { showDialog.value = true },
                    onDismissDialog = { showDialog.value = false },
                    refreshCall = {
                        viewModel.fetchList()
                    },
                    onSelection = {
                        viewModel.sort(it)
                        showDialog.value = false
                    }
                )
            }

            // Astronomy Details Screen
            composable(
                route = "${AstronomyScreen.Details.name}/{astronomyItem}",
                arguments = listOf(navArgument("astronomyItem") { type = NavType.StringType })
            ) { backStackEntry ->
                val serializedItem = backStackEntry.arguments?.getString("astronomyItem") ?: ""
                val decodedItem = URLDecoder.decode(serializedItem, "UTF-8")
                val astronomyItem = adapter.fromJson(decodedItem)

                astronomyItem?.let {
                    AstronomyDetailScreen(it)
                }
            }
        }
    }
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstronomyAppBar(
    currentScreen: String?,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
) {

    val isDetailScreen = currentScreen?.contains("Details") == true

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = if (isDetailScreen) Color.Transparent else MaterialTheme.colorScheme.primary, // Transparent on details screen
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("Our Universe", color = Color.White)
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = Color.White,
                        contentDescription = "back"
                    )
                }
            }
        }
    )
}