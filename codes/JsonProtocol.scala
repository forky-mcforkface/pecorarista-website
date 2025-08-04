import ApplicationTypes._
import spray.json.DefaultJsonProtocol
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

trait JsonProtocol extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val documentFormat = jsonFormat4(Document)
  implicit val documentUpdateRequestFormat = jsonFormat4(UpdateDocumentRequest)
}
object JsonProtocol extends JsonProtocol
