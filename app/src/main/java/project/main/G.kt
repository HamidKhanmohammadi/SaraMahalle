package project.main

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.Typeface
import android.os.StrictMode
import android.view.View
import androidx.core.content.res.ResourcesCompat
import org.jetbrains.anko.defaultSharedPreferences
import project.activity.XActivity
import project.adapter.ProductsStructe
import project.madule.*
import project.main.G.Companion.database
import project.main.G.Companion.directorySQLTest
import project.module.lang.EnLang
import project.module.lang.FaLang
import project.module.lang.Lang
import project.module.runOnUi
import project.styleAndUI.StyleHomePageColor
import java.io.File


val L = G.language
val S = G.styleColor

class G : Application() {
    companion object {
        lateinit var currentActivity: XActivity
        lateinit var language: Lang
        lateinit var styleColor: StyleHomePageColor
        lateinit var context: Context
        lateinit var assetManager: AssetManager
        var versionCode: Int = 100
        var versionName: String = "1.00"
        lateinit var theme: String
        var locale: String = "fa"
        lateinit var defaultFont: Typeface
        lateinit var selectedProductsStructeList: ProductsStructe
        lateinit var directorySQLTest: File
        var database : SQLiteDatabase? = null
        val documentsList = ArrayList<ProductsStructe>()

        var layoutDirection: Int = View.LAYOUT_DIRECTION_LTR

        var config: Configurator? = null
            set(value) {
                field = value
                value?.config()
                value?.populateNotificationChannels()
            }


        fun initialize() {
            theme = context.defaultSharedPreferences.getString("theme", "light")!!
            assetManager = context.assets
            config()
        }

        fun config() {
            Debug.logTag = "Sample"
            Debug.level = Debug.LOG_LEVEL.DEBUG
            language = EnLang()
            styleColor = StyleHomePageColor()
            processLanguage()
        }

        private fun processLanguage() {
            when (locale) {
                "en" -> {
                    language = EnLang()
                    layoutDirection = View.LAYOUT_DIRECTION_LTR
                    defaultFont = ResourcesCompat.getFont(
                        context,
                        ThemeHelper.getResource("english", "font")
                    )!!
                }

                "fa" -> {
                    language = FaLang()
                    layoutDirection = View.LAYOUT_DIRECTION_RTL
                    defaultFont =
                        ResourcesCompat.getFont(context, ThemeHelper.getResource("farsi", "font"))!!
                }
            }
        }

        fun vertion() {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
        initialize()
        vertion()

        createOrOpenDB()

    }
}

fun createOrOpenDB() {
    runOnUi {
        directorySQLTest = FileHelper.internal("testDB")
        val dbHelper = DatabaseHelper(G.context)
        database = dbHelper.writableDatabase
    }

}