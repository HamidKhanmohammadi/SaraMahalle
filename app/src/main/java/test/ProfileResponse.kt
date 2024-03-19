package ir.mebank.loan.core.module

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ProfileResponse {
    @JsonClass(generateAdapter = true)
    data class Response(
        var status: Int? = null,
        var message: String? = null,
        var feeds: Feeds? = null,
        var debugInfo: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class Feeds(
        var profiles: Array<Profiles>
    )

    @JsonClass(generateAdapter = true)
    data class Profiles(
        var firstname: String? = null,
        var lastname: String? = null,
        var gender: String? = null,
        var email: String? = null,
        var imageUrl: String? = null,
        var phone: String? = null
    )
}