package com.quane.little.ide.presenter

import com.quane.little.ide.view.{SetStatementViewListener, SetStatementView}
import com.quane.little.language.{SetStatement, Scope}
import com.quane.little.language.memory.Pointer
import com.quane.little.language.data.Value

/** Presenter for views representing a [[com.quane.little.language.SetStatement]]
  *
  * @author Sean Connolly
  */
class SetStatementPresenter[V <: SetStatementView](view: V)
  extends StatementPresenter
  with SetStatementViewListener {

  private var _name = ""
  private var _value = ""

  view.addViewListener(this)
  view.setName(_name)
  view.setValue(_value)

  private[presenter] def name_=(name: String): Unit = {
    _name = name
    view.setName(name)
  }

  private[presenter] def value_=(value: String): Unit = {
    _value = value
    view.setValue(value)
  }

  override def nameChanged(name: String): Unit = _name = name

  override def compile(scope: Scope): SetStatement = {
    new SetStatement(new Pointer(scope, _name), new Value("TODO"))
  }

}