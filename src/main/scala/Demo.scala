import scala.collection.immutable.ArraySeq
import scala.reflect.{ClassTag, classTag}


object Demo extends App {


  var a = List[Int]( 2, 4, 6, 3, 7, 10, 8, 4, 2)
  var c = List[String]("ogdoujp")
  println(a.prepended("ojpoe"))
  var h : List[Any] = a
  val b = new ImmutableList[Int](1, 2, 3)
  val d = new ImmutableList("hoa")
  case class Box[+A](value: A) {

    def set(a: A): Box[A] = Box(a)


  }
















}
