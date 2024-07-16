// For Comprehensions Worksheet
import scala.util.{Failure, Success, Try}
import scala.annotation.tailrec

// A quick tour of the standard library (collections and errors handling)
case class SuperHero(name: String, power: String)

// List data structure is fundamental in functional programming
// Lists in Scala are: immutable, recursive and homogeneous
val fantasticFour: List[SuperHero] = List(
  SuperHero("Mr Fantastic", "Elasticity"),
  SuperHero("The Invisible Girl", "Invisibility"),
  SuperHero("The Human Torch", "Pyrokinesis"),
  SuperHero("The Thing", "Strength")
)
val fruits: List[String] = List("apple", "orange", "banana")
val numbers: List[Int] = List(1, 2, 3, 4)
val empty: List[Nothing] = List()
val heterogeneous: List[Any] = List(fantasticFour.head, "pear", 42)

// Constructors of lists
// A convention in Scala states that operators ending in `:` are right-associative
1 :: (2 :: (3 :: Nil))
1 :: 2 :: 3 :: Nil
Nil.::(3).::(2).::(1)

// Pattern matching
numbers match {
  case 1 :: 2 :: xs => "starts with 1 and 2"
  case x :: Nil     => "is of length one"
  case List(x)      => "is of length one"
  case Nil          => "is empty"
  case List()       => "is empty"
  case _            => "is something else"
}

// Optional: insertion sort
def insertionSort(xs: List[Int]): List[Int] = {
  val isSmallerThan: (Int, Int) => Boolean = (x, y) => x <= y

  def insert(x: Int, xs: List[Int]): List[Int] =
    xs match {
      case List() => x :: Nil
      case y :: ys =>
        if (isSmallerThan(x, y)) x :: y :: ys
        else y :: insert(x, ys)
    }

  xs match {
    case List()  => List()
    case y :: ys => insert(y, insertionSort(ys))
  }
}

insertionSort(List(2, 1, 3)) == List(1, 2, 3)

// Operations on list:
// - map
// - filter
// - flatMap
// - foreach
numbers.map(x => x * x)
numbers.filter(_ % 2 == 1)
numbers.map(x => if x > 0 then List(-x, x) else List(x, -x))
numbers.flatMap(x => if x > 0 then List(-x, x) else List(x, -x))
numbers.foreach(println)

// Operations can be chained:
numbers
  .map(x => x * x)
  .filter(_ % 2 == 1)
  .flatMap(x => if x > 0 then List(-x, x) else List(x, -x))

// This expression above is equivalent to:
// (for {
//   y <- numbers
//   n = y * y
//   if n % 2 == 1
//   x = if n > 0 then List(-n, n) else List(n, -n)
// } yield x).flatten

// For comprehensions:
// - generators
// - filters
// - definitions
val xs = List(1, 2, 3, 4, 5, 6, 7, 8)
val ys = List(8, 6, 4, 2)

// for expression
val forExpression = for {
  x <- xs if x % 2 == 0
  y <- ys
} yield (x, y)

// is equivalent to
val filterThenFlatMap = xs
  .filter { x =>
    x % 2 == 0
  }
  .flatMap { x =>
    ys.map { y =>
      (x, y)
    }
  }

assert(forExpression == filterThenFlatMap)

// In Scala, the term "comprehension" refers to the ability to combine iterations, transformations, and filters in a concise and declarative manner using the for construct.
// A for comprehension allows you to express complex looping operations over collections in a more readable and functional style.
// In mathematics, the term "comprehension" is commonly used in the context of set theory and logic.
// It refers to a notation or construction that allows you to define a set by specifying the properties or conditions that its elements must satisfy.
// Georg Cantor (1845-1918) introduced the concept of set comprehension as a fundamental idea in his development of set theory.
// He used the term "Begriffsbildung" in German, which can be translated as "concept formation" or "comprehension".
// The term emphasizes the idea that a set is defined by comprehending or grasping the elements that satisfy certain conditions or properties.

// Remember our own implementation of a list (of integers):
enum MyList:
  case Empty
  case Cons(head: Int, tail: MyList)

  // `reverse` is going to be used as an helper in the implementation below
  final def reverse(): MyList = {
    @tailrec
    def reverseHelper(xs: MyList, accumulator: MyList): MyList = xs match
      case Empty => accumulator
      case Cons(head: Int, tail: MyList) =>
        reverseHelper(tail, Cons(head, accumulator))

    reverseHelper(this, Empty)
  }

  // `append` is going to be used as an helper in the implementation below
  final def append(ys: MyList): MyList = {
    @tailrec
    def appendHelper(xs: MyList, accumulator: MyList): MyList = xs match
      case Empty => accumulator
      case Cons(head: Int, tail: MyList) =>
        appendHelper(tail, Cons(head, accumulator))

    appendHelper(this.reverse(), ys)
  }

  final def isEmpty: Boolean = this match
    case Empty => true
    case _     => false

  // `foreach` should be f: A => U, this is a simple case as MyList is not generic
  // Note: `[U]` parameter needed to help scalac's type inference.
  @tailrec
  final def foreach(f: Int => Unit): Unit = this match
    case Empty => ()
    case Cons(head: Int, tail: MyList) =>
      f(head)
      tail.foreach(f)

  // `map` should be f: A => B, this is a simple case as MyList is not generic
  final def map(f: Int => Int): MyList = {
    @tailrec
    def mapHelper(xs: MyList, accumulator: MyList): MyList = xs match
      case Empty => accumulator
      case Cons(head: Int, tail: MyList) =>
        mapHelper(tail, Cons(f(head), accumulator))

    mapHelper(this, Empty).reverse()
  }

  // `p` should be A => Boolean, this is a simple case as MyList is not generic
  final def withFilter(p: Int => Boolean): MyList = {
    @tailrec
    def withFilterHelper(xs: MyList, accumulator: MyList): MyList = xs match
      case Empty => accumulator
      case Cons(head: Int, tail: MyList) =>
        withFilterHelper(
          tail,
          if p(head) then Cons(head, accumulator) else accumulator
        )

    withFilterHelper(this, Empty).reverse()
  }

  // `flatMap` should be f: A => MyList[B], this is a simple case as MyList is not generic
  final def flatMap(f: Int => MyList): MyList = {
    @tailrec
    def flatMapHelper(xs: MyList, accumulator: MyList): MyList = xs match
      case Empty => accumulator
      case Cons(head: Int, tail: MyList) =>
        val ys = f(head)
        flatMapHelper(tail, accumulator.append(ys))

    flatMapHelper(this, Empty)
  }

end MyList

import MyList.*
val myEmptyList: MyList = Empty
val myNumbers: MyList = Cons(1, Cons(2, Cons(3, Cons(4, Empty))))

myEmptyList.isEmpty
myNumbers.isEmpty

// Let's talk about `foreach`, `map`, `withFilter` and `flatMap`

// For loop: `foreach`
// Applying a for *loop* to the `numbers` list:
for { n <- numbers } println(n)
// What about `myNumbers`? `value foreach is not a member of MyList`
for { n <- myNumbers } println(n)

// This for comprehension (loop is okay in this specific case):
for { n <- numbers } println(n)
// is equivalent to:
numbers.foreach(println)

// We need to implement `foreach` for `MyList`
for { n <- myNumbers } println(n)

// For comprehension with a single generator: `map`
// We can apply a transformation to the `numbers` list:
for { n <- numbers } yield n * n
// What about `myNumbers`? `value map is not a member of MyList`
for { n <- myNumbers } yield n * n

// This for comprehension:
for { n <- numbers } yield n * n
// is equivalent to:
numbers.map(x => x * x)

// We need to implement `map` for `MyList`
for { n <- myNumbers } yield n * n
// To solve this problem, we add the `reverse` function
myNumbers.reverse()

// For comprehension with filtering enabled: `withFilter`
// We can filter the `numbers` list:
for {
  n <- numbers
  if n % 2 == 1
} yield n * n
// What about `myNumbers`? `value withFilter is not a member of MyList`
for {
  n <- myNumbers
  if n % 2 == 1
} yield n * n

// This for comprehension:
for {
  n <- numbers
  if n % 2 == 1
} yield n * n
// is equivalent to:
numbers.filter(_ % 2 == 1).map(x => x * x)

// We need to implement `withFilter` for `MyList`
for {
  n <- myNumbers
  if n % 2 == 1
} yield n * n

// For comprehension with a multiple generators: `flatMap`
val otherNumbers = List(4, 33, 9, 71)
for {
  n <- numbers
  o <- otherNumbers
  if n * n == o
} yield o

// What about `myNumbers`? `value flatMap is not a member of MyList`
val myOtherNumbers: MyList = Cons(4, Cons(33, Cons(9, Cons(71, Empty))))
for {
  n <- myNumbers
  o <- myOtherNumbers
  if n * n == o
} yield o

// We need to implement `flatMap` for `MyList`
for {
  n <- myNumbers
  o <- myOtherNumbers
  if n * n == o
} yield o
// To solve this problem, we add the `append` function
myNumbers.append(Cons(5, Cons(6, Empty)))

// Remember what we said during the introduction course: pure functions (and their signature) tell no lies

// This function is lying:
@throws(classOf[NumberFormatException])
def toFloat(s: String): Float = s.toFloat

// And this one if even worse:
def toInt(s: String): Int = s.toInt

// Throwing exception (or null) is not expected from pure functions, it make composition of functions weak
// Option type to the rescue: `Option[A]` is either `Some[A]` or None
// A good way to implement partially defined functions and deal with exceptions

val someValue: Option[Int] = Some(42)
val noValue: Option[Int] = None

def maybeInt(s: String): Option[Int] = s.trim.toIntOption
// try {
//   Some(s.trim.toInt)
// } catch {
//   case e: Exception => None
// }

// Another good thing is that `Option` works well with pattern matching, `map`, ... and for comprehensions
Some(42).map(x => x + 1)
(None: Option[Int]).map(x => x + 1)

Some(1).filter(_ % 2 == 1)
Some(2).filter(_ % 2 == 1)
(None: Option[Int]).filter(_ % 2 == 1)

Some(1).map(x => Some(x + 1))
Some(1).flatMap(x => Some(x + 1))

val maybeSum1: Option[Int] = for {
  x <- maybeInt("1")
  y <- maybeInt("2")
  z <- maybeInt("3")
} yield x + y + z

val maybeSum2: Option[Int] = for {
  x <- maybeInt("1")
  y <- maybeInt("oops")
  z <- maybeInt("3")
} yield x + y + z

val f: Option[Int] => String = {
  case Some(value) => s"value is $value"
  case None        => "something went wrong"
}
//val f: Option[Int] => String = x =>
//  x match {
//    case Some(value) => s"value is $value"
//    case None        => "something went wrong"
//  }

f(maybeSum1)
f(maybeSum2)

// In a functional programming style, we will prefer `Option` over `null` or exceptions
// A pure function signature is a contract with its users, need to be as explicit as possible

// `Try[A]` is either `Success[A]` or `Failure` (where any exception thrown during execution is converted to `Failure`)
// `Either[A, B]` is either a success with `Right[B]` or an error with `Left[A]` (by convention)
// `Try`s and `Either`s also work well with pattern matching, `map`, ... and for comprehensions

def div(x: Int, y: Int): Try[Double] =
  if y == 0 then Failure(new IllegalArgumentException("division by zero"))
  else Success(x / y)

div(1, 2)
div(1, 0)

def even(x: Either[String, Int]): Either[String, Int] =
  x.filterOrElse(_ % 2 == 0, "odd value")

even(Right(2))
even(Right(1))

// Difference between `filter` and `withFilter`:
// `def filter(p: A => Boolean): Repr` where `Repr` is the current collection, eg. `List`
// `def withFilter(p: A => Boolean): WithFilter[A, Repr]`, with the same meanings for `Repr` and `A`
// The `withFilter` method iterates only when we call the `foreach`, `map`, or `flatMap` method on `WithFilter`
// When we need to provide more than one filtering operation, `withFilter`provides better performances
// When we need to apply a single predicate without any mapping on the result, the `filter` method should be used
// Unless using a `map` with the identity function with `WithFilter`

// Accumulating errors, a Scalactic example (https://www.scalactic.org)
// Scalactic provides an “`Either` with attitude” named `Or`, designed for functional error handling
// `Every` enables you to accumulate errors, with `Every` is either `One` or `Many`
// `Or` is either `Good` or `Bad`