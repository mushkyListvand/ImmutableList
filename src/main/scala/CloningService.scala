//this generic class is used to create new immutable list which based on the values of another list
//A represents the type of original list  and B represents the type of new list
class CloningService[A, B](var newFirst: Node[B] = null, var newLast:Node[B] = null, var current:Node[A] = null,
                           var previous: Node[B] = null, var newNode:Node[B] = null ) extends ICloningService[A, B] {
/*   def cloneFirst(value : B = this.current.value.asInstanceOf[B]) = {
    this.newNode = new Node[B](value)
    this.newFirst = this.newNode
  }

   def cloneNode(value : B = this.current.value.asInstanceOf[B]) = {
    this.newNode = new Node[B](value, this.previous)
    this.previous.next = this.newNode
  }*/

  //attach new node to the previous node
  def cloneToList(value : B = this.current.value.asInstanceOf[B]) = {
    if(this.newFirst == null) {
      this.newNode = new Node[B](value)
      this.newFirst = this.newNode
    }
    else {
      this.newNode = new Node[B](value, this.previous)
      this.previous.next = this.newNode
    }
  }

  //move the previous of the new list to the new node which attached
  //move the current of the original list to point the next node
   def moveToNext() = {
    this.previous = this.newNode
    this.current = this.current.next
  }

  //update the last node
  def updateLast() = {
    this.newLast = this.previous
  }
}
