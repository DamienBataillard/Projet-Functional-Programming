
case class WeightedGraph[T, W](vertices: Set[T], edges: Map[(T, T), W]) extends WeightedGraphTrait[T, W] {
  def getVertices(): Set[T] = vertices

  def getEdges(): Set[(T, T)] = edges.keySet

  def getNeighbors(vertex: T): Set[T] = {
    edges.collect { case ((v1, v2), _) if v1 == vertex => v2 }.toSet
  }

  def addEdge(edge: (T, T), weight: W): WeightedGraph[T, W] = {
    copy(edges = edges + (edge -> weight))
  }

  def removeEdge(edge: (T, T)): WeightedGraph[T, W] = {
    copy(edges = edges - edge)
  }

  override def addEdge(edge: (T, T)): WeightedGraph[T, W] = throw new UnsupportedOperationException("Use addEdge(edge: (T, T), weight: W) for weighted graphs")
  
  def toDot: String = {
    val edgeStrings = edges.map { case ((v1, v2), w) => s"""  "$v1" -> "$v2" [label="$w"]""" }.mkString(";\n")
    s"""digraph G {
       |$edgeStrings
       |}""".stripMargin
  }

  def dfs(start: T): List[T] = {
    def dfsRecursive(vertex: T, visited: Set[T]): (List[T], Set[T]) = {
      if (visited.contains(vertex)) (List(), visited)
      else {
        val neighbors = getNeighbors(vertex)
        val (result, newVisited) = neighbors.foldLeft((List(vertex), visited + vertex)) { case ((res, vis), neighbor) =>
          val (subRes, subVisited) = dfsRecursive(neighbor, vis)
          (res ++ subRes, subVisited)
        }
        (result, newVisited)
      }
    }
    dfsRecursive(start, Set())._1
  }
  def bfs(start: T): List[T] = {
    def bfsIterative(queue: List[T], visited: Set[T], result: List[T]): List[T] = {
      if (queue.isEmpty) result
      else {
        val current = queue.head
        val neighbors = getNeighbors(current).filterNot(visited.contains)
        bfsIterative(queue.tail ++ neighbors, visited + current, result :+ current)
      }
    }
    bfsIterative(List(start), Set(), List())
  }
}

