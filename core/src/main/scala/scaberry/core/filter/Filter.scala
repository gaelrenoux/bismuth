package scaberry.core.filter

import scaberry.core.{Getter, Field => CField}

/**
  *
  * @tparam A Target type to filter
  */
sealed trait Filter[-A] extends Function1[A, Boolean] {

  def verify(target: A): Boolean

  def apply(target: A): Boolean = verify(target)

  def &&[B <: A](that: Filter[B]): Filter[B] =
    Filter.compositeAnd(this, that)

  def ||[B <: A](that: Filter[B]): Filter[B] =
    Filter.compositeOr(this, that)

}

object Filter {

  /** Empty filter, accepts any value. */
  sealed class Empty[-A] extends Filter[A] {
    override def verify(a: A): Boolean = true

    override def &&[B <: Any](that: Filter[B]): Filter[B] = that

    override def ||[B](that: Filter[B]): Filter[B] = Empty
  }

  object Empty extends Empty[Any]

  /** Commodity function to define an empty filter */
  def empty[A]: Empty[A] = Empty

  /** Value filter, accepts only value equals to its argument. */
  final class Value[-A] private[core](value: A) extends Filter[A] {
    override def verify(a: A): Boolean = a == value
  }

  /** Commodity function to define a Value */
  def value[A](value: A): Value[A] = new Value(value)

  /** Filter on an object by filtering on one of that object's fields.
    *
    * @tparam A Target type to filter
    * @tparam B Type of the filtered field
    */
  final class Field[-A, -B] private[core](
      val name: Symbol,
      getter: Getter[A, B],
      val filter: Filter[B]
  ) extends Filter[A] {
    override def verify(a: A): Boolean = filter(getter(a))
  }

  /** Commodity function to define a Field */
  def field[A, B](field: CField[A, B], filter: Filter[B]): Field[A, B] = new Field(field.name, field.getter, filter)

  /** Filter on multiple fields. */
  final class Fields[-A] private[core](seq: Seq[Field[A, _]]) extends Filter[A] {
    override def verify(a: A): Boolean = seq.forall(_.verify(a))
  }

  /** Commodity function to define a Fields */
  def fields[A](fields: Field[A, _]*): Fields[A] = new Fields(fields)

  /** Filters put together, all must match. */
  final class CompositeAnd[-A] private[core](seq: Seq[Filter[A]]) extends Filter[A] {

    override def verify(a: A): Boolean = seq.forall(_.verify(a))

    /*
        override def &&[B <: A, SB <: Ser](that: Filter[B, SB]): Filter[B, Ser] =
          Filter.compositeAnd[B, Ser, Ser](this.seq :+ that: _*)*/
  }

  /** Commodity function to define a CompositeAnd */
  def compositeAnd[A](filters: Filter[A]*): CompositeAnd[A] = new CompositeAnd(filters)

  /** Filters put together, any must match. */
  final class CompositeOr[-A] private[core](seq: Seq[Filter[A]]) extends Filter[A] {

    override def verify(a: A): Boolean = seq.exists(_.verify(a))
  }

  /** Commodity function to define a CompositeOr */
  def compositeOr[A](filters: Filter[A]*): CompositeOr[A] = new CompositeOr(filters)

}