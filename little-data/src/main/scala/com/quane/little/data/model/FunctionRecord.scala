package com.quane.little.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.base.Objects
import com.quane.little.language.FunctionDefinition

/** A database record for a [[com.quane.little.language.FunctionDefinition]].
  *
  * @author Sean Connolly
  */
class FunctionRecord(val ownerId: RecordId, var definition: FunctionDefinition)
  extends HasRecordId {

  @JsonProperty("_id")
  var id: RecordId = _

  override def equals(other: Any): Boolean = other match {
    case that: FunctionRecord =>
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