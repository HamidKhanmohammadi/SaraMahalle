package project.madule

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.FormBody

@Suppress("UNCHECKED_CAST")
class Json {
  companion object {
    val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

    inline fun <reified T> toMap(data: T): Map<String, Any> {
      val adapter = moshi.adapter(T::class.java)
      return adapter.toJsonValue(data) as Map<String, Any>
    }

    inline fun <reified T> toFormBody(data: T): FormBody {
      val formBodyBuilder = FormBody.Builder()
      val map = Json.toMap(data)
      map.forEach { (key, value) ->
        formBodyBuilder.add(key, value.toString())
      }
      return formBodyBuilder.build()
    }

    fun <T : Any> fromJson(json: String, clazz: Class<out T>): T? {
      val adapter : JsonAdapter<@JvmSuppressWildcards T> = moshi.adapter(clazz) as JsonAdapter<T>
      return adapter.fromJson(json)
    }
  }
}