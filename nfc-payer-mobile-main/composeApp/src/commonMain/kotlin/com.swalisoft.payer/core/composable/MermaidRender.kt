package com.swalisoft.payer.core.composable

import androidx.compose.runtime.Composable

@Composable
fun MermaidRender(
  content: String,
) {
  val htmlContent = """
  <html>
  <head>
      <link rel="stylesheet" href="https://localhost/styles.css">
      <script src="https://localhost/mermaid_min_11_5.js"></script>
      <script>
          mermaid.initialize({ startOnLoad:true, theme: "dark" });
      </script>
  </head>
  <body>
    <div class="mermaid">
$content
    </div>
  </body>
  </html>
""".trimIndent()

  InAppWebView(htmlContent)
}
