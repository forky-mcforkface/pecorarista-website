package example

object Main extends App {

  val parser = new Parser()
  val s = "太郎は二郎にこの本を渡した．"

  parser.parseToChunks(s).zipWithIndex.foreach { case (c, i) =>
    val dest = c.link
    c.tokens.zipWithIndex.foreach { case (t, _) =>
      println(s"${i}→${dest} ${t.normalizedSurface}")
    }
  }
}
