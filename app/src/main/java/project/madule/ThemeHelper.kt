package project.madule

import org.jetbrains.anko.defaultSharedPreferences
import project.main.G


class ThemeHelper {
  companion object {

    fun applyTheme() {
      when(G.theme) {
        "light" -> G.currentActivity.setTheme(
          getResource(
            "AppTheme.Light",
            "style"
          )
        )
        "dark" -> G.currentActivity.setTheme(
          getResource(
            "AppTheme.Dark",
            "style"
          )
        )
      }
    }

    fun switchTheme() {
      G.theme = if (G.theme == "light") "dark" else "light"
      G.context.defaultSharedPreferences.edit().putString("theme", G.theme).apply()
      G.currentActivity.recreate()
    }

    fun getResource(name: String, type: String) = G.context.resources.getIdentifier(name, type, G.context.packageName)
    fun getThemeDrawable(name: String) =
      getResource("${name}_${G.theme}", "drawable")
  }

}