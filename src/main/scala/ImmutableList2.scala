sealed trait ImmutableList2[+A] {


}

final case class Pair[A](first: Node[A], last: Node[A]) extends ImmutableList2[A]

case object ImmutableList2 extends ImmutableList[Nothing] {
  def apply[A](values: A*): Unit = {
    var current = null: Node[A]
    var previous = null: Node[A]
    var first = null: Node[A]


    for (v <- values) {
      current = new Node[A](v, previous)
      if (first == null) first = current
      else previous.next = current
      previous = current
    }
    Pair(first, current)
  }
}