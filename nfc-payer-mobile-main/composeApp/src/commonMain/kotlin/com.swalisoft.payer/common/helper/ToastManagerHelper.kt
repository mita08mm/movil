package eus.ctb.barik.common.helper

import com.swalisoft.payer.core.composable.ToastVariant
import org.jetbrains.compose.resources.StringResource

class ToastManagerHelper {
  private lateinit var listener: (Any, ToastVariant, Array<out Any>) -> Unit

  private fun showToast(message: StringResource, variant: ToastVariant = ToastVariant.Disabled, vararg formatArgs: Any) {
    listener(message, variant, formatArgs)
  }

  private fun showToast(message: String, variant: ToastVariant = ToastVariant.Error) {
    listener(message, variant, emptyArray())
  }

  // @param: message could be a String or a StringResource
  fun addListener(next: (message: Any, variant: ToastVariant, Array<out Any>) -> Unit) {
    listener = next
  }

  companion object {
    val shared = ToastManagerHelper()

    fun showToast(message: String, variant: ToastVariant = ToastVariant.Error) {
      shared.showToast(message, variant)
    }

    fun showToast(message: StringResource, variant: ToastVariant = ToastVariant.Error, vararg formatArgs: Any) {
      shared.showToast(message, variant, formatArgs)
    }
  }
}
