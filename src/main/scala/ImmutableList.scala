import scala.collection.immutable.ArraySeq
import scala.reflect.{ClassManifest, ClassTag}

class ImmutableList[A]() extends TImmutableList[A] {
  private var first:Node[A] = null
  private var last:Node[A] = null


  //builds new immutable list with the requested values
  def this(values:A*) = {
    this()
    println(values.getClass)
    if(values.size >= 1) {
      var current = null: Node[A]
      var previous = null: Node[A]

      for(v <- values) {
        current = new Node[A](v, previous)
        if (first == null) first = current
        else previous.next = current
        previous = current
      }
      last = current
    }
  }

  private def extractValues(): List[A] = {
    var current = first
    var values = List[A]()
    while(current!=null) {
      values = values.appended(current.value)
      current = current.next
    }
    values
  }

  //create new immutable list with the additional item
  override def add(item: A): TImmutableList[A] = {
    //scans all nodes from first to last and builds new list[A] with the addditional item
    var values = extractValues().appended(item)
    new ImmutableList[A](values: _*)
  }

  //create string for displaying list's values
  override def toString: String = {
     var values =  extractValues()
     values.toString().replace("List","ImmutableList")
  }
}


