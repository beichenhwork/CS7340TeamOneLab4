// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/c18jo/OneDrive/Desktop/Lab 4/Frontend/conf/routes
// @DATE:Mon Apr 05 20:18:10 MDT 2021


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
