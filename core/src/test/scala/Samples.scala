import bismuth.core._

object Samples {

  case class Person(name: String, age: Long)

  trait Animal {
    val weight: Long
    val color: String
    val name: Option[String]
  }

  case class Dog(color: String, weight: Long, name: Some[String]) extends Animal


  object personFields {
    val nameF = new Field[Person, String, Field.Copier[Person, String], Any]("name", _.name, (p, n) => p.copy(name = n))
    val ageF = new Field[Person, Long, Any, Any]("age", _.age)
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
