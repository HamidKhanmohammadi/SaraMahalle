package project.module

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import project.main.G

class NotificationHandler {

  class Builder(@DrawableRes var icon: Int, props: MethodBlock<Builder> = {}) {
    companion object {
      private var lastNotificationId = 0
    }

    private var notificationId = ++lastNotificationId
    private var actions = mutableListOf<Action>()

    var channelId: String = "default"
    var title: String = "Notification"
    var text: String = "Fill your notification text"
    var priority: Int = NotificationCompat.PRIORITY_DEFAULT
    var pendingIntent: PendingIntent? = null

    init {
      this.props()
      show()
    }

    private data class Action(@DrawableRes var icon: Int, var caption: String, var pendingIntent: PendingIntent?)

    fun addAction(@DrawableRes icon: Int, caption: String, pendingIntent: PendingIntent?) {
      actions.add(Action(icon, caption, pendingIntent))
    }

    private fun show() {
      NotificationManagerCompat
        .from(G.context)
        .notify(
          notificationId,
          NotificationCompat.Builder(G.context, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(priority)
            .apply {
              if (pendingIntent !== null) {
                setContentIntent(pendingIntent)
              }

              for (action in actions) {
                addAction(action.icon, action.caption, action.pendingIntent)
              }
            }
            .build()
        )
    }
  }


  class Channel(var id: String, var props: MethodBlock<Channel> = {}) {
    var name: String = "Default Channel"
    var description: String = "Default Channel Description"
    var importance: Int? = null
    var enableLights: Boolean = false
    var enableVibration: Boolean = false

    init {
      this.props()
      create()
    }

    private fun create() {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        if (importance == null) {
          importance = NotificationManager.IMPORTANCE_DEFAULT
        }

        val channel = NotificationChannel(id, name, importance!!).apply {
          description = this@Channel.description
          enableLights(enableLights)
          enableVibration(enableVibration)
        }

        val manager = G.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
      }
    }
  }

}