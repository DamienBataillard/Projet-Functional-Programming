package graphs

trait Graph[T] {
  def getVertices(): Set[T]
  def getEdges(): Set[(T, T)]
  def getNeighbors(vertex: T): Set[T]
  def addEdge(edge: (T, T)): Graph[T]
  def removeEdge(edge: (T, T)): Graph[T]
  def toDot: String
  def dfs(start: T): List[T]
  def bfs(start: T): List[T]
}

trait WeightedGraphTrait[T, W] extends Graph[T] {
  def addEdge(edge: (T, T), weight: W): WeightedGraph[T, W]
  def removeEdge(edge: (T, T)): WeightedGraph[T, W]
}

