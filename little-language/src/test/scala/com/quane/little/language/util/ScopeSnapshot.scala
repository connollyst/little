package com.quane.little.language.util

import com.quane.little.language.data.Value
import com.quane.little.language.{Scope, Expression}

/** An expression to facilitate testing; takes a snapshot of the [[Scope]] as it
  * is evaluated.
  *
  * @author Sean Connolly
  */
class ScopeSnapshot
  extends Expression {

  var _lastScope: Option[Scope] = None

  def scope =
    _lastScope match {
      case Some(s) => s
      case _ => throw new IllegalAccessException("No scope snapshot.")
    }

  override def evaluate(scope: Scope): Value = {
    // TODO deep copy scope to really take a 'snapshot'
    _lastScope = Some(scope)
    Value(None)
  }


}
