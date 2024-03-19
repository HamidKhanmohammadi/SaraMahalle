package project.madule

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.powrolx.saramahalle.R
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.navigationView
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.drawerLayout
import project.activity.MainActivity
import project.main.G
import project.main.L
import project.main.S
import project.module.MethodBlock

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
class DrawerHelper(var content: View, props: MethodBlock<DrawerHelper> = {}) {

    companion object {
        fun setDrawerView(content: View, props: MethodBlock<DrawerHelper> = {}) {
            DrawerHelper(content, props)
        }
    }

    inner class Ui {
        lateinit var toolbar: Toolbar
        lateinit var drawer: DrawerLayout
        lateinit var navView: NavigationView
    }

    val ui = Ui()

    init {
        this.props()
        initialize()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun initialize() = with(G.currentActivity) {
        ui.drawer = drawerLayout {
            layoutDirection = G.layoutDirection

            verticalLayout {
                appBarLayout {
                    backgroundColor = S.appBarBackground
                    ui.toolbar = toolbar()
                    ui.toolbar.setTitleTextColor(S.appBarText)
                }

                verticalLayout {
                    backgroundColor = Color.parseColor("#676b65")

                    addView(content.lparams(matchParent, matchParent))
                }.lparams(matchParent, matchParent)
            }

            ui.navView = navigationView {
                backgroundColor = S.navBackground
                itemTextColor = S.navText
                itemIconTintList = S.navIcone
                addHeaderView(G.config?.getDrawerHeader()!!)
                verticalLayout {
                    gravity = Gravity.BOTTOM
                    textView {
                        gravity = Gravity.CENTER
                        text = "${L.appName}\n${L.version}${G.versionName}"
                        textColor = Color.parseColor("#fde77f")
                        textSize = 16f
                        setTypeface(typeface, Typeface.ITALIC + Typeface.BOLD)
                    }.lparams(matchParent, wrapContent){
                        bottomMargin = 20
                    }
                }
            }.lparams(height = matchParent) {
                gravity = GravityCompat.START
            }
        }.applyRecursively {
            when (it) {
                is TextView -> it.setTypeface(G.defaultFont, it.typeface.style)
            }
        }

        setSupportActionBar(ui.toolbar)
        ui.toolbar.onClick {
            myIntentFinished(MainActivity())
        }
        val toggle = ActionBarDrawerToggle(
            this, ui.drawer, ui.toolbar, R.string.none, R.string.none
        )
        ui.drawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.title = L.appName
        //supportActionBar?.setIcon(R.drawable.mehr_eghtesad1)
        G.config?.populateDrawerMenu(ui.drawer, ui.navView.menu)

    }
}

fun DrawerLayout.close() {
    this.closeDrawer(GravityCompat.START)
}