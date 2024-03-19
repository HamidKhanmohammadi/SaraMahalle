package project.module.lang

abstract class Lang {
  open val hello_world = "hello_world"
  open val permissionRequiredMessage = "Please grant all permission required.\nIf not, app won't work normally."

  open val button_ok = "OK"
  open val button_cancel = "CANCEL"
  open val button_yes = "YES"
  open val button_no = "NO"

  open val appName = "loan"
  open val version = "version: "

  open val menu_products = "Products"
  open val menu_settings = "Settings"
  open val menu_application_category = "Application"
  open val menu_about = "About"
  open val menu_feedback = "Feedback"
  open val menu_about_category = "About Application"
}