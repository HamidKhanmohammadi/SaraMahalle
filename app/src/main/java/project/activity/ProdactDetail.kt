package project.activity

import android.os.Bundle
import android.view.Gravity
import com.powrolx.saramahalle.R
import org.jetbrains.anko.*
import project.madule.DrawerHelper.Companion.setDrawerView
import project.madule.makeView
import project.main.G

class ProdactDetail : XActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setDrawerView(prodactDetail())
    }

}

fun prodactDetail() = makeView {
    verticalLayout {
        verticalLayout {
            imageView() {
                backgroundResource = R.drawable.ic_launcher_background
            }.lparams(wrapContent, wrapContent) {
                gravity = Gravity.CENTER
            }

            textView(G.selectedProductsStructeList.pic_name) {
                textSize = 20f
            }.lparams(wrapContent, wrapContent) {
                gravity = Gravity.CENTER
            }

            ratingBar {
                rating = 3.5f
            }.lparams(wrapContent, wrapContent) {
                gravity = Gravity.CENTER
            }
        }
    }
}