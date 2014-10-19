package com.quane.little.data.model

import com.google.common.base.Objects
import com.quane.little.data.model.CodeCategory._
import com.quane.little.language.Code

/** A database record for a primitive [[com.quane.little.language.Code]].
  * That is, all code which is not itself a function.
  *
  * @author Sean Connolly
  */
class CodeRecord(var id: Id, val category: CodeCategory, val name: String, val code: Code)
  extends HasId[Id] {

  override def equals(other: Any): Boolean = other match {
    case that: CodeRecord =>
      that.isInstanceOf[CodeRecord] &&
        id == that.id &&
        code == that.code
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(id, code)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("id", id)
      .add("name", name)
      .add("category", category)
      .add("code", code)
      .toString

}
