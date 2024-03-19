package project.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.AnkoContext
import project.activity.ProdactDetail
import project.madule.AssetImageReader
import project.madule.myIntent
import project.main.G
import project.styleAndUI.RecyProductsUI

class AdapterRecyclerProducts(var list: ArrayList<ProductsStructe>) :
    RecyclerView.Adapter<AdapterRecyclerProducts.ProductsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(RecyProductsUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val item = list[position]

        val image = AssetImageReader.getImage("male/male_" + item.pro_assetImage_id + ".jpg")
        holder.pro_image.setImageDrawable(image)
        holder.pro_name.text = item.pic_name
        holder.pro_layout.setOnClickListener {
            G.selectedProductsStructeList = item
            myIntent(ProdactDetail())
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var pro_layout: ViewGroup
        var pro_image: ImageView
        var pro_name: TextView

        init {
            pro_layout = itemView.findViewById(RecyProductsUI.pro_layout_id)
            pro_image = itemView.findViewById(RecyProductsUI.pro_imge_id)
            pro_name = itemView.findViewById(RecyProductsUI.pro_name_id)
        }
    }
}

data class ProductsStructe(
    var pro_assetImage_id: Int = 0,
    var pic_name: String? = null,

    var pic_detaile: String? = null,
    var prodact_rating: Float? = 0f
)