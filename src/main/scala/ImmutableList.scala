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

   override  def clone() = {
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

  //create new immutable list with the additional item (on the end of the list)
  override def add(item: A): TImmutableList[A] = {
    //scans all nodes from first to last and builds new list[A] with the addditional item
    val copy:ImmutableList[A] = this.clone()
    var newNode = null:Node[A]
    newNode = new Node[A](item,copy.last)

    if(copy.first != null) copy.last.next = newNode
    else copy.first = newNode

    copy.last = newNode

    copy
  }

  override def prepend(item: A): TImmutableList[A] = {
    val copy = this.clone()
    var newNode = null:Node[A]
    newNode = new Node[A](item)

    if(copy.first == null) {
      copy.last = newNode
    }
    else {
      copy.first.previous = newNode
      newNode.next = copy.first
    }
    copy.first = newNode

    copy
  }

  override def remove(i: Int): TImmutableList[A] = {
      val copy = this.clone()
      var counter = 0
      var current = copy.first
      while(counter <= i && current != null) {
        /*  if(copy.first == current && copy.last == current) {
          copy.first = null
          copy.last = null
        }
        else if(copy.first == current) {
          copy.first = copy.first.next
          copy.first.previous = null
        }
        else if(current == copy.last) {
          current.previous.next = null
          copy.last = current.previous
        }
        else {
          current.previous.next = current.next
        }*/
        if (counter == i) {
          if (copy.last == current) {
            copy.last = current.previous
          }
          if (copy.first == current) {
              copy.first = current.next
            if(copy.first != null)
               copy.first.previous = null
          }
          else current.previous.next = current.next

        }
        current = current.next
        counter += 1
      }
    copy
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


