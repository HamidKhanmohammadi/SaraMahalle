package project.activity

import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.powrolx.saramahalle.R
import ir.mebank.loan.core.module.ProfileInput
import ir.mebank.loan.core.module.ProfileResponse
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.viewPager
import project.adapter.AdapterImagePager
import project.adapter.AdapterRecyclerProducts
import project.adapter.ProductsStructe
import project.madule.*
import project.madule.DrawerHelper.Companion.setDrawerView
import project.main.Endpoints
import project.main.G
import project.main.S
import project.module.runOnUi
import project.styleAndUI.*


class MainActivity : XActivity() {
    private val handler = Handler()
    private var runnable: Runnable? = null

    var imageId: Int = 0
    lateinit var imagePagerAdapter: AdapterImagePager
    lateinit var slider: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDrawerView(HomePage())
        //testWebservice2()
        //testWebservice3()
    }

    override fun onStart() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                imageId++
                if (imageId >= imagePagerAdapter.count) {
                    imageId = 0
                }
                slider.currentItem = imageId
                runnable = this
                handler.postDelayed(runnable, 5000)
            }
        }, 5000)
        super.onStart()
    }

    fun HomePage() = makeView {
        lateinit var title: String
        lateinit var desc: String
        val imageIds = ArrayList<Int>()
        val titles = ArrayList<String>()
        val descs = ArrayList<String>()

        verticalLayout {
            scrollView {
                lparams {
                    width = matchParent
                    height = matchParent
                }
                verticalLayout {
                    lparams {
                        width = matchParent
                        height = matchParent
                        gravity = Gravity.CENTER
                    }
//***********************view pager*****************************
                    verticalLayout {
                        lparams {
                            width = matchParent
                            height = wrapContent
                            greenLayout()
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
                                    /* when (imageIds.size) {
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
                                     descs.add(desc)**/
                                }

                                imagePagerAdapter = AdapterImagePager(imageIds, titles, descs)
                            }.lparams {
                                width = matchParent
                                height = 230.dip()
                            }
                            slider.adapter = imagePagerAdapter
                            addView(PagerIndicator(this.context))
                        }
                    }
//***********************recycler product*****************************
                    verticalLayout {
                        backgroundColor = S.sliderBackground
                        textView("پیشنهادات ویژه محلی") { txtdescLoan() }
                    }.applyRecursively {
                        when (it) {
                            is TextView -> it.setTypeface(
                                G.defaultFont,
                                Typeface.ITALIC + Typeface.BOLD
                            )
                        }
                    }
                    verticalLayout {
                        backgroundResource = R.drawable.image_recyc_back_layout
                        //truncate()
                        //populateProducts()
                        //assignToObject()

                        populateProductsServer()
                    }
//***********************maping in app*****************************
                    verticalLayout {
                        lparams {
                            width = matchParent
                            height = matchParent
                            wightLayout()
                        }
                        mspace(0.3f)
                        linearLayout {
                            lparams {
                                width = matchParent
                                height = matchParent
                                topMargin = 2.dip()
                                leftMargin = 2.dip()
                                rightMargin = 2.dip()
                                bottomMargin = 2.dip()
                            }
                            verticalLayout {
                                lparams {
                                    maping()
                                    backgroundResource = R.drawable.style_bck_layout
                                }
                                textView("خدمات شهری") {
                                    txtTitleLoan()
                                }.lparams {
                                    width = matchParent
                                    height = matchParent
                                }
                            }.onClick {
                                myIntentFinished(CityServicesActivity())
                            }
                            verticalLayout {
                                lparams {
                                    maping()
                                    backgroundResource = R.drawable.style_bck_layout
                                }
                                textView("بانک ها") {
                                    txtTitleLoan()
                                }.lparams {
                                    width = matchParent
                                    height = matchParent
                                }
                            }.onClick {
                                myIntentFinished(BanksActivity())
                            }
                            verticalLayout {
                                lparams {
                                    maping()
                                    backgroundResource = R.drawable.style_bck_layout
                                }
                                textView("املاک") {
                                    txtTitleLoan()
                                }.lparams {
                                    width = matchParent
                                    height = matchParent
                                }
                            }.onClick {
                                myIntentFinished(CityServicesActivity())
                            }
                        }
                        mspace(0.3f)
                        linearLayout {
                            lparams {
                                width = matchParent
                                height = matchParent
                                topMargin = 2.dip()
                                leftMargin = 2.dip()
                                rightMargin = 2.dip()
                                bottomMargin = 2.dip()
                            }
                            verticalLayout {
                                lparams {
                                    maping()
                                    backgroundResource = R.drawable.style_bck_layout
                                }
                                textView("خدمات شهری") {
                                    txtTitleLoan()
                                }.lparams {
                                    width = matchParent
                                    height = matchParent
                                }
                            }.onClick {
                                myIntentFinished(CityServicesActivity())
                            }
                            verticalLayout {
                                lparams {
                                    maping()
                                    backgroundResource = R.drawable.style_bck_layout
                                }
                                textView("بانک ها") {
                                    txtTitleLoan()
                                }.lparams {
                                    width = matchParent
                                    height = matchParent
                                }
                            }.onClick {
                                myIntentFinished(CityServicesActivity())
                            }
                            verticalLayout {
                                lparams {
                                    maping()
                                    backgroundResource = R.drawable.style_bck_layout
                                }
                                textView("املاک") {
                                    txtTitleLoan()
                                }.lparams {
                                    width = matchParent
                                    height = matchParent
                                }
                            }.onClick {
                                myIntentFinished(CityServicesActivity())
                            }
                        }
                        mspace(0.3f)
                        linearLayout {
                            lparams {
                                width = matchParent
                                height = matchParent
                                topMargin = 2.dip()
                                leftMargin = 2.dip()
                                rightMargin = 2.dip()
                                bottomMargin = 2.dip()
                            }
                            verticalLayout {
                                lparams {
                                    maping()
                                    backgroundResource = R.drawable.style_bck_layout
                                }
                                textView("خدمات شهری") {
                                    txtTitleLoan()
                                }.lparams {
                                    width = matchParent
                                    height = matchParent
                                }
                            }.onClick {
                                myIntentFinished(CityServicesActivity())
                            }
                            verticalLayout {
                                lparams {
                                    maping()
                                    backgroundResource = R.drawable.style_bck_layout
                                }
                                textView("بانک ها") {
                                    txtTitleLoan()
                                }.lparams {
                                    width = matchParent
                                    height = matchParent
                                }
                            }.onClick {
                                myIntentFinished(CityServicesActivity())
                            }
                            verticalLayout {
                                lparams {
                                    maping()
                                    backgroundResource = R.drawable.style_bck_layout
                                }
                                textView("املاک") {
                                    txtTitleLoan()
                                }.lparams {
                                    width = matchParent
                                    height = matchParent
                                }
                            }.onClick {
                                myIntentFinished(CityServicesActivity())
                            }
                        }
                        mspace(0.3f)
                        linearLayout {
                            lparams {
                                width = matchParent
                                height = matchParent
                                topMargin = 2.dip()
                                leftMargin = 2.dip()
                                rightMargin = 2.dip()
                                bottomMargin = 2.dip()
                            }
                            verticalLayout {
                                lparams {
                                    maping()
                                    backgroundResource = R.drawable.style_bck_layout
                                }
                                textView("خدمات شهری") {
                                    txtTitleLoan()
                                }.lparams {
                                    width = matchParent
                                    height = matchParent
                                }
                            }.onClick {
                                myIntentFinished(CityServicesActivity())
                            }
                            verticalLayout {
                                lparams {
                                    maping()
                                    backgroundResource = R.drawable.style_bck_layout
                                }
                                textView("بانک ها") {
                                    txtTitleLoan()
                                }.lparams {
                                    width = matchParent
                                    height = matchParent
                                }
                            }.onClick {
                                myIntentFinished(CityServicesActivity())
                            }
                            verticalLayout {
                                lparams {
                                    maping()
                                    backgroundResource = R.drawable.style_bck_layout
                                }
                                textView("املاک") {
                                    txtTitleLoan()
                                }.lparams {
                                    width = matchParent
                                    height = matchParent
                                }
                            }.onClick {
                                myIntentFinished(CityServicesActivity())
                            }
                        }
                    }
                }
            }
        }.applyRecursively {
            when (it) {
                is TextView -> it.setTypeface(G.defaultFont, Typeface.ITALIC + Typeface.BOLD)
            }
        }
    }
}

fun _LinearLayout.populateProductsServer() {
    val endpoint = Endpoints.testService
    val webservice = Webservice(
        endpoint, resource = "/generate",
        output = ProfileResponse.Response::class.java
    )
    Debug.info("Responce Received generate:" + webservice)
    webservice.request {
        data = ProfileInput(
            count = 10,
            gender = "both",
            type = "brief"
        )
        success = { call, response, content, entity ->
            runOnUi {
                G.documentsList.clear()
                try {

                    for (i in 0..9) {
                        val proStruct = ProductsStructe()
                        proStruct.pic_name = entity!!.feeds!!.profiles[i].firstname!!
                        proStruct.pic_detaile = entity.feeds!!.profiles[i].lastname!!

                        Debug.info("[$i] firstName:${proStruct.pic_name} &&& lastName:${proStruct.pic_detaile}")

                        G.documentsList.add(proStruct)
                    }
                    val recyclerView = recyclerView {}
                    recyclerView.layoutManager = LinearLayoutManager(G.context, LinearLayoutManager.HORIZONTAL, false)
                    val adapterPeroductRecycler = AdapterRecyclerProducts(G.documentsList)
                    recyclerView.adapter = adapterPeroductRecycler
                    adapterPeroductRecycler.notifyDataSetChanged()

                } catch (e: SQLiteConstraintException) {
                    Debug.info("myLog : ${e.message} ")
                }
            }

        }
        fail = { call, e -> Debug.info("Webservice Failed1 " + e?.message) }
    }

}