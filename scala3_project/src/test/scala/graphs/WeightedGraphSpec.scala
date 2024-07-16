package graphs

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import zio.json._
import json.GraphJson._

class WeightedGraphSpec extends AnyFlatSpec with Matchers {

  "A WeightedGraph" should "correctly add and remove edges" in {
    val graph = WeightedGraph(Set(1, 2, 3), Map((1, 2) -> 5))
    val updatedGraph = graph.addEdge((2, 3), 10)

    updatedGraph.getEdges() should contain allOf ((1, 2), (2, 3))
    updatedGraph.getNeighbors(1) should contain only 2
    updatedGraph.getNeighbors(2) should contain only 3

    val removedEdgeGraph = updatedGraph.removeEdge((1, 2))
    removedEdgeGraph.getEdges() should not contain (1, 2)
  }

  it should "perform DFS correctly" in {
    val graph = WeightedGraph(Set(1, 2, 3, 4), Map((1, 2) -> 5, (1, 3) -> 3, (2, 4) -> 8, (3, 4) -> 2))
    val dfsResult = graph.dfs(1)
    dfsResult shouldEqual List(1, 2, 4, 3)
  }

  it should "perform BFS correctly" in {
    val graph = WeightedGraph(Set(1, 2, 3, 4), Map((1, 2) -> 5, (1, 3) -> 3, (2, 4) -> 8))
    val bfsResult = graph.bfs(1)
    bfsResult shouldEqual List(1, 2, 3, 4)
  }

  it should "generate correct DOT format" in {
    val graph = WeightedGraph(Set(1, 2, 3), Map((1, 2) -> 5, (2, 3) -> 10))
    val dotFormat = graph.toDot
    dotFormat shouldEqual
      """digraph G {
        |  "1" -> "2" [label="5"];
        |  "2" -> "3" [label="10"]
        |}""".stripMargin
  }

  it should "perform Dijkstra's algorithm correctly" in {
    val graph = WeightedGraph(Set(1, 2, 3, 4, 5), Map(
      (1, 2) -> 10,
      (1, 3) -> 3,
      (2, 3) -> 1,
      (3, 2) -> 4,
      (2, 4) -> 2,
      (3, 4) -> 8,
      (4, 5) -> 7,
      (3, 5) -> 2
    ))
    val dijkstraResult = graph.dijkstra(1)
    dijkstraResult shouldEqual Map(
      1 -> 0,
      2 -> 7,
      3 -> 3,
      4 -> 9,
      5 -> 5
    )
  }

  it should "perform Floyd-Warshall algorithm correctly" in {
    val graph = WeightedGraph(Set(1, 2, 3, 4), Map(
      (1, 2) -> 3,
      (2, 3) -> 1,
      (3, 4) -> 2,
      (4, 1) -> 1,
      (1, 3) -> 10
    ))

    val floydWarshallResult = graph.floydWarshall

    val expectedResult = Map(
      (1, 1) -> 0.0,
      (1, 2) -> 3.0,
      (1, 3) -> 4.0,
      (1, 4) -> 6.0,
      (2, 1) -> 4.0,
      (2, 2) -> 0.0,
      (2, 3) -> 1.0,
      (2, 4) -> 3.0,
      (3, 1) -> 3.0,
      (3, 2) -> 6.0,
      (3, 3) -> 0.0,
      (3, 4) -> 2.0,
      (4, 1) -> 1.0,
      (4, 2) -> 4.0,
      (4, 3) -> 5.0,
      (4, 4) -> 0.0
    )

    // Converting Maps to sorted lists for comparison
    floydWarshallResult.toList.sorted shouldEqual expectedResult.toList.sorted
  }

}
