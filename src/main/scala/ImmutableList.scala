class ImmutableList[A] extends TImmutableList[A] {
  private var first:Node[A] = null
  private var last:Node[A] = null
  //val add:Node[A]

  def this(values:A*) = {
    this()
    if(values.size>=1) {
      var current = null: Node[A]
      var previous = null: Node[A]

      for(v <- values) {
        current = new Node[A](v, previous)
        if (first == null) first = current
        else previous.ChangeNext(current)
        previous = current
      }
      last = current
    }



  }

  override def add(item: A): TImmutableList[A] = {

    new ImmutableList[A] ()
  }
}


