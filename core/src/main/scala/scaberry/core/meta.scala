package scaberry.core

trait Meta[Source] {

  val orderedFields: Seq[Field[Source, _]]

  val fieldsMap: Map[Symbol, Field[Source, _]]

}

trait CaseMeta[Source] extends Meta[Source] {

  val orderedFields: Seq[CopyableField[Source, _]]

  val fieldsMap: Map[Symbol, CopyableField[Source, _]]

}
