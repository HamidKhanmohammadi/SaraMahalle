package project.madule

import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ir.mebank.loan.core.module.XIntent
import project.main.G
import project.module.FunctionBlock
import project.module.HelperDialog
import project.module.MethodBlock

class PermissionHandler(val permissions: List<String>, props: MethodBlock<PermissionHandler> = {}) {
  companion object {
    var lastRequestCode: Int = 0
  }

  private var activity = G.currentActivity
  private var requestCode: Int = ++lastRequestCode

  var onGrant: FunctionBlock? = null
  var permissionRequiredTitle: String = "Permission Required"
  var whyPermissionRequired: String = "Please grant all permissions..."

  init {
    activity.addPermissionHandler(this)
    this.props()
    request()
  }

  private fun canShowReason(): Boolean {
    for (permission in permissions) {
      if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
        return true
      }
    }

    return false
  }

  private fun request() {
    if (isGranted()) {
      return
    }

    if (canShowReason()) {
      val a = HelperDialog.alertOkCancel(
        title = permissionRequiredTitle,
        message = whyPermissionRequired,
        onOk = {
          requestPermission()
        },
        onCancel = {
          //myIntent(ActivityDocuments())
          //activity.finish()
        }
      )
    } else {
      requestPermission()
    }
  }

  private fun isGranted(): Boolean {
    for (permission in permissions) {
      if (PERMISSION_GRANTED != ContextCompat.checkSelfPermission(G.context, permission)) {
        return false
      }
    }

    return true
  }

  private fun requestPermission() {
    ActivityCompat.requestPermissions(G.currentActivity, permissions.toTypedArray(), requestCode)
  }

  fun processOnPermissionResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray): Boolean {
    if (requestCode != this.requestCode) {
      return false
    }

    //it's mine
    if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
      if (onGrant != null) {
        onGrant!!.invoke()
      }

      return true
    }

    if (canShowReason()) {
      request()
      //is denied
    } else {
      //is always denied
      HelperDialog.alertOkCancel(
        title = permissionRequiredTitle,
        message = whyPermissionRequired,
        onOk = {
          XIntent.openAppSettings()
          G.currentActivity.finish()
        },
        onCancel = {
          activity.finish()
        }
      )
    }

    return true
  }
}