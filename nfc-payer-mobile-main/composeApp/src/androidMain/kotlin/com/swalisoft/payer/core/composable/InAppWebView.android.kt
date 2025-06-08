package com.swalisoft.payer.core.composable

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import java.io.InputStream

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun InAppWebView(
  content: String,
  modifier: Modifier,
  onLoaded: () -> Unit,
) {
  AndroidView(
    modifier = modifier.zIndex(-1f),
    factory = {
      val webivew = WebView(it).apply {
        layoutParams = ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.WRAP_CONTENT,
        )

        settings.javaScriptEnabled = true

        webViewClient = object : WebViewClient() {
          override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
            view.settings.setSupportZoom(false)
          }

          // Intercept resource requests to load from assets if enabled
          override fun shouldInterceptRequest(
            view: WebView,
            request: WebResourceRequest
          ): WebResourceResponse? {
            val url = request.url.toString()

            if (url.endsWith(suffix = ".css") || url.endsWith(suffix = ".js")) {
              val fileName = url.substring(startIndex = url.lastIndexOf(char = '/') + 1)

              try {
                val inputStream: InputStream = view.context.assets.open(fileName)

                val mimeType = when {
                  url.endsWith(suffix = ".css") -> "text/css"
                  url.endsWith(suffix = ".js") -> "application/javascript"
                  else -> "text/plain"
                }
                return WebResourceResponse(mimeType, "UTF-8", inputStream)
              } catch (e: Exception) {
                println(e)
              }
            }

            return super.shouldInterceptRequest(view, request)
          }
        }

        loadData(content, "text/html", "utf-8")
      }

      webivew.setBackgroundColor(Color.TRANSPARENT)

      webivew
    }
  )
}
