import scalberto.core.{CopyableField, Field, Filter, Update}

object Samples {

  case class Person(name: String, age: Long)

  trait Animal {
    val weight: Long
    val color: String
    val name: Option[String]
  }

  case class Dog(color: String, weight: Long, name: Some[String]) extends Animal


  val personFields = new {
    val name = new CopyableField[Person, String]('name, _.name, (p, n) => p.copy(name = n))
    val age = new CopyableField[Person, Long]('age, _.age, (p, a) => p.copy(age = a))
  }

  val aFilter = personFields.name.filterEq("Roger") |@| personFields.age.filterWith(_ > 18)

  val anUpdate = personFields.name.updateSet("Roger") |@| personFields.age.updateWith(_ * 2)

  case class PersonFilter(
                           name: Filter[String] = Filter.None,
                           age: Filter[Long] = Filter.None
                         ) extends Filter.Custom[Person] {
    override def verify(p: Person): Boolean = name(p.name) && age(p.age)
  }


  case class PersonUpdate(
                           name: Update[String] = Update.identity,
                           age: Update[Long] = Update.identity
                         )


}
