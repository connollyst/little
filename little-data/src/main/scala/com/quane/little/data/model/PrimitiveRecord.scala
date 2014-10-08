package com.quane.little.data.model

import com.google.common.base.Objects
import com.quane.little.data.model.CodeCategory._
import com.quane.little.language.Code

/** A database record for a primitive [[com.quane.little.language.Code]].
  * That is, all code which is not itself a function.
  *
  * @author Sean Connolly
  */
class PrimitiveRecord(var id: RecordId, val category: CodeCategory, val name: String, val expression: Code)
  extends HasRecordId {

  override def equals(other: Any): Boolean = other match {
    case that: PrimitiveRecord =>
      that.isInstanceOf[PrimitiveRecord] &&
        id == that.id &&
        expression == that.expression
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(id, expression)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("id", id)
      .add("category", category)
      .add("name", name)
      .add("expression", expression)
      .toString

}
