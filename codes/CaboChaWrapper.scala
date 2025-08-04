package cabochawrapper

import org.chasen.cabocha.{
  Parser => CaboChaParser,
  Chunk => CaboChaChunk,
  Tree => CaboChaTree,
  Token => CaboChaToken,
  FormatType
}

case class Token(
  surface: String,
  normalizedSurface: String,
  feature: String,
  namedEntity: Option[String],
  additionalInfo: Option[String]
)

case class Chunk(
  score: Float,
  link: Int,
  additionalInfo: Option[String],
  features: Seq[String],
  tokens: Seq[Token]
)

class Parser {

  try {
    System.loadLibrary("CaboCha")
  } catch {
    case _: UnsatisfiedLinkError => {
      println("Make sure that `CaboCha.jar` and `libCaboCha.so` are in `lib`.")
      System.exit(1)
    }
  }

  val parser = new CaboChaParser()

  def parseToChunks(s: String): Seq[Chunk] = {
    val tree: CaboChaTree = this.parser.parse(s)
    (0.toLong until tree.chunk_size()).map { i =>
      val chunk = tree.chunk(i)
      val features = (0.toLong until chunk.getFeature_list_size()).map { i =>
        chunk.feature_list(i)
      }
      val n = chunk.getToken_pos()
      val N = n + chunk.getToken_size()
      val tokens = (n until N).map { i =>
        tree.token(i).toToken()
      }
      Chunk(score = chunk.getScore(),
            link = chunk.getLink(),
            tokens = tokens,
            additionalInfo = Option(chunk.getAdditional_info()).filter(_ != null),
            features = features)
    }
  }

  implicit class ExtendedCaboChaToken(token: CaboChaToken) {
    def toToken(): Token =
      Token(surface = token.getSurface(),
            normalizedSurface = token.getNormalized_surface(),
            feature = token.getFeature(),
            namedEntity = Option(token.getNe()).filter(_ != null),
            additionalInfo = Option(token.getAdditional_info()).filter(_ != null)
      )
  }
}
