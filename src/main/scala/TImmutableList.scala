import scala.reflect.ClassTag

trait TImmutableList[A] {
  def add(item:A): TImmutableList[A]
  def prepend(item:A): TImmutableList[A]
  def remove(i:Int):TImmutableList[A]
}
