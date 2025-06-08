package com.swalisoft.payer

import android.content.Context
import android.os.Bundle
import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
  companion object {
    lateinit var instance: MainActivity
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    instance = this

    val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val savedName = sharedPref.getString("username", "Guest")

    print(savedName)

    with(sharedPref.edit()) {
      putString("username", "test")
      apply()
    }

    setContent {
      App()
    }

    enableEdgeToEdge(
      statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
      navigationBarStyle = SystemBarStyle.light(
        Color.TRANSPARENT, Color.TRANSPARENT
      ),
    )
  }
}

@Preview
@Composable
fun AppAndroidPreview() {
  App()
}