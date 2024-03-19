package project.madule

import android.util.Log


class Debug {
  enum class LOG_LEVEL(val ord: Int) {
    VERBOSE(1),
    DEBUG(2),
    INFO(3),
    WARN(4),
    ERROR(5),
    DISABLE(6),
  }

  companion object {
    var logTag = "LOG"
    var level = LOG_LEVEL.DISABLE

    fun verbose(message: Any) = log(LOG_LEVEL.VERBOSE, message)
    fun debug(message: Any) = log(LOG_LEVEL.DEBUG, message)
    fun info(message: Any) = log(LOG_LEVEL.INFO, message)
    fun warn(message: Any) = log(LOG_LEVEL.WARN, message)
    fun error(message: Any) = log(LOG_LEVEL.ERROR, message)

    private fun log(level: LOG_LEVEL, message: Any) {
      if (level.ord < Companion.level.ord) {
        return
      }

      when (level) {
        LOG_LEVEL.VERBOSE -> Log.v(logTag, "" + message)
        LOG_LEVEL.DEBUG -> Log.d(logTag, "" + message)
        LOG_LEVEL.INFO -> Log.i(logTag, "" + message)
        LOG_LEVEL.WARN -> Log.w(logTag, "" + message)
        LOG_LEVEL.ERROR -> Log.e(logTag, "" + message)
        else -> {

        }
      }
    }
  }
}
