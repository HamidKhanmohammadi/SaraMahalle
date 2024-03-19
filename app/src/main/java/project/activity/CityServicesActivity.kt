package project.activity

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import org.jetbrains.anko.*
import project.madule.DrawerHelper
import project.madule.makeView
import project.main.G

class CityServicesActivity : XActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DrawerHelper.setDrawerView(cityServicesContent())
    }
}

fun cityServicesContent() = makeView {
    verticalLayout {
        textView("خدمات شهری")
    }.applyRecursively {
        when (it) {
            is TextView -> it.setTypeface(G.defaultFont, Typeface.ITALIC + Typeface.BOLD)
        }
    }
}