trait Route {

  implicit val elasticClient: ElasticClient
  implicit val executionContext: ExecutionContext

  val documentsIndexName = "documents"

  val routes = cors() {
    path(documentsIndexName) {
      get {
        parameter('term.?) { term =>
          complete(findDocuments(term))
        }
      } ~
        post {
          entity(as[UpdateDocumentRequest]) { request =>
            val message: Future[String] = updateDocument(request).map(_.getOrElse("Success!"))
            complete(message)
          }
        }
    }
  }

  private def findDocuments(term: Option[String]): Future[Seq[Document]] = ...

  private def updateDocument(request: UpdateDocumentRequest): Future[Option[String]] = ...

}
