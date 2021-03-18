import scala.reflect.ClassTag

trait TImmutableList[A] {
  def add(item:A): TImmutableList[A]
  def prepend(item:A): TImmutableList[A]
  def remove(i:Int):TImmutableList[A]
  def head(): A
  def tail(): TImmutableList[A]
  def isEmpty() : Boolean
  def nonEmpty() : Boolean
  def at(index : Int) : A
  def apply(index : Int) : A
  def size() : Int
  def take(n : Int) : TImmutableList[A]
  def drop(n : Int) : TImmutableList[A]
}
