trait Graph[T] {
  def getVertices(): Set[T]
  def getEdges(): Set[(T, T)]
  def getNeighbors(vertex: T): Set[T]
  def addEdge(edge: (T, T)): Graph[T]
  def removeEdge(edge: (T, T)): Graph[T]

  def toDot: String
}

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
}

case class UndirectedGraph[T](vertices: Set[T], edges: Set[(T, T)]) extends Graph[T] {
  def getVertices(): Set[T] = vertices

  def getEdges(): Set[(T, T)] = edges

  def getNeighbors(vertex: T): Set[T] = {
    edges.collect {
      case (v1, v2) if v1 == vertex => v2
      case (v1, v2) if v2 == vertex => v1
    }
  }

  def addEdge(edge: (T, T)): UndirectedGraph[T] = {
    copy(edges = edges + edge)
  }

  def removeEdge(edge: (T, T)): UndirectedGraph[T] = {
    copy(edges = edges - edge)
  }

  def toDot: String = {
    val edgeStrings = edges.map { case (v1, v2) => s"""  "$v1" -- "$v2"""" }.mkString(";\n")
    s"""graph G {
       |$edgeStrings
       |}""".stripMargin
  }
}
