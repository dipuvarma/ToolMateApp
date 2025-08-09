package com.dipuguide.toolmate.presentation.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dipuguide.toolmate.R
import com.dipuguide.toolmate.domain.model.ToolCardItemModel
import com.dipuguide.toolmate.presentation.navigation.BarcodeScannerRoute
import com.dipuguide.toolmate.presentation.navigation.Bookmark
import com.dipuguide.toolmate.presentation.navigation.DocumentScannerRoute
import com.dipuguide.toolmate.presentation.navigation.Home
import com.dipuguide.toolmate.presentation.navigation.ImageLabelingRoute
import com.dipuguide.toolmate.presentation.navigation.LocalNavController
import com.dipuguide.toolmate.presentation.navigation.ObjectDetectionRoute
import com.dipuguide.toolmate.presentation.navigation.Setting
import com.dipuguide.toolmate.presentation.navigation.SpeechToTextRoute
import com.dipuguide.toolmate.presentation.navigation.TextRecognitionRoute
import com.dipuguide.toolmate.presentation.screens.main.bookmark.BookmarkScreen
import com.dipuguide.toolmate.presentation.screens.main.home.HomeScreen
import com.dipuguide.toolmate.presentation.screens.main.setting.SettingScreen


@Composable
fun MainScreen() {
    val tabNavController = rememberNavController()
    val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val bottomBarDestinations =
        listOf(
            BottomBarDestination.HomeBottomBar,
            BottomBarDestination.BookmarksBottomBar,
            BottomBarDestination.SettingsBottomBar,
        )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
            ) {
                bottomBarDestinations.forEach { destination ->
                    val isSelected = currentDestination == destination.route
                    NavigationBarItem(
                        selected = isSelected,
                        icon = {
                            Icon(
                                imageVector = if (isSelected) destination.selectedIcon else destination.unselectedIcon,
                                contentDescription = destination.contentDescription,
                            )
                        },
                        onClick = {
                            if (!isSelected) {
                                tabNavController.navigate(destination.route) {
                                    popUpTo(tabNavController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        label = {
                            Text(
                                text = destination.label,
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = tabNavController,
            startDestination = Home.route,
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
        ) {

            composable(Home.route) {
                HomeScreen()
            }

            composable(Bookmark.route) {
                BookmarkScreen()
            }

            composable(Setting.route) {
                SettingScreen()
            }
        }
    }
}
