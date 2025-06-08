package com.swalisoft.payer

interface Platform {
  val name: String
}

expect fun getPlatform(): Platform