package project.main

import okhttp3.HttpUrl.Companion.toHttpUrl
import project.madule.Debug
import project.madule.Endpoint


class Endpoints {
  companion object {
    val testService = Endpoint().apply {
      url = "https://ware.uncox.com/api/profile"
      fail = { call, e -> Debug.info("Endpoint Failed " + e.message) }
    }

    val loanBranch = Endpoint().apply {
      url = "https://loan.mebank.ir/webservice"
      fail = { call, e -> Debug.info("loanBranche Failed : " + e.message) }
    }

    val productWevServiceEndpoint = Endpoint().apply {
      url = "https://192.168.43.100/mahalleh"
      fail = { call, e -> Debug.info("Endpoint Failed " + e.message) }
    }
  }
}