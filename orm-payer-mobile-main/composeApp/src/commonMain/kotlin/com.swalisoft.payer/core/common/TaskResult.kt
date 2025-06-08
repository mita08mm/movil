package com.swalisoft.payer.core.common

import com.swalisoft.payer.core.exception.MainException

data class TaskResult<out T : Any>(
  val data: T? = null,
  val exception: MainException? = null
) {
  constructor(data: T) : this(data = data, exception = null)
  constructor(exception: MainException) : this(data = null, exception = exception)

  val hasData get() = data != null

  val hasException get() = exception != null

  companion object {
    fun handleTasks(
      vararg tasks: TaskResult<Any>,
      onComplete: () -> Unit,
      onError: (MainException) -> Unit
    ) {
      for (task in tasks) {
        if (task.hasException) {
          onError(task.exception!!)
          return
        }
      }
      onComplete()
    }
  }
}
