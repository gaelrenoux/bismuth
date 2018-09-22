package scalberto.tests

import org.scalatest.{FlatSpec, Matchers}
import scalberto.core.{CopyableField, Field}
import scalberto.tests.data.{Animal, Dog}

import scala.reflect.ClassTag

class ScaffieldMacroSpec extends FlatSpec with Matchers with Helpers {

  "default selector on case classes" should "return the fields in the primary constructor" in {
    "Dog.meta.fields.name" should compile
    "Dog.meta.fields.weight" should compile
    "Dog.meta.fields.color" should compile
  }

  it should "not return fields from another constructor" in {
    "Dog.meta.fields.other" shouldNot typeCheck
  }

  it should "not return other vals" in {
    "Dog.meta.fields.animate" shouldNot typeCheck
    "Dog.meta.fields.owner" shouldNot typeCheck
    "Dog.meta.fields.genus" shouldNot typeCheck
  }

  it should "not return other vars" in {
    "Dog.meta.fields.whatever" shouldNot typeCheck
    "Dog.meta.fields.whatever2" shouldNot typeCheck
  }

  it should "not return the defs" in {
    "Dog.meta.fields.otherName" shouldNot typeCheck
    "Dog.meta.fields.otherOtherName" shouldNot typeCheck
    "Dog.meta.fields.unary" shouldNot typeCheck
    "Dog.meta.fields.parameterized" shouldNot typeCheck
    "Dog.meta.fields.unary2" shouldNot typeCheck
    "Dog.meta.fields.parameterized2" shouldNot typeCheck
  }

  /*
  "publicVal selector" should "return the public vals, vars, and nullary defs" in {
    "Animal.publicFields.name" should compile
    "Animal.publicFields.weight" should compile
    "Animal.publicFields.color" should compile
    "Animal.publicFields.otherName" should compile
    "Animal.publicFields.whatever" should compile
    "Dog.publicFields.name" should compile
    "Dog.publicFields.weight" should compile
    "Dog.publicFields.color" should compile
    "Dog.publicFields.owner" should compile
    "Dog.publicFields.otherName" should compile
    "Dog.publicFields.otherOtherName" should compile
    "Dog.publicFields.whatever" should compile
    "Dog.publicFields.whatever2" should compile
  }

  it should "not return the non-public vals" in {
    "Animal.publicFields.animate" shouldNot typeCheck
    "Dog.publicFields.animate" shouldNot typeCheck
    "Dog.publicFields.genus" shouldNot typeCheck
  }

  it should "not return the non-nullary defs" in {
    "Animal.publicFields.unary" shouldNot typeCheck
    "Animal.publicFields.parameterized" shouldNot typeCheck
    "Dog.publicFields.unary" shouldNot typeCheck
    "Dog.publicFields.parameterized" shouldNot typeCheck
    "Dog.publicFields.unary2" shouldNot typeCheck
    "Dog.publicFields.parameterized2" shouldNot typeCheck
  }

  "from" should "not work on non-case classes" in {
    "FieldsMacro.from[Animal]" shouldNot compile
  }

  it should "not return other vals, vars and defs" in {
    "Dog.fields.animate" shouldNot typeCheck
    "Dog.fields.owner" shouldNot typeCheck
    "Dog.fields.genus" shouldNot typeCheck
    "Dog.fields.whatever" shouldNot typeCheck
    "Dog.fields.whatever2" shouldNot typeCheck
    "Dog.fields.unary" shouldNot typeCheck
    "Dog.fields.parameterized" shouldNot typeCheck
    "Dog.fields.unary2" shouldNot typeCheck
    "Dog.fields.parameterized2" shouldNot typeCheck
    "Dog.fields.otherName" shouldNot typeCheck
    "Dog.fields.otherOtherName" shouldNot typeCheck
  }

  it should "not return fields from another constructor" in {
    "Dog.fields.other" shouldNot typeCheck
  }
*/

  "Definitions" should "have the correct class" in {
    //"val x: Field[Animal, Option[String]] = Animal.publicFields.name" should compile
    //"val x: Field[Animal, Long] = Animal.publicFields.weight" should compile
    //"val x: CopyableField[Dog, Some[String]] = Dog.publicFields.name" should compile
    //"val x: CopyableField[Dog, Long] = Dog.publicFields.weight" should compile
    //"val x: Field[Dog, String] = Dog.publicFields.owner" should compile
    "val x: CopyableField[Dog, String] = Dog.meta.fields.color" should compile
    "val x: CopyableField[Dog, Some[String]] = Dog.meta.fields.name" should compile
    "val x: CopyableField[Dog, Long] = Dog.meta.fields.weight" should compile
  }

  they should "carry the correct name" in {
    //force.arf(Animal.publicFields.name).name.name should be("name")
    //force.arf(Animal.publicFields.weight).name.name should be("weight")
    //force.arf(Animal.publicFields.otherName).name.name should be("otherName")
    //force.arf(Dog.publicFields.name).name.name should be("name")
    //force.arf(Dog.publicFields.weight).name.name should be("weight")
    //force.arf(Dog.publicFields.owner).name.name should be("owner")
    //force.arf(Dog.publicFields.otherName).name.name should be("otherName")
    //force.arf(Dog.publicFields.otherOtherName).name.name should be("otherOtherName")
    force.arf(Dog.meta.fields.color).name.name should be("color")
    force.arf(Dog.meta.fields.name).name.name should be("name")
    force.arf(Dog.meta.fields.weight).name.name should be("weight")
  }

  they should "carry the correct class tags" in {
    //force.arf(Animal.publicFields.name).typeClassTag should be(implicitly[ClassTag[Option[String]]])
    //force.arf(Animal.publicFields.weight).typeClassTag should be(implicitly[ClassTag[Long]])
    //force.arf(Animal.publicFields.otherName).typeClassTag should be(implicitly[ClassTag[Option[String]]])
    //force.arf(Dog.publicFields.name).typeClassTag should be(implicitly[ClassTag[Some[String]]])
    //force.arf(Dog.publicFields.weight).typeClassTag should be(implicitly[ClassTag[Long]])
    //force.arf(Dog.publicFields.owner).typeClassTag should be(implicitly[ClassTag[String]])
    //force.arf(Dog.publicFields.otherName).typeClassTag should be(implicitly[ClassTag[Option[String]]])
    //force.arf(Dog.publicFields.otherOtherName).typeClassTag should be(implicitly[ClassTag[Option[String]]])
    force.arf(Dog.meta.fields.color).typeClassTag should be(implicitly[ClassTag[String]])
    force.arf(Dog.meta.fields.name).typeClassTag should be(implicitly[ClassTag[Some[String]]])
    force.arf(Dog.meta.fields.weight).typeClassTag should be(implicitly[ClassTag[Long]])
  }


  val casimir: Animal = new Animal {
    override val weight: Long = 42L
    override val color: String = "orange"
    override val name: Option[String] = Some("Casimir")
  }
  val rex: Dog = Dog("brown", 9L, Some("Rex"))

  "Getters" should "return the correct value" in {
    //Animal.publicFields.name(casimir).get should be(Some("Casimir"))
    //Animal.publicFields.weight(casimir).get should be(42L)
    //Animal.publicFields.otherName(casimir).get should be(Some("Casimir"))
    //Animal.publicFields.name(rex).get should be(Some("Rex"))
    //Animal.publicFields.weight(rex).get should be(9L)
    //Animal.publicFields.otherName(rex).get should be(Some("Rex"))
    //Dog.publicFields.name(rex).get should be(Some("Rex"))
    //Dog.publicFields.weight(rex).get should be(9L)
    //Dog.publicFields.owner(rex).get should be("Unknown")
    //Dog.publicFields.otherName(rex).get should be(Some("Rex"))
    //Dog.publicFields.otherOtherName(rex).get should be(Some("Rex"))
    Dog.meta.fields.color(rex).get should be("brown")
    Dog.meta.fields.name(rex).get should be(Some("Rex"))
    Dog.meta.fields.weight(rex).get should be(9L)
  }

  "Copiers" should "return the correct value" in {
    //force.dcf[Some[String]](Dog.publicFields.name)(rex).copy(Some("Medor")) should be(rex.copy(name = Some("Medor")))
    //force.dcf[Long](Dog.publicFields.weight)(rex).copy(12L) should be(rex.copy(weight = 12L))
    force.dcf[String](Dog.meta.fields.color)(rex).copy("black") should be(rex.copy(color = "black"))
    force.dcf[Some[String]](Dog.meta.fields.name)(rex).copy(Some("Medor")) should be(rex.copy(name = Some("Medor")))
    force.dcf[Long](Dog.meta.fields.weight)(rex).copy(12L) should be(rex.copy(weight = 12L))
  }

  they should "expect the correct type" in {
    //"Dog.publicFields.name(rex).copy(None)" shouldNot typeCheck
    //"Dog.publicFields.weight(rex).copy(\"42\")" shouldNot typeCheck
    "Dog.meta.fields.color(rex).copy(42)" shouldNot typeCheck
    "Dog.meta.fields.name(rex).copy(\"Medor\")" shouldNot typeCheck
    "Dog.meta.fields.weight(rex).copy(\"12\")" shouldNot typeCheck
  }

  they should "not compile when not available" in {
    //"Animal.publicFields.name(casimir).copy(None)" shouldNot typeCheck
    //"Animal.publicFields.weight(casimir).copy(42L)" shouldNot typeCheck
  }




}

