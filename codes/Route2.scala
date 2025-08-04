private def findDocuments(term: Option[String]): Future[Seq[Document]] = {
  val queryBody = term match {
    case Some(s) => matchQuery("content", s)
    case None => matchAllQuery()
  }
  elasticClient
    .execute(
      search(documentsIndexName).limit(20).query(queryBody)
    )
    .map { response =>
      response match {
        case RequestSuccess(status @ _, body @ _, headers @ _, result) =>
          result.hits.hits.to[Seq].map(_.to[Document])
        case _ => Seq.empty[Document]
      }
    }
}

private def updateDocument(request: UpdateDocumentRequest): Future[Option[String]] =
  elasticClient
    .execute {
      update(request.uri)
        .in(documentsIndexName)
        .doc(UpdateDocumentRequest(request.uri, request.title, request.retrieved, request.content))
    }
    .map { response =>
      response match {
        case RequestFailure(status @ _, body @ _, headers @ _, error) => Some(error.reason)
        case _ => None
      }
    }
