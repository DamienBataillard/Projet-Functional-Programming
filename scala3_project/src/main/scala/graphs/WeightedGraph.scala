package graphs
import scala.collection.mutable
import scala.math.Numeric.Implicits._
import scala.math.Ordering.Implicits._

case class WeightedGraph[T, W: Numeric: Ordering](vertices: Set[T], edges: Map[(T, T), W]) extends WeightedGraphTrait[T, W] {
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

  def dijkstra(start: T): Map[T, W] = {
    val num = implicitly[Numeric[W]]
    val ord = implicitly[Ordering[W]]
    val dist = mutable.Map[T, W](start -> num.zero)
    val pq = mutable.PriorityQueue[(W, T)]((num.zero, start))(Ordering.by[(W, T), W](_._1).reverse)
    val visited = mutable.Set[T]()

    while (pq.nonEmpty) {
      val (currentDist, currentVertex) = pq.dequeue()
      if (!visited.contains(currentVertex)) {
        visited.add(currentVertex)
        for ((neighbor, weight) <- edges.collect { case ((`currentVertex`, v), w) => (v, w) }) {
          val newDist = num.plus(currentDist, weight)
          if (ord.lt(newDist, dist.getOrElse(neighbor, num.fromInt(Int.MaxValue)))) {
            dist(neighbor) = newDist
            pq.enqueue((newDist, neighbor))
          }
        }
      }
    }
    dist.toMap
  }

   def floydWarshall: Map[(T, T), Double] = {
    val dist = mutable.Map[(T, T), Double]()
    val num = implicitly[Numeric[W]]
    for (v <- vertices) {
      for (u <- vertices) {
        dist((v, u)) = if (v == u) 0.0 else Double.PositiveInfinity
      }
    }
    for (((v1, v2), weight) <- edges) {
      dist((v1, v2)) = num.toDouble(weight)
    }

    for (k <- vertices) {
      for (i <- vertices) {
        for (j <- vertices) {
          if (dist((i, k)) != Double.PositiveInfinity && dist((k, j)) != Double.PositiveInfinity) {
            val newDist = dist((i, k)) + dist((k, j))
            if (newDist < dist((i, j))) {
              dist((i, j)) = newDist
            }
          }
        }
      }
    }
    dist.toMap
  }
}