import scalberto.core.{Field, Fields, Filter, Update}

object Samples {

  case class Person(name: String, age: Long)

  trait Animal {
    val weight: Long
    val color: String
    val name: Option[String]
  }

  case class Dog(color: String, weight: Long, name: Some[String]) extends Animal


  val personFields = new Fields[Person] {
    val name = new Field[Person, String, Field.Copier[Person, String], Field.NoSetter.type]('name, _.name, (p, n) => p.copy(name = n))
    val age = new Field[Person, Long, Field.Copier[Person, Long], Field.NoSetter.type]('age, _.age, (p, a) => p.copy(age = a))
  }

  val aFilter = personFields.name.filterEq("Roger") |@| personFields.age.filterWith(_ > 18)

  case class PersonFilter(
                             name: Filter[String] = Filter.None,
                             age: Filter[Long] = Filter.None
                         ) extends Filter.Custom[Person] {
    override def verify(p: Person): Boolean = name(p.name) && age(p.age)
  }

  //val f1 = PersonFilter(name = "")




  case class PersonUpdate(
                             name: Update[String] = Update.Identity,
                             age: Update[Long] = Update.Identity
                         )



}
