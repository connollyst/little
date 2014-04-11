package com.quane.little.data.model

import com.google.common.base.Objects
import com.quane.little.language.FunctionDefinition

/** A database record for a [[FunctionDefinition]].
  *
  * @author Sean Connolly
  */
class FunctionDefinitionRecord(val ownerId: String, var definition: FunctionDefinition)
  extends HasRecordID {

  var id: RecordID = _

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("id", id)
      .add("definition", definition)
      .toString

}
