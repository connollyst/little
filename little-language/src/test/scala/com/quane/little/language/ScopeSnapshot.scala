package com.quane.little.language

import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.data.{Nada, ValueType, Value}

/** An statement to facilitate testing; takes a snapshot of the [[Scope]] as it
  * is evaluated.
  *
  * @author Sean Connolly
  */
class ScopeSnapshot extends Code {

  var _lastScope: Option[Scope] = None

  def scope =
    _lastScope match {
      case Some(s) => s
      case _ => throw new IllegalAccessException("No scope snapshot.")
    }

  override def returnType: ValueType = ValueType.Nothing

  override def evaluate(scope: Scope): Value = {
    // TODO deep copy scope to really take a 'snapshot'
    _lastScope = Some(scope)
    new Nada
  }


}
