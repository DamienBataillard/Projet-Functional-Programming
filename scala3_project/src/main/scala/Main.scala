import java.io._

object Main extends App {
  val directedGraph = DirectedGraph(Set(1, 2, 3), Set((1, 2), (2, 3)))
  val undirectedGraph = UndirectedGraph(Set(1, 2, 3), Set((1, 2), (2, 3)))

  saveDotToFile(directedGraph.toDot, "directedGraph.dot")
  saveDotToFile(undirectedGraph.toDot, "undirectedGraph.dot")

  generateGraphImage("directedGraph.dot", "directedGraph.png")
  generateGraphImage("undirectedGraph.dot", "undirectedGraph.png")

  def saveDotToFile(dot: String, filename: String): Unit = {
    val pw = new PrintWriter(new File(filename))
    pw.write(dot)
    pw.close()
  }

  def generateGraphImage(dotFile: String, outputFile: String): Unit = {
    try {
      val runtime = Runtime.getRuntime
      val process = runtime.exec(s"dot -Tpng $dotFile -o $outputFile")
      val exitCode = process.waitFor()
      if (exitCode != 0) {
        val errorStream = process.getErrorStream
        val errorMsg = scala.io.Source.fromInputStream(errorStream).mkString
        println(s"Error generating graph image: $errorMsg")
      } else {
        println(s"Graph image generated: $outputFile")
      }
    } catch {
      case e: IOException =>
        println(s"Error executing dot command: ${e.getMessage}")
    }
  }
}
