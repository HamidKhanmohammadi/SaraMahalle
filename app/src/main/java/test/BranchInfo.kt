package test

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true, generator = "sealed:type")
class BranchInfo {
    @JsonClass(generateAdapter = true)
    data class Branches(
        val message: Array<BranchsInfo>
    )
    @JsonClass(generateAdapter = true)
    data class BranchsInfo(
        val id: String? = null,
        val fr_name: String? = null,
        val ciry_id: String? = null
    )
}