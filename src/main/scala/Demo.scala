import scala.collection.immutable.ArraySeq
import scala.reflect.{ClassTag, classTag}


object Demo extends App {

  /*def createArray[T:ClassTag](elems:T*) = {
    val a = new Array[T](2)
    a
  }
 println(createArray(1,2,3,4).toList)*/
  var a = List[Int]()
  val b = new ImmutableList[Int](a: _*)
  println(b.remove(0))

}
