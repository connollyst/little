package com.quane.little.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.base.Objects
import com.quane.little.language.FunctionDefinition

/** A database record for a [[FunctionDefinition]].
  *
  * @author Sean Connolly
  */
class FunctionDefinitionRecord(val ownerId: RecordID, var definition: FunctionDefinition)
  extends HasRecordID {

  @JsonProperty("_id")
  var id: RecordID = _

  override def equals(other: Any): Boolean = other match {
    case that: FunctionDefinitionRecord =>
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
