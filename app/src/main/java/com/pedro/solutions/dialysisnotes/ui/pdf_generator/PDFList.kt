package com.pedro.solutions.dialysisnotes.ui.pdf_generator

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


@Composable
fun PDFList(viewmodel: PDFViewModel, innerPadding: PaddingValues) {
    val context = LocalContext.current
    val pdfList by viewmodel.allPDFsGenerated.observeAsState(initial = listOf())

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) {

        }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding), userScrollEnabled = true
    ) {

        items(pdfList) { pdfItem ->

            PDFItem(
                pdfItem, modifier = Modifier
                    .fillMaxSize()
                    .height(160.dp)
                    .padding(10.dp)
            ) {
                launcher.launch(
                    arrayOf(
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                )
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "application/pdf"

                    // Optionally, specify a URI for the file that should appear in the
                    // system file picker when it loads.
                    putExtra(DocumentsContract.EXTRA_INITIAL_URI, Uri.parse(pdfItem.fileDirectory))
                }
                context.startActivity(intent)
            }
        }
    }
}