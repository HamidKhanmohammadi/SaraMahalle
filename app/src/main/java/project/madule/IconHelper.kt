package project.madule

import android.graphics.Color
import com.mikepenz.iconics.IconicsColor
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial
import com.mikepenz.iconics.utils.toIconicsSizeDp
import project.main.G

class IconHelper {
  companion object {
    fun icon(name: GoogleMaterial.Icon, size: Int = 5, color: Int = Color.BLUE, padding: Int = 0): IconicsDrawable {
      return IconicsDrawable(G.context, name).apply {
        size.toIconicsSizeDp()
        IconicsColor.colorInt(color)
        padding.toIconicsSizeDp()
      }
    }
  }
}