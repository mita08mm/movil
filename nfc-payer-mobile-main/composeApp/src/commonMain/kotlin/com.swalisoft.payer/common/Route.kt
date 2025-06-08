package com.swalisoft.payer.common

import kotlinx.serialization.Serializable

sealed interface Route {

  @Serializable
  data object OptionGraph : Route

  @Serializable
  data object Languages : Route

  @Serializable
  data object Login: Route

  @Serializable
  data object  Register: Route

  @Serializable
  data class Subject(val categoryId: String? = null): Route

//  @Serializable
//  data object Subject : Route

    @Serializable
  data object ListNotes: Route

  @Serializable
  data class AddNotes(val categoryId: String): Route

  @Serializable
  data object SingleNote : Route

  @Serializable
  data object Home : Route

  @Serializable
  data object LandingAuth : Route

  @Serializable
  data object Notification: Route

  @Serializable
  data object Profile: Route

  @Serializable
  data object ToDo: Route

  @Serializable
  data object OcrScan: Route

  @Serializable
  data object CategoryNote: Route

}
