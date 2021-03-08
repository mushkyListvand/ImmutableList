import scala.collection.immutable.ArraySeq
import scala.reflect.{ClassTag, classTag}


object Demo extends App {

  /*def createArray[T:ClassTag](elems:T*) = {
    val a = new Array[T](2)
    a
  }
 println(createArray(1,2,3,4).toList)*/
  var a = List[Int](1, 2)
/*  a = a.appended(9)
  a = a.appended(10)
  a = a.appended(11)*/


  val b = new ImmutableList[Int](a: _*)
  println(b.tail())
  println(b)


}
