package test

import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.os.Build
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.powrolx.saramahalle.R
import ir.mebank.loan.core.module.*
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.viewPager
import project.adapter.AdapterImagePager
import project.adapter.AdapterRecyclerProducts
import project.adapter.ProductsStructe
import project.madule.*
import project.main.Endpoints
import project.main.G
import project.main.L
import project.module.*


private fun testNotification() {
    val snoozePendingIntent = XIntent.createPendingIntent(AboutActivity::class.java)
    val notificationBodyPendingIntent = XIntent.createPendingIntent(AboutActivity::class.java)

    NotificationHandler.Builder(android.R.drawable.ic_dialog_info) {
        channelId = NotificationChannels.PUBLIC
        text = "Hello from Notification Module"
        title = "Some Notification"
        pendingIntent = notificationBodyPendingIntent

        addAction(android.R.drawable.ic_dialog_alert, "Snooze", snoozePendingIntent)
    }
}

private fun testDialogs() {
    HelperDialog.alertOk(
        message = "Some Message",
        title = "Title",
        onOk = {
            HelperDialog.alertOkCancel(
                message = "Message 2",
                onOk = {
                    HelperDialog.alertYesNo(
                        message = "Message 2",
                        onYes = {
                            HelperDialog.alertYesNoCancel(
                                message = "Message 2",
                                onYes = {

                                }
                            )
                        }
                    )
                }
            )
        }
    )
}

private fun testPermissionHandlers() {
    PermissionHandler(
        listOf(
            AndroidPermission.WRITE_EXTERNAL_STORAGE,
            AndroidPermission.RECEIVE_SMS
        )
    ) {
        whyPermissionRequired = L.permissionRequiredMessage
        onGrant = {
            Debug.info("Client Granted second")
        }
    }
}

fun testWebservice2() {
    val endpoint = Endpoints.loanBranch
    val webservice = Webservice(
        endpoint, resource = "/Branches.php",
        output = BranchInfo.Branches::class.java
    )

    webservice.request {
        success = { call, response, content, entity ->
            Debug.info("Responce Received Branches" + content)
        }
        fail = { call, e ->
            Debug.info("Webservice Failed2 " + e?.message)
        }
    }
}

fun generalContent2() = makeView {

    var imageId: Int = 0
    lateinit var imagePagerAdapter: AdapterImagePager
    lateinit var slider: ViewPager
    lateinit var title: String
    lateinit var desc: String
    val imageIds = ArrayList<Int>()
    val titles = ArrayList<String>()
    val descs = ArrayList<String>()

    constraintLayout {
        lparams {
            width = matchParent
            height = matchParent
            backgroundColor = Color.rgb(2, 151, 20)
        }
        button {
            text = "ورود"
            textColor = Color.parseColor("#007E42")
            width = wrapContent
            height = wrapContent
            backgroundResource = R.drawable.style_button_home
        }.lparams {
            bottomToBottom = ConstraintSet.PARENT_ID
            endToEnd = ConstraintSet.PARENT_ID
            horizontalBias = 0.334f
            startToStart = ConstraintSet.PARENT_ID
            topToTop = ConstraintSet.PARENT_ID
            verticalBias = 0.011f
        }
        button {
            text = "ثبت نام"
            textColor = Color.parseColor("#007E42")
            width = wrapContent
            height = wrapContent
            backgroundResource = R.drawable.style_button_home
        }.lparams {
            bottomToBottom = ConstraintSet.PARENT_ID
            endToEnd = ConstraintSet.PARENT_ID
            horizontalBias = 0.03f
            startToStart = ConstraintSet.PARENT_ID
            topToTop = ConstraintSet.PARENT_ID
            verticalBias = 0.011f
        }
        imageView(R.drawable.text_panel).lparams {
            width = dip(108)
            height = dip(47)
            topMargin = dip(2)
            bottomToBottom = ConstraintSet.PARENT_ID
            endToEnd = ConstraintSet.PARENT_ID
            horizontalBias = 0.855f
            startToStart = ConstraintSet.PARENT_ID
            topToTop = ConstraintSet.PARENT_ID
            verticalBias = 0.001f
        }
        imageView(R.drawable.white_favicon_180).lparams {
            width = dip(35)
            height = dip(35)
            topMargin = dip(2)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                marginEnd = dip(8)
            }
            bottomToBottom = ConstraintSet.PARENT_ID
            endToEnd = ConstraintSet.PARENT_ID
            horizontalBias = 1.0f
            startToStart = ConstraintSet.PARENT_ID
            topToTop = ConstraintSet.PARENT_ID
            verticalBias = 0.011f
        }
        verticalLayout {

            slider = viewPager {
                for (i in 1..4) {
                    imageId = G.context.resources.getIdentifier(
                        "slide$i",
                        "drawable",
                        G.context.packageName
                    )
                    imageIds.add(imageId)
                    when (imageIds.size) {
                        1 -> {
                            title = "محاسبه آنلاین سود تسهیلات"
                            desc =
                                "با ثبت نام در سامانه تسهیلات می توانید با انتخاب طرح مورد نظر خود و مبلغ تسهیلات ، سود تسهیلات و تعداد قسط های خود را مشاهده کنید"
                        }
                        2 -> {
                            title = "درخواست آنلاین تسهیلات"
                            desc =
                                "شما میتوانید در این سامانه ، تسهیلات مورد نظر خود را به صورت آنلاین درخواست داده و از روند پیشرفت آن مطلع شوید"
                        }
                        3 -> {
                            title = "پیگیری درخواست تسهیلات"
                            desc =
                                "با پیگیری درخواست تسهیلات ، پس از تأییدیه با مراجعه به شعبه تسهیلات خود را دریافت کنید"
                        }
                        4 -> {
                            title = "دریافت راهنمای کاربری"
                            desc =
                                "برای آشنایی با روند ثبت درخواست تسهیلات می توانید فایل را بارگزاری کنید"
                        }
                    }
                    titles.add(title)
                    descs.add(desc)
                }
                imagePagerAdapter = AdapterImagePager(imageIds, titles, descs)

            }.lparams {
                width = matchParent
                height = 230.dip()
            }
            slider.adapter = imagePagerAdapter
        }.lparams {
            bottomToBottom = ConstraintSet.PARENT_ID
            endToEnd = ConstraintSet.PARENT_ID
            horizontalBias = 0.491f
            startToStart = ConstraintSet.PARENT_ID
            topToTop = ConstraintSet.PARENT_ID
            verticalBias = 0.115f
            topMargin = 10.dip()
        }
    }
}


fun _LinearLayout.assignToObject() {
    val recyclerView = recyclerView {}
    runOnUi {
        val cursor = G.database!!.rawQuery("SELECT * FROM products", null)
        while (cursor.moveToNext()) {
            val proStruct = ProductsStructe()
            proStruct.pro_assetImage_id = cursor.getInt(cursor.getColumnIndex("product_id"))
            proStruct.pic_name = cursor.getString(cursor.getColumnIndex("product_name"))
            G.documentsList.add(proStruct)
        }
        cursor.close()
    }
    recyclerView.layoutManager =
        LinearLayoutManager(G.context, LinearLayoutManager.HORIZONTAL, false)
    val adapterPeroductRecycler = AdapterRecyclerProducts(G.documentsList)
    recyclerView.adapter = adapterPeroductRecycler
    adapterPeroductRecycler.notifyDataSetChanged()
}

private fun populateProducts() {
    runOnUi {
        try {
            for (i in 0..9) {
                val prodactName = "name$i"
                val productLogoUrl = "url$i"
                val productId: Int = DBHelper(G.database!!).insert(
                    "products",
                    arrayOf("product_name", "product_logoUrl"),
                    arrayOf<Any>(prodactName, productLogoUrl)
                )
                for (j in 0..2) {
                    val customer_name = "customer_name#$j" + "product_name#$i"
                    val customer_famili = "customer_famili#$j" + "product_name#$i"
                    val customer_phoneNumber = "customer_phoneNumber#$j" + "product_name#$i"
                    DBHelper(G.database!!).insert(
                        "customers",
                        arrayOf(
                            "product_id",
                            "customer_name",
                            "customer_famili",
                            "customer_phoneNumber"
                        ),
                        arrayOf<Any>(
                            productId,
                            customer_name,
                            customer_famili,
                            customer_phoneNumber
                        )
                    )
                }
            }
        } catch (e: SQLiteConstraintException) {
            Debug.info("myLog : ${e.message} ")
        }
    }
}

fun truncate() {

    runOnUi {
        DBHelper(G.database!!).truncate("products")
        DBHelper(G.database!!).truncate("customers")
    }
}

fun _LinearLayout.fakeData() {
    val recyclerView = recyclerView {}
    for (i in 0..49) {
        val proStruct = ProductsStructe()
        proStruct.pro_assetImage_id = i
        proStruct.pic_name = "name #$i"

        G.documentsList.add(proStruct)
    }
    recyclerView.layoutManager =
        LinearLayoutManager(G.context, LinearLayoutManager.HORIZONTAL, false)
    val adapterPeroductRecycler = AdapterRecyclerProducts(G.documentsList)
    recyclerView.adapter = adapterPeroductRecycler
    adapterPeroductRecycler.notifyDataSetChanged()
}

fun _LinearLayout.fakeDatabaceSqlite() {
    runOnUi {
        try {
            for (i in 1..10) {

                val prodactName = "name$i"
                val productLogoUrl = "url$i"
                G.database?.execSQL("INSERT INTO 'products'('product_name','product_logoUrl') VALUES ('" + prodactName + "','" + productLogoUrl + "')")
                val cursor: Cursor? =
                    G.database?.rawQuery("SELECT last_insert_rowid() AS lastId", null)
                cursor?.moveToFirst()
                val productId = cursor?.getInt(cursor.getColumnIndex("lastId"))
                cursor?.close()
                Debug.info("lastId----$productId")

                for (j in 1..3) {
                    val customer_name = "customer_name#$j" + "product_name#$i"
                    val customer_famili = "customer_famili#$j" + "product_name#$i"
                    val customer_phoneNumber = "customer_phoneNumber#$j" + "product_name#$i"

                    G.database?.execSQL("INSERT INTO 'customers'('product_id','customer_name','customer_famili','customer_phoneNumber') VALUES ('" + productId + "','" + customer_name + "','" + customer_famili + "','" + customer_phoneNumber + "')")

                }
            }
        } catch (e: SQLiteConstraintException) {
            Debug.info("myLog : ${e.message} ")
        }
    }
}

fun readData() {
    runOnUi {
        val cursor: Cursor? = G.database?.rawQuery("SELECT * FROM products", null)
        DBHelper(G.database!!).dumpCursor(cursor!!)

        cursor.close()
    }
}