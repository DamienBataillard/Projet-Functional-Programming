package graphs

import zio._
import zio.console._

object GraphApp {
  def menu: ZIO[Console with Has[GraphService], Throwable, Unit] = for {
    _ <- putStrLn("1. Add Edge")
    _ <- putStrLn("2. Remove Edge")
    _ <- putStrLn("3. Display Graph")
    _ <- putStrLn("4. Perform DFS")
    _ <- putStrLn("5. Perform BFS")
    _ <- putStrLn("6. Exit")
    choice <- getStrLn
    _ <- choice match {
      case "1" => addEdge
      case "2" => removeEdge
      case "3" => displayGraph
      case "4" => performDFS
      case "5" => performBFS
      case "6" => ZIO.succeed(())
      case _   => putStrLn("Invalid choice, please try again") *> menu
    }
  } yield ()

  def addEdge: ZIO[Console with Has[GraphService], Throwable, Unit] = for {
    _ <- putStrLn("Enter source vertex:")
    src <- getStrLn.map(_.toInt)
    _ <- putStrLn("Enter destination vertex:")
    dest <- getStrLn.map(_.toInt)
    _ <- putStrLn("Enter weight:")
    weight <- getStrLn.map(_.toInt)
    graphService <- ZIO.service[GraphService]
    _ <- graphService.addEdge(src, dest, weight)
    _ <- putStrLn(s"Edge added: ($src -> $dest) with weight $weight")
    _ <- menu
  } yield ()

  def removeEdge: ZIO[Console with Has[GraphService], Throwable, Unit] = for {
    _ <- putStrLn("Enter source vertex:")
    src <- getStrLn.map(_.toInt)
    _ <- putStrLn("Enter destination vertex:")
    dest <- getStrLn.map(_.toInt)
    graphService <- ZIO.service[GraphService]
    _ <- graphService.removeEdge(src, dest)
    _ <- putStrLn(s"Edge removed: ($src -> $dest)")
    _ <- menu
  } yield ()

  def displayGraph: ZIO[Console with Has[GraphService], Throwable, Unit] = for {
    graphService <- ZIO.service[GraphService]
    graph <- graphService.getGraph
    _ <- putStrLn(s"Graph: $graph")
    _ <- menu
  } yield ()

  def performDFS: ZIO[Console with Has[GraphService], Throwable, Unit] = ???
  def performBFS: ZIO[Console with Has[GraphService], Throwable, Unit] = ???
}
