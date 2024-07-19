package graphs

case class DirectedGraph[T](vertices: Set[T], edges: Set[(T, T)]) extends Graph[T] {
  def getVertices(): Set[T] = vertices

  def getEdges(): Set[(T, T)] = edges

  def getNeighbors(vertex: T): Set[T] = {
    edges.collect { case (v1, v2) if v1 == vertex => v2 }
  }

  def addEdge(edge: (T, T)): DirectedGraph[T] = {
    copy(edges = edges + edge)
  }

  def removeEdge(edge: (T, T)): DirectedGraph[T] = {
    copy(edges = edges - edge)
  }

  def toDot: String = {
    val edgeStrings = edges.map { case (v1, v2) => s"""  "$v1" -> "$v2"""" }.mkString(";\n")
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
    bfsIterative(List(start), Set(start), List())
  }

  // Topological Sorting
  def topologicalSort(): List[T] = {
    def dfsTopo(vertex: T, visited: Set[T], stack: List[T]): (List[T], Set[T]) = {
      if (visited.contains(vertex)) (stack, visited)
      else {
        val neighbors = getNeighbors(vertex)
        val (newStack, newVisited) = neighbors.foldLeft((stack, visited + vertex)) { case ((stk, vis), neighbor) =>
          val (subStack, subVisited) = dfsTopo(neighbor, vis, stk)
          (subStack, subVisited)
        }
        (vertex :: newStack, newVisited)
      }
    }
    val (stack, _) = vertices.foldLeft((List[T](), Set[T]())) { case ((stk, vis), vertex) =>
      val (subStack, subVisited) = dfsTopo(vertex, vis, stk)
      (subStack, subVisited)
    }
    stack.reverse
  }

  // Cycle Detection
  def hasCycle(): Boolean = {
    def dfsCycle(vertex: T, visited: Set[T], recStack: Set[T]): Boolean = {
      if (recStack.contains(vertex)) true
      else if (visited.contains(vertex)) false
      else {
        val neighbors = getNeighbors(vertex)
        neighbors.exists(neighbor => dfsCycle(neighbor, visited + vertex, recStack + vertex))
      }
    }
    vertices.exists(vertex => dfsCycle(vertex, Set[T](), Set[T]()))
  }
}