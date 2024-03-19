package project.activity

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import org.jetbrains.anko.*
import project.madule.DrawerHelper.Companion.setDrawerView
import project.madule.makeView
import project.main.G

class ActivitySubmitRequest : XActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDrawerView(contentSubmitRequest())
    }
}

fun contentSubmitRequest() = makeView {
    scrollView {
        lparams(matchParent, matchParent)
        verticalLayout {
            lparams(matchParent, matchParent)
            gravity = Gravity.CENTER_HORIZONTAL
            linearLayout {
                gravity = Gravity.CENTER
                textView("نام کاربر : ").lparams(wrapContent, matchParent)
                textView("حمید خان محمدی")
            }

        }.applyRecursively {
            when (it) {
                is TextView -> it.setTypeface(G.defaultFont, Typeface.ITALIC + Typeface.BOLD)
            }
        }
    }
}
