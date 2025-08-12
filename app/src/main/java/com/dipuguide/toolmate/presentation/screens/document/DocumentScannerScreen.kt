package com.dipuguide.toolmate.presentation.screens.document

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dipuguide.toolmate.presentation.common.state.UiState
import com.dipuguide.toolmate.presentation.navigation.DocumentScannerRoute
import com.dipuguide.toolmate.presentation.navigation.LocalNavController
import com.dipuguide.toolmate.utils.Dimen
import com.google.mlkit.vision.documentscanner.GmsDocumentScanner
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ContextCastToActivity")
@Composable
fun DocumentScannerScreen(modifier: Modifier = Modifier) {

    val activity = LocalContext.current as Activity
    val viewModel: DocumentScannerViewModel = hiltViewModel<DocumentScannerViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val navController = LocalNavController.current

    val documentScannerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val result = GmsDocumentScanningResult.fromActivityResultIntent(result.data)
            viewModel.documentScanned(
                context = activity,
                result = result
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Title")
                },
                navigationIcon = {

                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.startDocumentScan(
                        activity = activity
                    ) { sender ->
                        documentScannerLauncher.launch(IntentSenderRequest.Builder(sender).build())
                    }
                }
            ) {
                Row(
                    modifier = Modifier.padding(Dimen.PaddingSmall)
                ) {
                    Icon(
                        imageVector = Icons.Default.DocumentScanner,
                        contentDescription = null
                    )
                    Text(text = "Scan")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(horizontal = Dimen.PaddingMedium),
            verticalArrangement = Arrangement.spacedBy(Dimen.SpacerSmall)
        ) {
            items(10) {
                PdfDocumentWidget(
                    pdfTitle = "Motu Photo 22376r6288283",
                    pdfDetail = "124:2367:367, yhjbshgsdsdssddsds"
                ) {

                }
            }
        }
    }
}


@Composable
fun PdfDocumentWidget(
    pdfTitle: String,
    pdfDetail: String,
    onClickPdf: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            onClickPdf()
        },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.PaddingSmall),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.PictureAsPdf,
                contentDescription = null,
                modifier = Modifier.size(42.dp)
            )
            Spacer(modifier = Modifier.width(Dimen.SpacerSmall))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Title",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "pdf Details",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }
    }
}