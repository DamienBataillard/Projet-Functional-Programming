
import scala.io.Source
import zio.json._
import graphs._
import json.GraphJson._
import viz._

object Main extends App {

  val directedGraph = DirectedGraph(Set(1, 2, 3), Set((1, 2), (2, 3)))
  val undirectedGraph = UndirectedGraph(Set(1, 2, 3), Set((1, 2), (2, 3)))
  val weightedGraphTest = WeightedGraph(Set(1, 2, 3), Map((1, 2) -> 5, (2, 3) -> 10))

  println("Directed Graph DOT representation:")
  println(directedGraph.toDot)

  println("Undirected Graph DOT representation:")
  println(undirectedGraph.toDot)

  println("Weighted Graph DOT representation:")
  println(weightedGraphTest.toDot)


  // Example JSON string for UndirectedGraph
  val undirectedJson = """{"vertices":[1,2,3,4],"edges":[[1,2],[1,3],[2,4],[3,4]]}"""

  // Decode the JSON string into UndirectedGraph
  def decodeJsonToUndirectedGraph(jsonString: String): Either[String, UndirectedGraph[Int]] = {
    jsonString.fromJson[UndirectedGraph[Int]]
  }

  decodeJsonToUndirectedGraph(undirectedJson) match {
    case Right(graph) => println(s"Successfully decoded undirected graph: $graph")
    case Left(error)  => println(s"Failed to decode JSON: $error")
  }

  // Example JSON string for WeightedGraph
  val weightedJson = """{"vertices":[1,2,3,4],"edges":[[[1,2],5.0],[[1,3],2.5],[[2,4],7.1],[[3,4],4.3]]}"""

  // Decode the JSON string into WeightedGraph
  def decodeJsonToWeightedGraph(jsonString: String): Either[String, WeightedGraph[Int, Double]] = {
    jsonString.fromJson[WeightedGraph[Int, Double]]
  }

  decodeJsonToWeightedGraph(weightedJson) match {
    case Right(graph) => println(s"Successfully decoded weighted graph: $graph")
    case Left(error)  => println(s"Failed to decode JSON: $error")
  }

  // Encode a WeightedGraph to JSON string
  val weightedGraph = WeightedGraph(Set(1, 2, 3, 4), Map((1, 2) -> 5.0, (1, 3) -> 2.5, (2, 4) -> 7.1, (3, 4) -> 4.3))
  val encodedWeightedJson = weightedGraph.toJson
  println(s"Encoded weighted graph JSON: $encodedWeightedJson")
}
