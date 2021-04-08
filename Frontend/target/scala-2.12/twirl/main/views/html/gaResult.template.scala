
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import scala.collection.JavaConverters._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import play.data._
import play.core.j.PlayFormsMagicForJava._

object gaResult extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[List[List[String]],play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(q1Metadata:List[List[String]]):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <title>Lab 3 Paper Selection</title>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

            <!-- Compiled and minified JavaScript -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
        <link rel="shortcut icon" type="image/x-icon" href="docs/images/favicon.ico" />
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.5.1/dist/leaflet.css" integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ==" crossorigin=""/>
        <script src="https://unpkg.com/leaflet@1.5.1/dist/leaflet.js" integrity="sha512-GffPMF3RvMeYyc1LWMHtK8EbPv0iNZ8/oTtHPx9/cc2ILxQ+u905qIwdpULaqDkyBKgOaB57QTMg7ztg8Jm2Og==" crossorigin=""></script>
    </head>
    <body>

        <header class="topbar">
            <h1 class="focused" style="font-size: 37px;
                color: black">
                <strong>Lab 4 Best Fit</strong>
            </h1>
            <h6><a href=""""),_display_(/*25.27*/routes/*25.33*/.HomeController.resultView()),format.raw/*25.61*/("""">  <- return</a></h6>
        </header>
        <div class="row" >
            <div class="card col s3 " >
                <div class="card-panel"  style="width: 120rem;">
                    <table border="1" >

                        """),_display_(/*32.26*/for(paperMetadata <- q1Metadata) yield /*32.58*/{_display_(Seq[Any](format.raw/*32.59*/("""
                            """),format.raw/*33.29*/("""<tr>
                                <td>Total evolution time </td>
                                <td>"""),_display_(/*35.38*/paperMetadata/*35.51*/.get(0)),format.raw/*35.58*/("""</td>
                            </tr>
                            <tr>
                                <td>Best solution</td>
                                <td>"""),_display_(/*39.38*/paperMetadata/*39.51*/.get(1)),format.raw/*39.58*/("""</td>
                            </tr>
                            <tr>
                                <td>Service contained</td>
                                <td>"""),_display_(/*43.38*/paperMetadata/*43.51*/.get(2)),format.raw/*43.58*/("""</td>
                            </tr>
                        """)))}),format.raw/*45.26*/("""
                    """),format.raw/*46.21*/("""</table>
                </div>
            </div>
        </div>
    </body>
</html>"""))
      }
    }
  }

  def render(q1Metadata:List[List[String]]): play.twirl.api.HtmlFormat.Appendable = apply(q1Metadata)

  def f:((List[List[String]]) => play.twirl.api.HtmlFormat.Appendable) = (q1Metadata) => apply(q1Metadata)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: 2021-04-08T13:28:22.140
                  SOURCE: /Users/beichenhu/Desktop/Service-Oriented Computing /Lab4/lab4/CS7340TeamOneLab4/Frontend/app/views/gaResult.scala.html
                  HASH: ff5cacb14edac4277f24f05e92634f9893e9409d
                  MATRIX: 963->1|1088->33|2534->1454|2549->1460|2598->1488|2864->1727|2912->1759|2951->1760|3008->1789|3140->1894|3162->1907|3190->1914|3382->2079|3404->2092|3432->2099|3628->2268|3650->2281|3678->2288|3774->2353|3823->2374
                  LINES: 28->1|33->2|56->25|56->25|56->25|63->32|63->32|63->32|64->33|66->35|66->35|66->35|70->39|70->39|70->39|74->43|74->43|74->43|76->45|77->46
                  -- GENERATED --
              */
          