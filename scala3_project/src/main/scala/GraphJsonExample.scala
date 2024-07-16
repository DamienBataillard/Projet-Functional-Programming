import scala.io.Source
import zio.json._
import graphs._
import json.GraphJson._

object GraphJsonExample extends App {

  def readJsonFile(filePath: String): String = {
    val source = Source.fromResource(filePath)
    try source.mkString finally source.close()
  }

  // Example JSON file paths (relative to the resources directory)
  val directedGraphJsonPath = "directedGraph.json"
  val undirectedGraphJsonPath = "undirectedGraph.json"
  val weightedGraphJsonPath = "weightedGraph.json"

  // Read JSON content from files
  val directedGraphJson = readJsonFile(directedGraphJsonPath)
  val undirectedGraphJson = readJsonFile(undirectedGraphJsonPath)
  val weightedGraphJson = readJsonFile(weightedGraphJsonPath)

  println(directedGraphJson)
  println(undirectedGraphJson)
  println(weightedGraphJson)


  // Decode the JSON strings
  val directedGraph = decodeJsonToDirectedGraph(directedGraphJson)
  val undirectedGraph = decodeJsonToUndirectedGraph(undirectedGraphJson)
  //val weightedGraph = decodeJsonToWeightedGraph(weightedGraphJson)

  // Print the decoded graphs
  println(s"Directed Graph: $directedGraph")
  println(s"Undirected Graph: $undirectedGraph")
  //println(s"Weighted Graph: $weightedGraph")

  directedGraph match {
    case Right(graph) =>
      println(s"Vertices: ${graph.getVertices()}")
      println(s"Edges: ${graph.getEdges()}")
      println(s"Neighbors of vertex 1: ${graph.getNeighbors(1)}")
      println(s"DFS starting from vertex 1: ${graph.dfs(1)}")
      println(s"BFS starting from vertex 1: ${graph.bfs(1)}")
    case Left(error) => println(s"Failed to decode directed graph: $error")
  }

  undirectedGraph match {
    case Right(graph) =>
      println(s"Vertices: ${graph.getVertices()}")
      println(s"Edges: ${graph.getEdges()}")
      println(s"Neighbors of vertex 1: ${graph.getNeighbors(1)}")
      println(s"DFS starting from vertex 1: ${graph.dfs(1)}")
      println(s"BFS starting from vertex 1: ${graph.bfs(1)}")
    case Left(error) => println(s"Failed to decode undirected graph: $error")
  }
/*
  weightedGraph match {
    case Right(graph) =>
      println(s"Vertices: ${graph.getVertices()}")
      println(s"Edges: ${graph.getEdges()}")
      println(s"Neighbors of vertex 1: ${graph.getNeighbors(1)}")
      println(s"DFS starting from vertex 1: ${graph.dfs(1)}")
      println(s"BFS starting from vertex 1: ${graph.bfs(1)}")
    case Left(error) => println(s"Failed to decode weighted graph: $error")
  }*/
}
