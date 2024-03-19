package ir.mebank.loan.core.module

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import project.activity.XActivity
import project.main.G

class XIntent {
  companion object {
    var lastPendingIntentId = 0

    fun createPendingIntent(clazz: Class<out XActivity>): PendingIntent {
      val intent = Intent(G.context, clazz)
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      val pendingIntent = PendingIntent.getActivity(G.context, ++lastPendingIntentId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
      return pendingIntent
    }

    fun openAppSettings() {
      val intent = Intent()
      intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
      val uri = Uri.fromParts("package", G.context.packageName, null)
      intent.data = uri
      G.currentActivity.startActivity(intent)
    }
  }
}