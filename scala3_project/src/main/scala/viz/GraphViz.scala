package viz
import graphs._


object GraphImplicits {
  implicit class GraphViz[T](graph: Graph[T]) {
    def toDot: String = graph match {
      case g: DirectedGraph[T] =>
        val edgesStr = g.edges.foldLeft("") { (acc, edge) =>
          acc + s"""  "${edge._1}" -> "${edge._2}";\n"""
        }
        s"DerectedGraph {\n$edgesStr}"

      case g: UndirectedGraph[T] =>
        val edgesStr = g.edges.foldLeft("") { (acc, edge) =>
          acc + s"""  "${edge._1}" -- "${edge._2}";\n"""
        }
        s"UndirectedGraph {\n$edgesStr}"

      case g: WeightedGraph[T, _] =>
        val edgesStr = g.edges.foldLeft("") { (acc, edge) =>
          acc + s"""  "${edge._1._1}" -> "${edge._1._2}" [label="${edge._2}"];\n"""
        }
        s"WeightGraph{\n$edgesStr}"

      case _ => throw new UnsupportedOperationException("Unsupported graph type")
    }
  }
}
