package scaberry.core

import scaberry.core.Field.{Copier, Getter}
import scaberry.core.Update._

/** An update on a target object. */
trait Update[Target] extends Function1[Target, Target] {

  def update(t: Target): Target

  def apply(t: Target): Target = update(t)

  /** Combines two updates into one, which will be applied in order. */
  def and(other: Update[Target]): Update[Target] = new ComposedU[Target](other :: this :: Nil)

  /** Combines two updates into one, which will be applied in order. */
  def `|@|`(other: Update[Target]): Update[Target] = and(other)

}


object Update {

  /** Empty update, returning the object itself. */
  final class NoChange[T] extends Update[T] {
    override def update(t: T): T = t
  }

  def identity[T]: Update[T] = new NoChange[T]

  /** Update with a new value. */
  final class ValueU[T](val value: T) extends Update[T] {
    override def update(target: T): T = value
  }

  def value[T](value: T) = new ValueU(value)

  /** Update by applying the given operation. */
  final class OperationU[T](op: T => T) extends Update[T] {
    override def update(target: T): T = op(target)
  }

  def operation[T](op: T => T) = new OperationU(op)

  /** Field update. Updates an object by updating one of that object's fields.
    *
    * @tparam T Target type to update
    * @tparam F Type of the updated field
    */
  final class FieldU[T, F] private[core](val name: Symbol, getter: Getter[T, F], copier: Copier[T, F], val fieldUpdate: Update[F]) extends Update[T] {
    override def update(target: T): T = copier(target, fieldUpdate.update(getter(target)))
  }

  def field[T, F](field: CopyableField[T, F], update: Update[F]): FieldU[T, F] = new FieldU(field.name, field.getter, field.copier, update)

  /** Composed update, obtained by combining updates. */
  final class ComposedU[T] private[core](list: List[Update[T]]) extends Update[T] {
    override def update(target: T): T = operations.foldLeft(target) { (acc, op) => op.update(acc) }

    lazy val operations: List[Update[T]] = list.reverse

    /* Specific definition of {{and}} for Composed update, to avoid too deep a hierarchy. */
    override def and(other: Update[T]): Update[T] = new ComposedU[T](other :: list)
  }

  /** Trait for Updates created through macros. */
  trait CustomU[T] extends Update[T]

}