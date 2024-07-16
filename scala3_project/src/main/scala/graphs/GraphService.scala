package graphs

import zio._

case class GraphService(graph: Ref[Graph[Int, Int]]) {
  def addEdge(src: Int, dest: Int, weight: Int): UIO[Unit] =
    graph.update(_.addEdge(src, dest, weight))

  def removeEdge(src: Int, dest: Int): UIO[Unit] =
    graph.update(_.removeEdge(src, dest))

  def getGraph: UIO[Graph[Int, Int]] =
    graph.get
}

object GraphService {
  def live: ULayer[Has[GraphService]] =
    Ref.make(Graph.empty[Int, Int]).map(ref => GraphService(ref)).toLayer
}
