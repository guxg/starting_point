package code {
  package snippet {

    import _root_.scala.xml.{ NodeSeq, Text }
    import _root_.net.liftweb.util._
    import _root_.net.liftweb.common._
    import _root_.java.util.Date
    import code.lib._
    import Helpers._
    import net.liftweb.http.RenderFuncDispatch
    import net.liftweb.http.StatefulSnippet
    import net.liftweb.http.js.JsCmd
    import net.liftweb.http.SHtml
    import org.apache.http.client.methods.HttpGet
    import org.apache.http.impl.client.DefaultHttpClient
    import java.net.URI
    import org.apache.http.client.HttpResponseException
    import net.liftweb.http.js.JE.JsRaw
    import net.liftweb.http.js.JsCmds._
    import scala.xml.XML
    import org.apache.http.util.EntityUtils
    import net.liftweb.http.js.JE.ValById

    class HelloWorld extends StatefulSnippet with RenderFuncDispatch with Loggable {

      def zipS(z: String): JsCmd = {

        val httpclient = new DefaultHttpClient()

        val uri = new URI("http://zip.cgis.biz/xml/zip.php?zn=%s".format(z))

        val httpGet = new HttpGet(uri);

        val cmd = tryo[JsCmd] { onError: Throwable => logger.error(onError) } {

          logger.info("<<" + uri.toString())
          val response = httpclient.execute(httpGet)
          logger.info(">>" + response)

          val entity = response.getStatusLine.getStatusCode match {
            case 200 => response.getEntity
            case code => throw new HttpResponseException(code, response.getStatusLine.toString)
          }

          val res = XML.loadString(EntityUtils.toString(entity))

          httpclient.getConnectionManager.shutdown

          (res \ "ADDRESS_value").map {
            address =>
              JsRaw("$('#custAddr').val('%s %s');".format((address \\ "@state").text, (address \\ "@city").text))
          }.head

        }
        cmd.openOr(JsRaw(Noop))
      }

      var zipCode = "1030000";
      var address  = "";

      def render = {

        "name=zipcode" #> SHtml.text(zipCode, zipCode = _, "id" -> "custZips") &
          "name=postSearch [onclick]" #> SHtml.ajaxCall(ValById("custZips"), zipS _) &
        "name=address" #> SHtml.text(address, address = _, "id" -> "custAddr") 

      }
    }

  }
}
