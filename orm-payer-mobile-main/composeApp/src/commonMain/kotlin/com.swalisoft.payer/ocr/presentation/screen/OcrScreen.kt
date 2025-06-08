package com.swalisoft.payer.ocr.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swalisoft.payer.app.MainAppLayout
import com.swalisoft.payer.assets.OcrIcons
import com.swalisoft.payer.assets.ocricons.PhotoCamera
import com.swalisoft.payer.assets.ocricons.PhotoLibrary
import com.swalisoft.payer.common.Route
import com.swalisoft.payer.ocr.presentation.OcrViewModel
import com.swalisoft.payer.ocr.presentation.components.CameraPreview
import com.swalisoft.payer.ocr.presentation.components.OcrPreview
import com.swalisoft.payer.ocr.presentation.ImagePicker
import com.swalisoft.payer.core.composable.GradientButtonCircle
import com.swalisoft.payer.ocr.presentation.OcrStage
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OcrScreen(
  navigator: NavHostController,
  viewModel: OcrViewModel = koinViewModel()
) {
  var showImagePicker by remember { mutableStateOf(false) }
  val state by viewModel.state.collectAsState()

  MainAppLayout(
    navigator = navigator,
    title = "OCR",
    handleBack = { navigator.popBackStack() },
    floatingActions = {
      if (!state.isLoading) {
        Column(
          modifier = Modifier.offset(-(4).dp, -(22).dp),
          verticalArrangement = Arrangement.spacedBy(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          if(state.stage is OcrStage.Preview) {
            GradientButtonCircle(
              icon = OcrIcons.PhotoCamera,
              modifier = Modifier.size(44.dp),
              onClick = {
                showImagePicker = false

                viewModel.backToCamera()
              }
            )
          }
          GradientButtonCircle(
            icon = OcrIcons.PhotoLibrary,
            onClick = { showImagePicker = true }
          )
        }
      }
    }
  ) {
    Box {
      when (state.stage) {
        is OcrStage.Idle -> {
          CameraPreview(
            onCapture = { imageBytes ->
              viewModel.preview(imageBytes)
            }
          )
        }

        is OcrStage.Preview -> {
          val stage = state.stage as OcrStage.Preview;

          OcrPreview(
            image = stage.image,
            onConfirm = { categoryId ->
              viewModel.processImage(stage.image, categoryId).invokeOnCompletion {
                navigator.navigate(Route.SingleNote)
              }
            },
          )
        }

        is OcrStage.Error -> {
          Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
          ) {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "Error",
                modifier = Modifier.size(48.dp)
              )
              Spacer(modifier = Modifier.height(16.dp))
              Text(text = "Error con el OCR")
            }
          }
        }
      }

      if (state.isLoading) {
        Box(
          modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
          contentAlignment = Alignment.Center,
        ) {
          CircularProgressIndicator()
        }
      }
    }
  }

  if (showImagePicker) {
    ImagePicker(onImagePicked = { imageBytes ->
      if (imageBytes.isNotEmpty()) {
        viewModel.preview(imageBytes)
      }

      showImagePicker = false
    })
  }
}