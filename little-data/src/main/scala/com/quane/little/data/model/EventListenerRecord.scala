package com.quane.little.data.model

import com.google.common.base.Objects
import com.quane.little.language.event.EventListener

/** A database record for an [[EventListener]].
  *
  * @author Sean Connolly
  */
class EventListenerRecord(var definition: EventListener) {

  var _id: ID = _

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("id", _id)
      .add("definition", definition)
      .toString

}
