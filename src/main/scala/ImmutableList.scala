import scala.collection.immutable.ArraySeq
import scala.reflect.{ClassManifest, ClassTag}

class ImmutableList[A]() extends TImmutableList[A] {

  private var first: Node[A] = null
  private var last: Node[A] = null


  private def this(first: Node[A], last: Node[A]) = {
    this()
    this.first = first
    this.last = last
  }

  //builds new immutable list with the requested values
  def this(values: A*) = {
    this()

    if (values.size >= 1) {
      var current = null: Node[A]
      var previous = null: Node[A]

      for (v <- values) {
        current = new Node[A](v, previous)
        if (first == null) first = current
        else previous.next = current
        previous = current
      }
      last = current
    }
  }

  private def cloneList(n: Int = this.size()) = {
    if (n <= 0) new ImmutableList[A]()
    val data = new CloningService[A, A]()
    var counter = 0
    data.current = this.first

    while (data.current != null && counter < n) {
      data.cloneToList()
      data.moveToNext()
      counter += 1
    }
    data.updateLast()
    new ImmutableList[A](data.newFirst, data.newLast)

  }

  //create new immutable list with the additional item (on the end of the list)
  override def add(item: A): TImmutableList[A] = {
    //scans all nodes from first to last and builds new list[A] with the addditional item
    val copy: ImmutableList[A] = this.cloneList()
    var newNode = null: Node[A]
    newNode = new Node[A](item, copy.last)

    if (copy.first != null) copy.last.next = newNode
    else copy.first = newNode

    copy.last = newNode

    copy
  }

  override def prepend(item: A): TImmutableList[A] = {
    val copy = this.cloneList()
    var newNode = null: Node[A]
    newNode = new Node[A](item)

    if (copy.first == null) {
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
    val copy = this.cloneList()
    var counter = 0
    var current = copy.first
    while (counter <= i && current != null) {
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
          if (copy.first != null)
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
    var current = this.first
    if (current == null) return ""

    var sb = new StringBuilder()
    while (current != null) {
      sb ++= s"${current.value},"
      current = current.next
    }
    sb.deleteCharAt(sb.length() - 1) //delete last comma
    sb.toString()
  }

  override def head(): A = {
    if (first != null)
      first.value
    else throw new NoSuchElementException("head of empty list")
  }

  override def tail(): TImmutableList[A] = {
    if (this.first == null)
      throw new UnsupportedOperationException("tail of empty list")

    if (this.first.next == null)
      new ImmutableList[A]()
    else {
      val nextOfFirst = this.first.next
      val newFirst = new Node[A](nextOfFirst.value, next = nextOfFirst.next)

      if (nextOfFirst.next == null)
        new ImmutableList[A](newFirst, new Node[A](this.last.value))
      else new ImmutableList[A](newFirst, this.last)
    }
  }

  override def isEmpty(): Boolean = {
    if (this.first == null) true
    else false
  }

  override def nonEmpty(): Boolean = {
    if (this.first == null) true
    else false
  }

  override def at(index: Int): A = {
    if (index < 0) throw new IndexOutOfBoundsException(s"$index")

    var current = this.first
    var counter = 0
    while (current != null && counter < index) {
      counter += 1
      current = current.next
    }

    if (current != null) current.value
    else throw new IndexOutOfBoundsException(s"$index")
  }

  override def apply(index: Int): A = {
    this.at(index)
  }

  override def size(): Int = {
    var current = this.first
    var size = 0
    while (current != null) {
      size += 1
      current = current.next
    }
    size
  }

  //the method will return a list consisting only of the first n elements of this collection, or else the collection,
  // if it has less than n elements
  override def take(n: Int): TImmutableList[A] = {
    if (n <= 0) new ImmutableList[A]()
    this.cloneList(n)
  }

  //that will return a list consisting of all elements of this general collection except the first n ones,
  // or else an empty list, if this list has less than n elements.
  override def drop(n: Int): TImmutableList[A] = {
    var counter = 0
    var current = this.first
    while (current != null && counter < n) {
      current = current.next
      counter += 1
    }
    val newList = new ImmutableList[A]()
    if (current != null) {
      newList.first = new Node[A](current.value, next = current.next)
      if (current == this.last)
        newList.last = new Node[A](this.last.value)
      else newList.last = this.last
    }
    newList
  }

  //This method checks for equality between this list and the object passed
  override def equals(l: TImmutableList[A]): Boolean = {
    if (l == null) return false
    var currentOfList1 = this.first
    var currentOfList2 = l.asInstanceOf[ImmutableList[A]].first
    while (currentOfList1 != null && currentOfList2 != null) {
      if (!currentOfList1.value.equals(currentOfList2.value)) return false
      currentOfList1 = currentOfList1.next
      currentOfList2 = currentOfList2.next
    }
    if (currentOfList1 != null || currentOfList2 != null) return false
    return true
  }

  //gets a predicate from A and returns a filtered List of all the elements that satisfy the predicate
  override def filter(p: A => Boolean): TImmutableList[A] = {
    val data = new CloningService[A, A]()
    data.current = this.first

    while (data.current != null) {
      if (p(data.current.value)) {
        data.cloneToList()
      }
      data.moveToNext()
    }
    data.updateLast()
    new ImmutableList[A](data.newFirst, data.newLast)
  }

  override def headOption(): Option[A] =
    if (this.first != null) Some(this.first.value)
    else None

  override def map[B](f: A => B): TImmutableList[B] = {
    val data = new CloningService[A, B]()
    data.current = this.first
    while (data.current != null) {
      data.cloneToList(f(data.current.value))
      data.moveToNext()
    }
    data.updateLast()
    new ImmutableList[B](data.newFirst, data.newLast)
  }

  override def takeWhile(p: A => Boolean): TImmutableList[A] = {
    val data = new CloningService[A, A]()
    data.current = this.first
    while (data.current != null && p(data.current.value)) {
      data.cloneToList()
      data.moveToNext()
    }
    data.updateLast()
    new ImmutableList[A](data.newFirst, data.newLast)
  }

  //foldLeft(z, op) - which will take a start value z and a binary operator op,
  // and will apply the operator to the start value and all elements of this collection or iterator,
  // going left to right: op(...,op(op(z, x1), x2), ..., xn) where x1, .., xn are the elements of the list.
  // Return z if the list is empty
  override def foldLeft[B](z: B)(op: (B, A) => B): B = {
    var current = this.first
    var intialVal = z
    while (current != null) {
      intialVal = op(intialVal, current.value)
      current = current.next
    }
    intialVal
  }

  //This method returns a new List consisting of the current List and the provided List
  override def concat[B](suffix: TImmutableList[B]): TImmutableList[B] = {
    val copyOfCurrent = new CloningService[B, B]()
    copyOfCurrent.current = this.first.asInstanceOf[Node[B]]
    while (copyOfCurrent.current != null) {
      copyOfCurrent.cloneToList()
      copyOfCurrent.moveToNext()
    }

    copyOfCurrent.updateLast()


    new ImmutableList[B](copyOfCurrent.newFirst, copyOfCurrent.newLast)
  }


}


