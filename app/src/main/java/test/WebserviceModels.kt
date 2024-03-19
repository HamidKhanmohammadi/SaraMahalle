package ir.mebank.loan.core.module

data class ProfileInput(
  var count: Int = 10,
  var gender: String = "both",
  var type: String = "brief"
)