package com.quane.little.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.base.Objects
import com.quane.little.language.event.EventListener

/** A database record for an [[EventListener]].
  *
  * @author Sean Connolly
  */
class ListenerRecord(val ownerId: RecordId, var listener: EventListener)
  extends HasRecordId {

  @JsonProperty("_id")
  var id: RecordId = _

  override def equals(other: Any): Boolean = other match {
    case that: ListenerRecord =>
      id == that.id &&
        ownerId == that.ownerId &&
        listener == that.listener
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(id, ownerId, listener)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("id", id)
      .add("listener", listener)
      .toString

}
