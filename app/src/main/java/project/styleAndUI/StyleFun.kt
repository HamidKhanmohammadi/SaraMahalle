package project.styleAndUI

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.powrolx.saramahalle.R
import org.jetbrains.anko.*
import project.madule.dip
import project.main.S

fun _LinearLayout.wightLayout() {
    backgroundColor = Color.parseColor("#c3a53c")
}

//background slider
fun _LinearLayout.greenLayout() {
    backgroundColor = S.sliderBackground
    gravity = Gravity.CENTER
}

fun TextView.txtTitleLoan() {
    textSize = 21f
    padding = 60
    gravity = Gravity.CENTER
    textColor = Color.BLACK
    setTypeface(typeface, Typeface.ITALIC)
}

fun TextView.txtdescLoan() {
    gravity = Gravity.CENTER
    textColor = Color.BLACK
}

fun TextView.question() {
    padding = 8.dip()
    textColor = Color.WHITE
    textSize = 15f
}

fun Button.btnHome() {
    setTypeface(typeface, Typeface.BOLD_ITALIC)
    textColor = Color.parseColor("#007E42")
    width = wrapContent
    height = wrapContent
    backgroundResource = R.drawable.style_button_home
}

fun LinearLayout.LayoutParams.lparamsPartLay2() {
    width = matchParent
    height = wrapContent
    gravity = Gravity.CENTER
    topMargin = 10
    bottomMargin = 10
    leftMargin = 10
    rightMargin = 10
}

fun LinearLayout.LayoutParams.maping(){
    width = 0.dip()
    height = matchParent
    weight = 1F
    topMargin = 2.dip()
    leftMargin = 2.dip()
    rightMargin = 2.dip()
    bottomMargin = 2.dip()
}