# Functional Programming / Scala Project 

## Project Overview

This project is a Scala 3 library for creating and manipulating different types of graph data structures. It supports directed, undirected, and weighted graphs and includes various operations. This project was realized by Damien BATAILLARD, Kamilia BENKIRANE, Karim ELMESTARI, Olivier HELLOUIN DE MENIBUS.

## Sommaire

#### 1. [Configuration du projet](#configuration-du-projet)

* [Instructions](#instructions)
* [Code structure](#code-structure)
* [Design Decisions](#design-decisions)
* [Tests](#testing)



## Instructions

### Building the Project

To build the project, you need to have Scala 3 and SBT (Scala Build Tool) installed. Follow these steps:

1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/graph-library.git
   cd scala3_project
   ```

2. Download and install all the dependencies listed in the build.sbt file + compile the project :
   ```sh
   sbt compile
   ```

3. Run the application : 
    ```sh
    sbt run
    ```

4. Run the tests : 
    ```sh
    sbt test
    ```

This will execute all the unit tests and display the test results. 


## Code structure
- `/resources`: Contains .json files reprensenting a directed, undirected and weighted graphs.
- `/scala`
    - `/graphs`: Contains all files related to graphs and operations.
        - `/Graph.scala`: Contains the graph trait
        - `/DirectedGraph.scala`: Directed Graph case class
        - `/UndirectedGraph.scala`: Undirected case class
        - `/WeightedGraph.scala`: Weighted case class
    - `/json` 
        - `/GraphJson.scala` : Contains the implicit methods that handle json encoding and decoding
    - `viz` 
        - `GraphViz.scala` : Contains implicit method to display a graph using GrphViz and foldLeft method.
    - `Main.scala`: User interface in the console to perform all available operations
  - `/test/scala/graphs` : Contains all tests for each type of graphs 
    - `DirectedGraphSpec.scala` 
    - `UndirectedGraphSpec.scala` 
    - `WeightedGraphSpec.scala` 



## Design Decisions

### Separation of Graph Types

The library defines a `Graph` trait to encapsulate common graph operations and algorithms. This trait is implemented by the different case classes for directed, undirected, and weighted graphs. This separation allows each graph type to have specialized implementations (which is necessary for the vast majority of operations depending on the type of graph) while sharing a common interface. We decided to use case classes for the graphs because they are immutable and they allow us to use methods like copy which we used to add and remove edges more efficiently. We also used tail-recursion for most of the operations and algorithms on graphs such as dfs, bfs, hasCycle, dijkstra...

### Weighted Graphs

The `WeightedGraphTrait` extends the `Graph` trait to include weights on edges. The weights are generic, allowing for flexibility in the type of weight used (integers, floats, double...).

### JSON Encoding/Decoding

The JSON encoding/decoding functionality is implemented for directed and undirected graphs using `zio.json` library. Therefore the user can decode a json file containing a graph (example in `scala3_project/src/main/resources`) and perform operations all operations available on it and inversely. Unfortunately, we did not achieve implementing this functionality for graph type Weighted Graph because of the complexity of handling generic types in Scala's implicit methods.

### GraphViz Representation

The library includes a method to generate a GraphViz DOT format representation of the graph using the `foldLeft` method. This allows for easy visualization of the graph structure.

## Testing

The library includes comprehensive tests for each of the graph types (Directed, Undirected an Weighted) and their respective operations and algorithms. The tests are organized to cover the following aspects:

- **Vertex and Edge Operations**: Adding and removing vertices and edges.
- **Graph Traversal**: BFS and DFS algorithms.
- **Pathfinding and Shortest Path**: Floyd-Warshall and Dijkstra's algorithms.
- **Cycle Detection and Topological Sorting**: Detecting cycles and performing topological sorting.
- **JSON Serialization**: Encoding and decoding graphs to/from JSON for directed and undirected graphs, using the json files in `/resources`.
- **GraphViz Generation**: Generating GraphViz DOT representation of a graph.

To run the tests, use the command:

```sh
sbt test
```


