package graphs

import zio._
import zio.console._
import zio.json._
import graphs._
import json.GraphJson._
import viz._

object Main extends App {

  val directedGraph = DirectedGraph(Set(1, 2, 3), Set((1, 2), (2, 3)))
  val undirectedGraph = UndirectedGraph(Set(1, 2, 3), Set((1, 2), (2, 3)))
  val weightedGraphTest = WeightedGraph(Set(1, 2, 3), Map((1, 2) -> 5, (2, 3) -> 10))

  override def run(args: List[String]): URIO[ZEnv, ExitCode] = {
    val graphApp = for {
      _ <- putStrLn("Directed Graph DOT representation:")
      _ <- putStrLn(directedGraph.toDot)

      _ <- putStrLn("Undirected Graph DOT representation:")
      _ <- putStrLn(undirectedGraph.toDot)

      _ <- putStrLn("Weighted Graph DOT representation:")
      _ <- putStrLn(weightedGraphTest.toDot)

      _ <- putStrLn(weightedGraphTest.dijkstra(1).toString)
      _ <- putStrLn(weightedGraphTest.floydWarshall.toString)

      // Example JSON string for UndirectedGraph
      val undirectedJson = """{"vertices":[1,2,3,4],"edges":[[1,2],[1,3],[2,4],[3,4]]}"""

      // Decode the JSON string into UndirectedGraph
      _ <- decodeJsonToUndirectedGraph(undirectedJson) match {
        case Right(graph) => putStrLn(s"Successfully decoded undirected graph: $graph")
        case Left(error)  => putStrLn(s"Failed to decode JSON: $error")
      }

      // Example JSON string for WeightedGraph
      val weightedJson = """{"vertices":[1,2,3,4],"edges":[[[1,2],5.0],[[1,3],2.5],[[2,4],7.1],[[3,4],4.3]]}"""

      // Decode the JSON string into WeightedGraph
      _ <- decodeJsonToWeightedGraph(weightedJson) match {
        case Right(graph) => putStrLn(s"Successfully decoded weighted graph: $graph")
        case Left(error)  => putStrLn(s"Failed to decode JSON: $error")
      }

      // Encode a WeightedGraph to JSON string
      val weightedGraph = WeightedGraph(Set(1, 2, 3, 4), Map((1, 2) -> 5.0, (1, 3) -> 2.5, (2, 4) -> 7.1, (3, 4) -> 4.3))
      val encodedWeightedJson = weightedGraph.toJson
      _ <- putStrLn(s"Encoded weighted graph JSON: $encodedWeightedJson")
    } yield ()

    graphApp.exitCode
  }

  def decodeJsonToUndirectedGraph(jsonString: String): Either[String, UndirectedGraph[Int]] = {
    jsonString.fromJson[UndirectedGraph[Int]]
  }

  def decodeJsonToWeightedGraph(jsonString: String): Either[String, WeightedGraph[Int, Double]] = {
    jsonString.fromJson[WeightedGraph[Int, Double]]
  }
}

  println(directedGraph.hasCycle())
  println(directedGraph.topologicalSort())


  /*

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
  println(s"Encoded weighted graph JSON: $encodedWeightedJson")*/
}
