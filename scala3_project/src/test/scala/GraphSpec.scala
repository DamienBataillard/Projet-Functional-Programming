import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GraphSpec extends AnyFlatSpec with Matchers {

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
}
