package project.adapter


import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import org.jetbrains.anko.*
import project.madule.dip
import project.madule.makeView
import java.util.*

class AdapterImagePager(var imageIds: ArrayList<Int>, private val title: ArrayList<String>, private val desc: ArrayList<String>) :
    PagerAdapter() {

    override fun getCount(): Int {
        return imageIds.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        lateinit var img_slider: ImageView
        lateinit var txt_title_slider: TextView
        lateinit var txt_desc_slider: TextView
        val view = makeView {
            verticalLayout {
                lparams{
                    width = matchParent
                    height = matchParent
                }
                relativeLayout {
                    img_slider = imageView {
                        scaleType = ImageView.ScaleType.FIT_CENTER
                    }.lparams {
                        width = 300.dip()
                        height = 250.dip()
                        topMargin = 30.dip()
                        bottomMargin = 50.dip()
                        leftMargin = 60.dip()
                    }

                   /* txt_title_slider = textView{
                        text = "..."
                        textColor = Color.parseColor("#EDFAF1")
                        textSize = 24F
                        gravity = Gravity.CENTER
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                    }
                    txt_desc_slider = textView("000") {
                        text = "..."
                        textColor = Color.parseColor("#EDFAF1")
                        textSize = 16F
                        gravity = Gravity.CENTER
                        leftPadding = 10.dip()
                        rightPadding = 10.dip()
                    }.lparams{
                        width = matchParent
                        height = wrapContent
                        alignParentBottom()
                    }**/

                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }
        //txt_title_slider.text = title[position]
        //txt_desc_slider.text = desc[position]
        img_slider.setImageResource(imageIds[position])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
