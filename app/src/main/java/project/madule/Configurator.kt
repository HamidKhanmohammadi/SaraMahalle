package project.madule

import android.view.Menu
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout

interface Configurator {
  fun config()
  fun populateNotificationChannels()
  fun populateDrawerMenu(drawer: DrawerLayout, menu: Menu)
  fun getDrawerHeader(): View
}