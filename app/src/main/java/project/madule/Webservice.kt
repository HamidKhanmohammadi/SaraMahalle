package project.madule

import okhttp3.*
import project.main.Endpoints
import project.module.MethodBlock
import project.module.runOnUi
import java.io.IOException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class Endpoint {
  lateinit var url: String
  var timeout: Long = 5_000

  var success: (Call, Response, String?) -> Unit = { call, response, content -> }
  var fail: (Call, IOException) -> Unit = { call, e -> }
}


class Webservice<T : Any>(var endpoint: Endpoint, var resource: String, var output: Class<out T>) {
  var success: (Call, Response, String?, T?) -> Unit = { call, response, content, entity -> }
  var fail: (Call, IOException) -> Unit = { call, e -> }

  inner class Requester {
    var data: Any? = null
    var success: (Call, Response, String?, T?) -> Unit = { call, response, content, entity -> }
    var fail: (Call, IOException) -> Unit = { call, e -> }

    fun run(): Call {
      val client = initializeClient()
      val post = preparePostData(data)
      val request = Request.Builder()
        .url(endpoint.url + resource)
        .addHeader("Content-Type","application/json")
        .post(post)
        .build()

      val call = client.newCall(request)
      processResponse(call)
      return call
    }


    private fun initializeClient(): OkHttpClient {
      val client = OkHttpClient.Builder().apply {

        try {
          connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
          connectTimeout(endpoint.timeout, TimeUnit.MILLISECONDS)
          writeTimeout(endpoint.timeout, TimeUnit.MILLISECONDS)
          readTimeout(endpoint.timeout, TimeUnit.MILLISECONDS)
          // Create a trust manager that does not validate certificate chains
          val trustAllCerts:  Array<TrustManager> = arrayOf(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?){}
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate>  = arrayOf()
          })
          // Create an ssl socket factory with our all-trusting manager

          // Install the all-trusting trust manager
          val  sslContext = SSLContext.getInstance("SSL")
          sslContext.init(null, trustAllCerts, SecureRandom())


          val sslSocketFactory = SSLContext.getInstance("TLSv1.2").apply {
            val trustAllCerts = arrayOf(*trustAllCerts)
            init(null, trustAllCerts, SecureRandom())
          }.socketFactory
          sslSocketFactory(sslSocketFactory, trustAllCerts.first() as X509TrustManager)
          HostnameVerifier { hostname, session ->
            if (hostname.equals("http://192.168.43.100")) {
              true
            } else {
              false
            }
          }

        } catch (e: Exception) {
        }

      }.build()

      return client
    }


    private fun preparePostData(data: Any?): FormBody {
      val builder = FormBody.Builder()
      if (data != null) {
        val inputJson = Json.toMap(data)

        val it = inputJson.entries.iterator()
        while (it.hasNext()) {
          val pair = it.next() as Map.Entry<String, *>
          builder.add(pair.key, pair.value.toString())
        }
      }

      return builder.build()
    }

    private fun processResponse(call: Call) {
      call.enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
          val content = response.body?.string()

          var result: T? = null
          if (content != null) {
            result = Json.fromJson(content, output)
          }

          runOnUi {
            endpoint.success(call, response, content)
            this@Webservice.success(call, response, content, result)
            success(call, response, content, result)
          }
        }

        override fun onFailure(call: Call, e: IOException) {
          runOnUi {
            endpoint.fail(call, e)
            this@Webservice.fail(call, e)
            fail(call, e)
          }
        }
      })
    }
  }


  fun request(props: MethodBlock<Requester> = {}): Call {
    return Requester().apply(props).run()
  }
}

var hostnameVerifier =
  HostnameVerifier { hostname, session ->
    if (hostname.equals("192.168.43.100")) {
      true
    } else {
      false
    }
  }