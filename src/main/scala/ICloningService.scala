trait ICloningService[A, B] {
  var newFirst: Node[B]
  var newLast:Node[B]
  var current:Node[A]
  var previous: Node[B]
  var newNode:Node[B]

  def cloneToList(value : B)
  def moveToNext()
  def updateLast()


}
