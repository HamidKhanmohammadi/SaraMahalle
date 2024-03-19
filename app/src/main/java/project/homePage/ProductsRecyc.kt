package project.homePage

import com.squareup.moshi.JsonClass
import project.madule.Debug
import project.madule.Webservice
import project.main.Endpoints


fun testWebservice3() {
    val endpoint = Endpoints.productWevServiceEndpoint
    val webservice = Webservice(
        endpoint, resource = "/news",
        output = ProductRecycInfo.Products::class.java
    )
    Debug.info("Responce Received31==== " + webservice)
    webservice.request {
        success = { call, response, content, entity ->
            Debug.info("Responce Received32==== " + call)
            Debug.info("Responce Received33==== " + response)
            Debug.info("Responce Received34 " + content)
        }
        fail = { call, e ->
            Debug.info("Webservice Failed2 " + e.message)
        }
    }
}

@JsonClass(generateAdapter = true, generator = "sealed:type")
class ProductRecycInfo {
    data class Products(
        val feed: Array<ProductsInfo>,
        var status: Int? = null
    )

    @JsonClass(generateAdapter = true)
    data class ProductsInfo(
        val product_id: String? = null,
        val product_name: String? = null,
        val product_logoUrl: String? = null
    )
}