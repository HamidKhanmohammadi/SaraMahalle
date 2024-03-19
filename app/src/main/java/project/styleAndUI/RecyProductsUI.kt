package project.styleAndUI

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.powrolx.saramahalle.R
import org.jetbrains.anko.*
import project.main.G

class RecyProductsUI : AnkoComponent<ViewGroup> {

    companion object {
        val pro_imge_id = 1
        val pro_name_id = 2
        val pro_layout_id = 3
    }

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        linearLayout {
            lparams(wrapContent, wrapContent)
            verticalLayout {
                backgroundResource = R.drawable.image_backgraound
                linearLayout {
                    id = pro_layout_id
                    clipToOutline = true
                    gravity = Gravity.CENTER
                    imageView(R.drawable.ic_launcher_background){
                        id = pro_imge_id
                    }.lparams {
                        backgroundResource = R.drawable.image_backgraound
                        width = 320
                        height = 320
                    }
                }
                linearLayout {
                    gravity = Gravity.CENTER
                    textView() {
                        txtdescLoan()
                        id = pro_name_id
                    }
                }
                verticalLayout {
                    textView("") {txtdescLoan()}
                }
            }.applyRecursively {
                when (it) {
                    is _LinearLayout -> it.lparams { lparamsPartLay2() }
                }
            }
        }.applyRecursively {
            when (it) {
                is TextView -> it.setTypeface(G.defaultFont, Typeface.ITALIC + Typeface.BOLD)
            }
        }
    }
}