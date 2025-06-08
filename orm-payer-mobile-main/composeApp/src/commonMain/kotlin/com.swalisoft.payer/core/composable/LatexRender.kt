package com.swalisoft.payer.core.composable

import androidx.compose.runtime.Composable

@Composable
fun LatexRender(
  content: String,
) {
  val htmlContent = """
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://localhost/katex.min.css" >
<link rel="stylesheet" href="https://localhost/styles.css">
<script src="https://localhost/katex.min.js.js"></script>
</head>
<body>
  <div id="math"></div>
  <script>
  document.addEventListener("DOMContentLoaded", () => {
      katex.render(String.raw`$content`, document.getElementById('math'))
  })
  </script>
  </body>
  </html>
"""
  InAppWebView(htmlContent)
}
