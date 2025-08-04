object ApplicationTypes {
  case class Document(
    uri: String,
    title: String,
    retrieved: Long,
    content: String
  )
  case class UpdateDocumentRequest(
    uri: String,
    title: Option[String],
    retrieved: Option[Long],
    content: Option[String]
  )
}
