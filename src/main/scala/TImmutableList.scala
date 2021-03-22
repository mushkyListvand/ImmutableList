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
  def equals(l : TImmutableList[A]) : Boolean
  def filter(p : (A) => Boolean) : TImmutableList[A]
  def headOption() : Option[A]
  def map[B](f: (A) => B) : TImmutableList[B]
  def takeWhile(p: (A) => Boolean): TImmutableList[A]
  def foldLeft[B](z: B)(op : (B, A) => B) : B
  def concat[B >: A](suffix : TImmutableList[B]) : TImmutableList[B]

}
