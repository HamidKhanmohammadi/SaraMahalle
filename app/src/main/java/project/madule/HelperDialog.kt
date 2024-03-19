package project.module

import android.app.AlertDialog
import android.view.Gravity
import project.main.G
import project.main.L

class HelperDialog {
  companion object {
    private fun alertCommonBuilder(message: String, title: String? = null): AlertDialog.Builder {
      return AlertDialog.Builder(G.currentActivity)
        .setMessage(message)
        .setTitle(title)
        Gravity.RIGHT or Gravity.CENTER_VERTICAL
    }

    fun alertOk(message: String, title: String? = null, onOk: FunctionBlock = {}, justReturn: Boolean = false): AlertDialog.Builder {
      return alertCommonBuilder(message, title)
        .setPositiveButton(L.button_ok, { dialog, i -> onOk() }).apply {
          if (!justReturn) {
            create()
            show()
          }
        }
    }

    fun alertOkCancel(message: String, title: String? = null, onOk: FunctionBlock = {}, onCancel: FunctionBlock = {}, justReturn: Boolean = false): AlertDialog.Builder {
      return alertOk(message, title, onOk, true)
        .setNeutralButton(L.button_cancel, { dialog, i -> onCancel() }).apply {
          if (!justReturn) {
            create()
            show()
          }
        }
    }

    fun alertYesNo(message: String, title: String? = null, onYes: FunctionBlock = {}, onNo: FunctionBlock = {}, justReturn: Boolean = false): AlertDialog.Builder {
      return alertCommonBuilder(message, title)
        .setPositiveButton(L.button_yes, { dialog, i -> onYes() })
        .setNegativeButton(L.button_no, { dialog, i -> onNo() }).apply {
          if (!justReturn) {
            create()
            show()
          }
        }

    }

    fun alertYesNoCancel(message: String, title: String? = null, onYes: FunctionBlock = {}, onNo: FunctionBlock = {}, onCancel: FunctionBlock = {}, justReturn: Boolean = false) {
      alertYesNo(message, title, onYes, onNo, true)
        .setNeutralButton(L.button_cancel, { dialog, i -> onCancel() }).apply {
          if (!justReturn) {
            create()
            show()
          }
        }
    }
  }
}