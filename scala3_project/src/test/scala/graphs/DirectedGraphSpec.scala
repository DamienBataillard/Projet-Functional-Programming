package graphs
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import zio.json._
import json.GraphJson._


class DirectedGraphSpec extends AnyFlatSpec with Matchers {

  "A DirectedGraph" should "correctly add and remove edges" in {
    val graph = DirectedGraph(Set(1, 2, 3), Set((1, 2)))
    val updatedGraph = graph.addEdge((2, 3))

    updatedGraph.getEdges() should contain allOf ((1, 2), (2, 3))
    updatedGraph.getNeighbors(1) should contain only 2
    updatedGraph.getNeighbors(2) should contain only 3

    val removedEdgeGraph = updatedGraph.removeEdge((1, 2))
    removedEdgeGraph.getEdges() should not contain (1, 2)
  }

  it should "perform DFS correctly" in {
    val graph = DirectedGraph(Set(0,1, 2, 3, 4,5), Set((0, 1), (0, 2), (1, 5),(1, 4),(2, 3)))
    val dfsResult = graph.dfs(0)
    dfsResult should (equal(List(0, 1, 5, 4, 2, 3)) or equal(List(0, 1, 4, 5, 2, 3)))
  }

  it should "perform BFS correctly" in {
  val graph = DirectedGraph(Set(0,1, 2, 3, 4,5), Set((0, 1), (0, 2),(1,3), (2, 4), (2,5)))
  val bfsResult = graph.bfs(0)
  bfsResult should (equal(List(0, 1, 2, 3, 4, 5)) or equal(List(0, 1, 2, 3, 5, 4)))
  }

  it should "generate correct DOT format" in {
      val graph = DirectedGraph(Set(1, 2, 3), Set((1, 2), (2, 3)))
      val dotFormat = graph.toDot
      dotFormat shouldEqual
        """digraph G {
          |  "1" -> "2";
          |  "2" -> "3"
          |}""".stripMargin
    }

    it should "correctly encode to JSON" in {
    val graph = DirectedGraph(Set(1, 2, 3), Set((1, 2), (2, 3)))
    val json = graph.toJson
    json shouldEqual """{"vertices":[1,2,3],"edges":[[1,2],[2,3]]}"""
  }

  it should "correctly decode from JSON" in {
    val json = """{"vertices":[1,2,3],"edges":[[1,2],[2,3]]}"""
    val decodedGraph = json.fromJson[DirectedGraph[Int]]
    decodedGraph should be (Right(DirectedGraph(Set(1, 2, 3), Set((1, 2), (2, 3)))))
  }

  it should "decode JSON to DirectedGraph using decodeJsonToDirectedGraph" in {
    val json = """{"vertices":[1,2,3],"edges":[[1,2],[2,3]]}"""
    val decodedGraph = decodeJsonToDirectedGraph(json)
    decodedGraph should be (Right(DirectedGraph(Set(1, 2, 3), Set((1, 2), (2, 3)))))
  }

}
