import scala.collection.immutable.ArraySeq
import scala.reflect.{ClassManifest, ClassTag}

class ImmutableList[A]() extends TImmutableList[A] {
  private var first:Node[A] = null
  private var last:Node[A] = null

  private def this (first:Node[A], last:Node[A]) =
  {
    this()
    this.first = first
    this.last = last
  }

  //builds new immutable list with the requested values
  def this(values:A*) = {
    this()

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

 /* private def extractValues(): List[A] = {
    /* var values = extractValues().appended(item)
                    new ImmutableList[A](values: _*)*/
    var current = first
    var values = List[A]()
    while(current!=null) {
      values = values.appended(current.value)
      current = current.next
    }
    values
  }*/

  private def Clone() = {
    var newFirst = null: Node[A]
    var newLast = null:Node[A]
    var current = this.first
    var previous = null: Node[A]
    var newNode = null:Node[A]

    while(current!=null) {

      if(newFirst == null) {
        newNode = new Node[A](current.value)
        newFirst = newNode
      } else {
        newNode = new Node[A](current.value, previous)
        previous.next = newNode
      }

      previous = newNode
      current = current.next
    }
    newLast = previous
    new ImmutableList[A](newFirst, newLast)

  }

  //create new immutable list with the additional item
  override def add(item: A): TImmutableList[A] = {
    //scans all nodes from first to last and builds new list[A] with the addditional item
    val copy:ImmutableList[A] = this.Clone()
    var newNode = new Node[A](item, copy.last)
    copy.last.next = newNode
    copy.last = newNode

    copy
  }

  override def prepend(item: A): TImmutableList[A] = {
    /*var values = extractValues()*/
    new ImmutableList[A]()
  }

  //create string for displaying list's values by string builder
  override def toString: String = {
    var sb = new StringBuilder("ImmutableList(")
    var current = this.first
    while(current != null) {
      sb ++= s"${current.value},"
      current = current.next
    }
    sb.deleteCharAt(sb.length() - 1) //delete last comma
    sb += ')'
    sb.toString()
  }
}


