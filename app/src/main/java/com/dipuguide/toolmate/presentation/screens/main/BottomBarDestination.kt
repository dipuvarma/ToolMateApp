package com.dipuguide.toolmate.presentation.screens.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.dipuguide.toolmate.presentation.navigation.Bookmark
import com.dipuguide.toolmate.presentation.navigation.Home
import com.dipuguide.toolmate.presentation.navigation.Setting

sealed class BottomBarDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String,
    val contentDescription: String,
) {
    data object HomeBottomBar :
        BottomBarDestination(
            Home.route,
            Icons.Rounded.Home,
            Icons.Outlined.Home,
            "Home",
            "Home Screen",
        )

    data object BookmarksBottomBar : BottomBarDestination(
        Bookmark.route,
        Icons.Rounded.Bookmark,
        Icons.Outlined.BookmarkBorder,
        "Bookmarks",
        "Bookmarks Screen",
    )

    data object SettingsBottomBar : BottomBarDestination(
        Setting.route,
        Icons.Rounded.Settings,
        Icons.Outlined.Settings,
        "Settings",
        "Settings Screen",
    )
}