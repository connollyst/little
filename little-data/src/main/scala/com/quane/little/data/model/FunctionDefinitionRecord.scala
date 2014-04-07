package com.quane.little.data.model

import com.google.common.base.Objects
import com.quane.little.language.FunctionDefinition

/** A database record for a [[FunctionDefinition]].
  *
  * @author Sean Connolly
  */
class FunctionDefinitionRecord(val ownerId: String, var definition: FunctionDefinition) {

  var _id: ID = _

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("id", _id)
      .add("definition", definition)
      .toString

}
