package com.quane.little.ide.presenter

import com.quane.little.ide.view.{PrintStatementViewListener, PrintStatementView}
import com.quane.little.language.{PrintStatement, Scope}
import com.quane.little.language.data.Value

/**
 *
 *
 * @author Sean Connolly
 */
class PrintStatementPresenter[V <: PrintStatementView](view: V)
  extends StatementPresenter
  with PrintStatementViewListener {

  private var _value = ""

  view.addViewListener(this)
  view.setValue(_value)

  private[presenter] def value_=(value: String): Unit = {
    _value = value
    view.setValue(value)
  }

  override def valueChanged(value: String): Unit = _value = value

  override def compile(scope: Scope): PrintStatement = {
    new PrintStatement(new Value(_value))
  }

}