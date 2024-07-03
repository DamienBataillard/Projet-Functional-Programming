import GraphImplicits._

object Main extends App {
  val directedGraph = DirectedGraph(Set(1, 2, 3), Set((1, 2), (2, 3)))
  val undirectedGraph = UndirectedGraph(Set(1, 2, 3), Set((1, 2), (2, 3)))
  val weightedGraph = WeightedGraph(Set(1, 2, 3), Map((1, 2) -> 5, (2, 3) -> 10))

  println("Directed Graph DOT representation:")
  println(directedGraph.toDot)

  println("Undirected Graph DOT representation:")
  println(undirectedGraph.toDot)

  println("Weighted Graph DOT representation:")
  println(weightedGraph.toDot)
}
