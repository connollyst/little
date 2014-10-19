package com.quane.little.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.base.Objects
import com.quane.little.language.FunctionDefinition
import com.quane.little.data.model.CodeCategory.CodeCategory

/** A database record for a [[com.quane.little.language.FunctionDefinition]].
  *
  * @author Sean Connolly
  */
class FunctionRecord(val ownerId: UserId, var category: CodeCategory, var definition: FunctionDefinition)
  extends HasId[FunctionId] {

  @JsonProperty("_id")
  var id: FunctionId = _

  def asCodeRecord: CodeRecord = {
    new CodeRecord(id, category, definition.name, definition.asReference)
  }

  override def equals(other: Any): Boolean = other match {
    case that: FunctionRecord =>
      that.isInstanceOf[FunctionRecord] &&
        id == that.id &&
        ownerId == that.ownerId &&
        definition == that.definition
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(id, ownerId, definition)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("id", id)
      .add("ownerId", ownerId)
      .add("definition", definition)
      .toString

}
