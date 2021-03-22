class Node[A](val value:A, var previous:Node[A] = null, var next:Node[A] = null){

  def ChangeNext(next:Node[A]) = {
    this.next = next
  }
  def ChangePrevious(previous:Node[A]) = {
    this.previous = previous
  }






}
