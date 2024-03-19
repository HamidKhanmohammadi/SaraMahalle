package test

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import project.activity.XActivity
import project.madule.ThemeHelper
import project.madule.makeView
import project.madule.mspace
import project.main.G
import project.main.L


class AboutActivity : XActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //setDrawerView(R.Layout.activity_about)
  }
}

fun content() = makeView {
  verticalLayout {
    mspace(0.1f)

    linearLayout {
      weightSum = 1.0f
      gravity = Gravity.CENTER

      imageView(ThemeHelper.getThemeDrawable("uncox")) {
        onClick {
          ThemeHelper.switchTheme()
        }
      }.lparams(0, wrapContent, 0.6f)
    }

    mspace(0.7f)

    textView {
      gravity = Gravity.CENTER
      text = "${L.appName}\n${L.version}${G.versionName}"
      textSize = 16f
      setTypeface(typeface, Typeface.ITALIC + Typeface.BOLD)
    }.lparams(matchParent, wrapContent)

    mspace(0.1f)

    horizontalProgressBar {
      progress = 50
    }.lparams(matchParent, wrapContent) {
      horizontalPadding = dip(48)
    }

    mspace(0.1f)
  }
}
