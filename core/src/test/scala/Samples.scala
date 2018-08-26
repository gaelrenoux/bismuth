import bismuth.core._

object Samples {

  case class Person(name: String, age: Long)

  trait Animal {
    val weight: Long
    val color: String
    val name: Option[String]
  }

  case class Dog(color: String, weight: Long, name: Some[String]) extends Animal


  val personFields = new Fields[Person] {
    val name = new Field[Person, String, Field.Copier[Person, String], Field.NoSetter.type]("name", _.name, (p, n) => p.copy(name = n))
    val age = new Field[Person, Long, Field.Copier[Person, Long], Field.NoSetter.type]("name", _.age, (p, a) => p.copy(age = a))
  }

  case class PersonUpdate(
                          name: Update[String] = Update.Identity,
                          age: Update[Long] = Update.Identity
                        )


  case class PersonFilter(
                           name: Filter[String] = Filter.None,
                           age: Filter[Long] = Filter.None
                         ) extends Filter.Operation[Person](p => name(p.name) && age(p.age))

  val f1 = PersonFilter(name = "")


}
