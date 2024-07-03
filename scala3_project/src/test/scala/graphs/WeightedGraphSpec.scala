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

    it should "correctly encode to JSON" in {
    val graph = WeightedGraph(Set(1, 2, 3), Map((1, 2) -> 5.0, (2, 3) -> 3.0))
    val json = graph.toJson
    json shouldEqual """{"vertices":[1,2,3],"edges":[[[1,2],5.0],[[2,3],3.0]]}"""
  }

  it should "correctly decode from JSON" in {
    val json = """{"vertices":[1,2,3],"edges":[[[1,2],5.0],[[2,3],3.0]]}"""
    val decodedGraph = json.fromJson[WeightedGraph[Int, Double]]
    decodedGraph should be (Right(WeightedGraph(Set(1, 2, 3), Map((1, 2) -> 5.0, (2, 3) -> 3.0))))
  }

  it should "decode JSON to WeightedGraph using decodeJsonToWeightedGraph" in {
    val json = """{"vertices":[1,2,3],"edges":[[[1,2],5.0],[[2,3],3.0]]}"""
    val decodedGraph = decodeJsonToWeightedGraph(json)
    decodedGraph should be (Right(WeightedGraph(Set(1, 2, 3), Map((1, 2) -> 5.0, (2, 3) -> 3.0))))
  }
}
