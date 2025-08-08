package com.dipuguide.toolmate.presentation.screens.main.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dipuguide.toolmate.R
import com.dipuguide.toolmate.domain.model.ToolCardItemModel
import com.dipuguide.toolmate.presentation.common.component.SearchWidget
import com.dipuguide.toolmate.presentation.common.component.ToolCardItemWidget
import com.dipuguide.toolmate.presentation.navigation.BarcodeScannerRoute
import com.dipuguide.toolmate.presentation.navigation.DocumentScannerRoute
import com.dipuguide.toolmate.presentation.navigation.ImageLabelingRoute
import com.dipuguide.toolmate.presentation.navigation.LocalNavController
import com.dipuguide.toolmate.presentation.navigation.ObjectDetectionRoute
import com.dipuguide.toolmate.presentation.navigation.SpeechToTextRoute
import com.dipuguide.toolmate.presentation.navigation.TextRecognitionRoute
import com.dipuguide.toolmate.utils.Dimen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(paddingValues: PaddingValues) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val navController = LocalNavController.current

    val toolCardItems = listOf(
        ToolCardItemModel(
            id = 1,
            title = "Barcode Scanner",
            image = painterResource(id = R.drawable.barcode_scanner_icon),
            imageDescription = "Barcode Scanner",
            onClick = {
                navController.navigate(BarcodeScannerRoute)
            }
        ),
        ToolCardItemModel(
            id = 2,
            title = "Document Scanner",
            image = painterResource(id = R.drawable.document_scanner_icon),
            imageDescription = "Document Scanner",
            onClick = {
                navController.navigate(DocumentScannerRoute)
            }
        ),
        ToolCardItemModel(
            id = 3,
            title = "Object Detection",
            image = painterResource(id = R.drawable.object_dectation_icon),
            imageDescription = "Object Detection",
            onClick = {
                navController.navigate(ObjectDetectionRoute)
            }
        ),
        ToolCardItemModel(
            id = 4,
            title = "Image Labeling",
            image = painterResource(id = R.drawable.image_labeling_icon),
            imageDescription = "Image Labeling",
            onClick = {
                navController.navigate(ImageLabelingRoute)
            }
        ),
        ToolCardItemModel(
            id = 5,
            title = "Text Recognition",
            image = painterResource(id = R.drawable.text_recognition_icon),
            imageDescription = "Text Recognition",
            onClick = {
                navController.navigate(TextRecognitionRoute)
            }
        ),

        ToolCardItemModel(
            id = 6,
            title = "Speech To Text",
            image = painterResource(id = R.drawable.speech_to_text_icon),
            imageDescription = "Speech To Text",
            onClick = {
                navController.navigate(SpeechToTextRoute)
            }
        ),
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MyTopAppBar(
                scrollBehavior,
            )
        }
    ) { paddingValues ->
        ScrollContent(
            paddingValues = paddingValues,
            toolCardItems = toolCardItems
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(scrollBehavior: TopAppBarScrollBehavior) {
    val isTopAppBarMinimized = scrollBehavior.state.collapsedFraction > 0.5
    MediumTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                AnimatedVisibility(
                    visible = !isTopAppBarMinimized,
                    enter = expandHorizontally(),
                    exit = shrinkHorizontally(),
                ) {
                    Image(
                        painter = painterResource(R.drawable.tool_mate_logo),
                        contentDescription = "App Icon",
                    )
                }
                Text(
                    stringResource(R.string.app_name),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = if (isTopAppBarMinimized) MaterialTheme.typography.titleLarge else MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        )
    )
}


@Composable
fun ScrollContent(
    paddingValues: PaddingValues,
    toolCardItems: List<ToolCardItemModel>
) {

    var searchTool by remember { mutableStateOf("") }

    val filterToolCardItem = remember(searchTool, toolCardItems) {
        if (searchTool.isEmpty()) {
            toolCardItems
        } else {
            toolCardItems.filter { toolCardItemModel ->
                toolCardItemModel.title.contains(searchTool, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = Dimen.PaddingMedium)
    ) {
        Spacer(modifier = Modifier.height(Dimen.SpacerLarge))
        SearchWidget(value = searchTool) {
            searchTool = it
        }
        Spacer(modifier = Modifier.height(Dimen.SpacerLarge))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(Dimen.PaddingSmall),
            verticalArrangement = Arrangement.spacedBy(Dimen.PaddingSmall),
            contentPadding = PaddingValues(bottom = Dimen.PaddingSmall)
        ) {
            items(filterToolCardItem, key = { it.id }) { toolCardItem ->
                ToolCardItemWidget(
                    toolCardItem = toolCardItem
                )
            }
        }
    }

}
