object Main extends App with Route {

  implicit val system = ActorSystem()
  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val akkaClient = AkkaHttpClient(AkkaHttpClientSettings(Seq("localhost:9200")))
  val elasticClient = ElasticClient(akkaClient)
  val bindingFuture = Http().bindAndHandle(routes, "localhost", 8080)

  println(s"Server online at http://localhost:8080/.\nPress Return to exit.")

  StdIn.readLine()
  elasticClient.close()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete { _ => system.terminate() }

}
