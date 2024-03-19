package project.madule

import android.content.Intent
import android.view.Gravity
import android.view.Menu
import androidx.drawerlayout.widget.DrawerLayout
import com.powrolx.saramahalle.R
import org.jetbrains.anko.*
import project.activity.ActivitySubmitRequest
import project.main.G
import project.module.NotificationChannels
import project.module.NotificationHandler

class Config : Configurator {

    override fun getDrawerHeader() = makeView {
        verticalLayout {
            linearLayout {
                lparams {
                    topMargin = 30
                }
                gravity = Gravity.CENTER
                imageView(R.drawable.white_favicon_180)
                imageView(R.drawable.text_panel)
                horizontalPadding = 48.dip()
            }
            imageView {
                backgroundResource = R.drawable.list_devider_loan
            }.lparams {
                width = matchParent
                height = 2
            }
        }
    }

    override fun populateDrawerMenu(drawer: DrawerLayout, menu: Menu) {
        menu.addItem(
            title = "ورود",
            icon = IC.gmd_login,
            onClick = {
                val intent = Intent(G.currentActivity, ActivitySubmitRequest::class.java)
                intentMenu(drawer, intent)
                true
            }
        )
        menu.addItem(
            title = "اطلاعات هویتی",
            icon = IC.gmd_person_outline,
           // onClick = {
           //     val intent = Intent(G.currentActivity, ActivityIdentityInformation::class.java)
           //     intentMenu(drawer, intent)
           //     true
           // }
        )
        menu.addItem(
            title = "مدارک و مستندات",
            icon = IC.gmd_subtitles,
            // onClick = {
            //    val intent = Intent(G.currentActivity, ActivityDocuments::class.java)
            //    intentMenu(drawer, intent)
            //    true
            //}
        )
        menu.addItem(
            title = "هزینه ها و درآمدها",
            icon = IC.gmd_timeline,
            //onClick = {
            //    val intent = Intent(G.currentActivity, ActivityCostsAndRevenues::class.java)
            //    intentMenu(drawer, intent)
            //    true
            //}
        )
        menu.addItem(
            title = "وام و تسهیلات دریافت شده",
            icon = IC.gmd_library_books,
            //onClick = {
            //    val intent = Intent(G.currentActivity, ActivityLoansReceived::class.java)
            //    intentMenu(drawer, intent)
            //    true
            //}
        )
        menu.addItem(
            title = "سپرده های بانکی",
            icon = IC.gmd_folder_shared,
            //onClick = {
            //    val intent = Intent(G.currentActivity, ActivityBankDeposits::class.java)
            //    intentMenu(drawer, intent)
            //    true
            //}
        )
        menu.addItem(
            title = "اموال و دارایی ها",
            icon = IC.gmd_location_city,
            //onClick = {
            //    val intent = Intent(G.currentActivity, ActivityProperty::class.java)
            //    intentMenu(drawer, intent)
            //    true
            //}
        )
        menu.addItem(
            title = "تاریخچه تسهیلات",
            icon = IC.gmd_history,
            //onClick = {
            //    val intent = Intent(G.currentActivity, ActivityLoanHistory::class.java)
            //    intentMenu(drawer, intent)
            //    true
            //}
        )
    }

    override fun config() {
        Debug.logTag = "NotifyApp"
        Debug.level = Debug.LOG_LEVEL.DEBUG
    }

    override fun populateNotificationChannels() {
        NotificationHandler.Channel(NotificationChannels.PUBLIC) {
            name = "Public News"
            description = "Global News about everything..."
        }

        NotificationHandler.Channel(NotificationChannels.DISCOUNTS) {
            name = "Discount and Offers"
            description = "Update about any offers and discounts..."
        }

        NotificationHandler.Channel(NotificationChannels.Q_SERVICE) {
            name = "Question/Answer Service"
            description = "When you receive notify in question service, we will notify here"
        }
    }
}

fun intentMenu(drawer: DrawerLayout, intent: Intent) {
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    G.currentActivity.startActivity(intent)
    //G.currentActivity.finish()
    drawer.close()
}
