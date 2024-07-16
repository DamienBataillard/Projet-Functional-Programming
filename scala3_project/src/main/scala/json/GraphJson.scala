package json
import zio.json._
import graphs._
import json._

object GraphJson {
  // Implicit encoders and decoders for DirectedGraph
  implicit def directedGraphEncoder[T: JsonEncoder]: JsonEncoder[DirectedGraph[T]] = DeriveJsonEncoder.gen[DirectedGraph[T]]
  implicit def directedGraphDecoder[T: JsonDecoder]: JsonDecoder[DirectedGraph[T]] = DeriveJsonDecoder.gen[DirectedGraph[T]]

  // Implicit encoders and decoders for UndirectedGraph
  implicit def undirectedGraphEncoder[T: JsonEncoder]: JsonEncoder[UndirectedGraph[T]] = DeriveJsonEncoder.gen[UndirectedGraph[T]]
  implicit def undirectedGraphDecoder[T: JsonDecoder]: JsonDecoder[UndirectedGraph[T]] = DeriveJsonDecoder.gen[UndirectedGraph[T]]

  // Implicit encoders and decoders for WeightedGraph
  //implicit def weightedGraphEncoder[T: JsonEncoder, W: JsonEncoder]: JsonEncoder[WeightedGraph[T, W]] = DeriveJsonEncoder.gen[WeightedGraph[T, W]]
  //implicit def weightedGraphDecoder[T: JsonDecoder, W: JsonDecoder]: JsonDecoder[WeightedGraph[T, W]] = DeriveJsonDecoder.gen[WeightedGraph[T, W]]

  // Custom encoders and decoders for Set and Map
  implicit def setEncoder[T: JsonEncoder]: JsonEncoder[Set[T]] = JsonEncoder[List[T]].contramap(_.toList)
  implicit def setDecoder[T: JsonDecoder]: JsonDecoder[Set[T]] = JsonDecoder[List[T]].map(_.toSet)

  implicit def mapEncoder[K: JsonEncoder, V: JsonEncoder]: JsonEncoder[Map[K, V]] = JsonEncoder[List[(K, V)]].contramap(_.toList)
  implicit def mapDecoder[K: JsonDecoder, V: JsonDecoder]: JsonDecoder[Map[K, V]] = JsonDecoder[List[(K, V)]].map(_.toMap)

  def decodeJsonToDirectedGraph(jsonString: String): Either[String, DirectedGraph[Int]] = {
    jsonString.fromJson[DirectedGraph[Int]]
  }

  def decodeJsonToUndirectedGraph(jsonString: String): Either[String, UndirectedGraph[Int]] = {
    jsonString.fromJson[UndirectedGraph[Int]]
  }

  /*
  def decodeJsonToWeightedGraph(jsonString: String): Either[String, WeightedGraph[Int, Double]] = {
    jsonString.fromJson[WeightedGraph[Int, Double]]
  }
*/

  

}

