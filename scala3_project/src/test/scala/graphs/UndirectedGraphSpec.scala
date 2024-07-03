
package graphs
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import zio.json._
import json.GraphJson._


class UndirectedGraphSpec extends AnyFlatSpec with Matchers {

  
  "An UndirectedGraph" should "correctly add and remove edges" in {
    val graph = UndirectedGraph(Set(1, 2, 3), Set((1, 2)))
    val updatedGraph = graph.addEdge((2, 3))

    updatedGraph.getEdges() should contain allOf ((1, 2), (2, 3))
    updatedGraph.getNeighbors(1) should contain only 2
    updatedGraph.getNeighbors(2) should contain allOf (1, 3)

    val removedEdgeGraph = updatedGraph.removeEdge((1, 2))
    removedEdgeGraph.getEdges() should not contain (1, 2)
  }

  it should "perform DFS correctly" in {
    val graph = UndirectedGraph(Set(1, 2, 3, 4), Set((1, 2), (1, 3), (2, 4), (3, 4)))
    val dfsResult = graph.dfs(1)
    dfsResult shouldEqual List(1, 2, 4, 3)
  }

  it should "perform BFS correctly" in {
    val graph = UndirectedGraph(Set(1, 2, 3, 4), Set((1, 2), (1, 3), (2, 4)))
    val bfsResult = graph.bfs(1)
    bfsResult shouldEqual List(1, 2, 3, 4)
  }

  it should "generate correct DOT format" in {
      val graph = UndirectedGraph(Set(1, 2, 3), Set((1, 2), (2, 3)))
      val dotFormat = graph.toDot
      dotFormat shouldEqual
        """graph G {
          |  "1" -- "2";
          |  "2" -- "3"
          |}""".stripMargin
    }

    it should "correctly encode to JSON" in {
    val graph = UndirectedGraph(Set(1, 2, 3), Set((1, 2), (2, 3)))
    val json = graph.toJson
    json shouldEqual """{"vertices":[1,2,3],"edges":[[1,2],[2,3]]}"""
  }

  it should "correctly decode from JSON" in {
    val json = """{"vertices":[1,2,3],"edges":[[1,2],[2,3]]}"""
    val decodedGraph = json.fromJson[UndirectedGraph[Int]]
    decodedGraph should be (Right(UndirectedGraph(Set(1, 2, 3), Set((1, 2), (2, 3)))))
  }

  it should "decode JSON to UndirectedGraph using decodeJsonToUndirectedGraph" in {
    val json = """{"vertices":[1,2,3],"edges":[[1,2],[2,3]]}"""
    val decodedGraph = decodeJsonToUndirectedGraph(json)
    decodedGraph should be (Right(UndirectedGraph(Set(1, 2, 3), Set((1, 2), (2, 3)))))
  }

}
