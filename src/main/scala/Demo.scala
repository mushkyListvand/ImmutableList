import scala.collection.immutable.ArraySeq
import scala.reflect.{ClassTag, classTag}


object Demo extends App {


  var a = List[Int](1, 2, 3)

  val b = new ImmutableList[Int](1,2,3)
  println(b.drop(2))
  println(b)




}
