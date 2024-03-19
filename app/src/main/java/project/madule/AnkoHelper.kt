package project.madule

import android.content.Context
import android.content.Intent
import android.widget.Space
import org.jetbrains.anko.*
import project.activity.XActivity
import project.main.G


fun _LinearLayout.mspace(weight: Float): Space {
  return space().lparams(0, 0, weight)
}

fun makeView(init: AnkoContext<Context>.() -> Unit) = G.currentActivity.UI {
  init()
}.view


fun Int.dip() = G.context.dip(this)
fun Float.dip() = G.context.dip(this)

fun myIntentFinished(activity: XActivity) {
  G.currentActivity.startActivity(Intent(G.context, activity::class.java))
  G.currentActivity.finish()
}
fun myIntent(activity: XActivity) {
  G.currentActivity.startActivity(Intent(G.context, activity::class.java))
}
