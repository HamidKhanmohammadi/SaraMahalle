package project.activity

import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import org.jetbrains.anko.applyRecursively
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout
import project.madule.DrawerHelper
import project.madule.makeView
import project.main.G

class BanksActivity: XActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DrawerHelper.setDrawerView(banksContent())
    }
}

fun banksContent() = makeView {
    verticalLayout {
        textView("بانکها")
    }.applyRecursively {
        when (it) {
            is TextView -> it.setTypeface(G.defaultFont, Typeface.ITALIC + Typeface.BOLD)
        }
    }
}