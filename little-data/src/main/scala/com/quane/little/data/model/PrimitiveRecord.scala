package com.quane.little.data.model

import com.quane.little.language.Expression
import com.google.common.base.Objects

/** A database record for a primitive [[com.quane.little.language.Expression]].
  * That is, all expressions which are not themselves functions.
  *
  * @author Sean Connolly
  */
class PrimitiveRecord(var id: RecordId, val expression: Expression)
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
      .add("expression", expression)
      .toString

}
