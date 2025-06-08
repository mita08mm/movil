package com.swalisoft.payer

import android.app.Application
import com.swalisoft.payer.common.di.initKoin
import org.koin.android.ext.koin.androidContext

class NotesApplication : Application() {
  override fun onCreate() {
    initKoin {
      androidContext(this@NotesApplication)
    }
  }
}

